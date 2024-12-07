package com.mx.medical.internal.controller.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = { UniqueConsultingRoomTimeValidator.class })
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueConsultingRoomTime {
	String message() default "No se puede agendar cita en un mismo consultorio a la misma hora.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
