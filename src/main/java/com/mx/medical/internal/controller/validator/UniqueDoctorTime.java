package com.mx.medical.internal.controller.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = { UniqueDoctorTimeValidator.class })
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueDoctorTime {
	String message() default "No se puede agendar cita para un mismo Dr. a la misma hora.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}