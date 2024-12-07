package com.mx.medical.internal.controller.validator;

import com.mx.medical.internal.controller.appoiment.request.AppointmentRequest;
import com.mx.medical.internal.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueConsultingRoomTimeValidator implements ConstraintValidator<UniqueConsultingRoomTime, AppointmentRequest> {

	private final AppointmentRepository appointmentRepository;

	@Override
	public void initialize(UniqueConsultingRoomTime constraintAnnotation) {
	}

	@Override
	public boolean isValid(AppointmentRequest request, ConstraintValidatorContext context) {
		return !appointmentRepository.existsByConsultingRoomIdAndConsultationSchedule(
			request.getConsultingRoomId(),
			request.getConsultationSchedule()
		);
	}
}
