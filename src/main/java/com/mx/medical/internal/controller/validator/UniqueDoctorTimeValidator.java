package com.mx.medical.internal.controller.validator;

import com.mx.medical.internal.controller.appoiment.request.AppointmentRequest;
import com.mx.medical.internal.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class UniqueDoctorTimeValidator implements ConstraintValidator<UniqueDoctorTime, AppointmentRequest> {

	private final AppointmentRepository appointmentRepository;

	@Override
	public void initialize(UniqueDoctorTime constraintAnnotation) {
	}

	@Override
	public boolean isValid(AppointmentRequest request, ConstraintValidatorContext context) {
		// Verificar si ya existe una cita para el mismo doctor a la misma hora
		boolean isExistingAppointment = appointmentRepository.existsByDoctorIdAndConsultationSchedule(
			request.getDoctorId(),
			request.getConsultationSchedule()
		);

		if(isExistingAppointment) {
			return false;
		}

		// Verificar si el doctor ya tiene 8 citas en el día
		long appointmentsCount = appointmentRepository.countByDoctorIdAndConsultationScheduleBetween(
			request.getDoctorId(),
			request.getConsultationSchedule().toLocalDate().atStartOfDay(),
			request.getConsultationSchedule().toLocalDate().atTime(23, 59, 59)
		);

		if(appointmentsCount >= 8) {
			return false;
		}

		// Verificar si ya existe una cita para el mismo paciente a la misma hora o con menos de 2 horas de diferencia
		LocalDateTime startDateTime = request.getConsultationSchedule().minusHours(2);
		LocalDateTime endDateTime = request.getConsultationSchedule().plusHours(2);

		long overlappingAppointments = appointmentRepository.countByPatientNameAndConsultationScheduleBetween(
			request.getPatientName(), startDateTime, endDateTime);

		return overlappingAppointments == 0;
	}
}
