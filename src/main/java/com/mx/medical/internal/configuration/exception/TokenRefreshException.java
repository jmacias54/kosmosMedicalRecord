package com.mx.medical.internal.configuration.exception;

public class TokenRefreshException extends RuntimeException {



	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	/**
	 * @param msg
	 */
	public TokenRefreshException(String msg) {
		super(msg);
	}

	/**
	 * @param mensaje
	 * @param exception
	 */
	public TokenRefreshException(String mensaje, Throwable exception) {
		super(mensaje);
	}
	
	public TokenRefreshException(String token, String message) {
		super(String.format("Failed for [%s]: %s", token, message));
	}
}
