package com.mx.medical.internal.configuration.handler;

import com.mx.medical.internal.configuration.handler.response.Error;
import com.mx.medical.internal.configuration.exception.BadRequestException;
import com.mx.medical.internal.configuration.exception.ItemNotFoundException;
import com.mx.medical.internal.configuration.handler.response.ResponseError;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.*;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	private final MessageSource messageSource;

	private static final String ARGS_NOT_VALID = "ArgumentNotValid";
	private static final String BIND_EXCEPTION = "BindException";
	private static final String TYPE_MISMATCH = "TypeMismatch";

	private static final String MISSING_SERVLET_REQ_PART = "MissingServletRequestPart";
	private static final String MISSING_SERVLET_REQ_PARAM = "MissingServletRequestParameter";

	private static final String NO_HANDLER_FOUND_EXCEPTION = "NoHandlerFoundException";
	private static final String REQ_METHOD_NOT_SUPPORTED = "RequestMethodNotSupported";
	private static final String MEDIA_TYPE_NOT_SUPPORTED = "MediaTypeNotSupported";
	private static final String ENTITY_NOT_FOUND = "EntityNotFound";

	@ExceptionHandler({EntityNotFoundException.class})
	public ResponseEntity<ResponseError> entityNotFoundException(final Exception ex, final HttpServletRequest request) {
		logger.error("EntityNotFoundException Occured:: URL " + request.getRequestURI() + getParameters(request));
		logger.error("Error detail:: ", ex);
		ResponseError responseError = createResponseError(ENTITY_NOT_FOUND, Arrays.asList(ex.getMessage()));
		return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = ConstraintViolationException.class)
	public ResponseEntity handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
		List<Error> errors = new ArrayList<>();

		for(ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(getError(violation.getMessage()));
		}

		return new ResponseEntity<>(ResponseError.builder().errors(errors).build(), HttpStatus.BAD_REQUEST);
	}


	@ExceptionHandler({ItemNotFoundException.class})
	public ResponseEntity<ResponseError> itemNotFoundException(final Exception ex, final HttpServletRequest request) {
		logger.error("itemNotFoundException Occured:: URL " + request.getRequestURI() + getParameters(request));
		logger.error("Error detail:: ", ex);
		ResponseError responseError = createResponseError(
			HttpStatus.NOT_FOUND.toString(),
			Arrays.asList(ex.getMessage())
		);
		return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({BadRequestException.class})
	public ResponseEntity<ResponseError> badRequestException(final Exception ex, final HttpServletRequest request) {
		logger.error("BadRequestException Occured:: URL " + request.getRequestURI() + getParameters(request));
		logger.error("Error detail:: ", ex);
		ResponseError responseError = createResponseError(
			HttpStatus.NOT_FOUND.toString(),
			Arrays.asList(ex.getMessage())
		);
		return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
		MethodArgumentNotValidException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request
	) {

		logger.error("handleMethodArgumentNotValid Occured:: URL ");
		logger.error("Error detail:: ", ex);

		imprimeError(ex);
		final List<String> errors = new ArrayList<>();
		for(final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getDefaultMessage());
		}
		for(final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getDefaultMessage());
		}

		ResponseError body = createResponseError(ARGS_NOT_VALID, errors);

		return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(
		BindException ex, HttpHeaders headers, HttpStatus status,
		WebRequest request
	) {

		logger.error("handleBindException Occured:: URL ");
		logger.error("Error detail:: ", ex);
		imprimeError(ex);
		final List<String> errors = new ArrayList<>();
		for(final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getDefaultMessage());
		}
		for(final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getDefaultMessage());
		}

		ResponseError body = createResponseError(BIND_EXCEPTION, errors);

		return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(
		TypeMismatchException ex, HttpHeaders headers,
		HttpStatus status, WebRequest request
	) {

		logger.error("handleTypeMismatch Occured:: URL ");
		logger.error("Error detail:: ", ex);
		imprimeError(ex);

		String error = " The entered value => " + (isNull(ex.getValue()) ? "'UNKNOWN'" : ex.getValue())
			+ " is a property of type => " + (isNull(ex.getPropertyName()) ? "'UNKNOWN'" : ex.getPropertyName()) + " ,the correct type to enter is => "
			+ ex.getRequiredType();

		ResponseError body = createResponseError(TYPE_MISMATCH, Arrays.asList(error));

		return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(
		MissingServletRequestPartException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request
	) {

		logger.error("handleMissingServletRequestPart Occured:: URL ");
		logger.error("Error detail:: ", ex);
		imprimeError(ex);
		final String error = ex.getRequestPartName() + " missing part.";

		ResponseError body = createResponseError(MISSING_SERVLET_REQ_PART, Arrays.asList(error));

		return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
		MissingServletRequestParameterException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request
	) {

		logger.error("handleMissingServletRequestParameter Occured:: URL ");
		logger.error("Error detail:: ", ex);
		imprimeError(ex);
		final String error = ex.getParameterName() + " missing parameter.";

		ResponseError body = createResponseError(MISSING_SERVLET_REQ_PARAM, Arrays.asList(error));

		return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
	}

	// 404
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(
		NoHandlerFoundException ex, HttpHeaders headers,
		HttpStatus status, WebRequest request
	) {

		logger.error("handleNoHandlerFoundException Occured:: URL ");
		logger.error("Error detail:: ", ex);
		imprimeError(ex);
		final String error = " No service found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

		ResponseError body = createResponseError(NO_HANDLER_FOUND_EXCEPTION, Arrays.asList(error));

		return handleExceptionInternal(ex, body, headers, HttpStatus.NOT_FOUND, request);
	}

	// 405 - method is not supported for this request. Supported methods are
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
		HttpRequestMethodNotSupportedException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request
	) {
		logger.error("handleHttpRequestMethodNotSupported Occured:: URL ");
		logger.error("Error detail:: ", ex);
		imprimeError(ex);
		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(" The method is not compatible with this request. The allowed methods are ");
		for(HttpMethod httpMethod : ex.getSupportedHttpMethods()) {
			builder.append(httpMethod + ", ");
		}

		HttpRequestMethodNotSupportedException exa = new HttpRequestMethodNotSupportedException(
			request.getHeader("accept"), String.valueOf(ex.getSupportedHttpMethods()));
		ResponseError body = createResponseError(REQ_METHOD_NOT_SUPPORTED, Arrays.asList(builder.toString()));

		return handleExceptionInternal(exa, body, headers, HttpStatus.METHOD_NOT_ALLOWED, request);

	}

	// 415
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
		HttpMediaTypeNotSupportedException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request
	) {

		logger.error("handleHttpMediaTypeNotSupported Occured:: URL ");
		logger.error("Error detail:: ", ex);
		imprimeError(ex);

		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" Media type is not supported. Supported media types are ");
		for(MediaType mediaType : ex.getSupportedMediaTypes()) {
			builder.append(mediaType + ", ");
		}

		MediaType mediaType = MediaType.parseMediaType(request.getHeader("accept"));
		HttpMediaTypeNotSupportedException exa = new HttpMediaTypeNotSupportedException(
			mediaType,
			ex.getSupportedMediaTypes()
		);
		headers.setAccept(ex.getSupportedMediaTypes());
		ResponseError body = createResponseError(
			MEDIA_TYPE_NOT_SUPPORTED,
			Arrays.asList(builder.substring(0, builder.length() - 2))
		);

		return handleExceptionInternal(exa, body, headers, HttpStatus.UNSUPPORTED_MEDIA_TYPE, request);
	}

	private ResponseError createResponseError(String httpStatusStr, List<String> errors) {

		logger.error("--- createResponseError ---  ");

		List<Error> errorList = errors.stream()
			.map(error -> Objects.nonNull(getError(error)) ? getError(error) : getError(httpStatusStr, error))
			.collect(Collectors.toList());

		return ResponseError.builder().errors(errorList).build();
	}

	private Error getError(String httpStatusStr, String error) {

		return Error.builder().code(httpStatusStr).title(httpStatusStr).detail(error).build();

	}

	private Error getError(String errorStr) {
		try {

			String title = messageSource.getMessage(errorStr, null, Locale.ENGLISH);
			String detail = messageSource.getMessage(errorStr.concat(".detail"), null, Locale.ENGLISH);
			return Error.builder().code(errorStr).title(title).detail(detail).build();
		} catch(NoSuchMessageException nsme) {
			logger.error(nsme.getMessage());
			return null;
		}
	}

	private void imprimeError(Throwable ex) {
		logger.error("Clase ejecutada  " + ex.getClass().getName());
		logger.error("Exception handler executed  " + ex);
	}

	private String getParameters(HttpServletRequest request) {

		StringBuilder posted = new StringBuilder();
		Enumeration<?> e = request.getParameterNames();
		if(nonNull(e)) {
			posted.append("?");
		}
		String ipAddr = getRemoteAddr(request); // : ip;
		if(nonNull(ipAddr) && !ipAddr.equals("")) {
			posted.append("&_ip=" + ipAddr);
		}
		String auth = request.getHeader("Authorization");

		if((isNull(auth)) || !auth.startsWith("Basic ")) {
			final String userAgent = request.getHeader("User-Agent");
			posted.append("&User-Agent=" + userAgent);
		}
		else {
			posted.append("&Authorization =" + auth);
		}

		if(nonNull(e))
			while(e.hasMoreElements()) {
				Object objectParam = e.nextElement();
				String param = (String)objectParam;
				String value = request.getParameter(param);
				posted.append("&" + param + "=" + value);
			}
		return posted.toString();
	}

	// get the source IP address of the HTTP request
	private String getRemoteAddr(HttpServletRequest request) {
		return request.getRemoteAddr();
	}

}
