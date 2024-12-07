package com.mx.medical.internal.service.impl;

import com.mx.medical.internal.configuration.exception.BadRequestException;
import com.mx.medical.internal.controller.appoiment.request.AppointmentRequest;
import com.mx.medical.internal.controller.appoiment.request.AppointmentResponse;
import com.mx.medical.internal.entity.Appointment;
import com.mx.medical.internal.repository.AppointmentRepository;
import com.mx.medical.internal.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

	private final AppointmentRepository appointmentRepository;


	@Override
	public AppointmentResponse create(AppointmentRequest request) {
		Appointment appointment = new Appointment();
		appointment.setDoctorId(request.getDoctorId());
		appointment.setConsultationSchedule(request.getConsultationSchedule());
		appointment.setConsultingRoomId(request.getConsultingRoomId());
		appointment.setPatientName(request.getPatientName());

		Appointment saved = appointmentRepository.save(appointment);

		return new AppointmentResponse().setAppointmentId(saved.getId());
	}

	public List<Appointment> search(LocalDate fecha, Long consultorioId, Long doctorId) {
		if(fecha != null && consultorioId != null && doctorId != null) {
			// Consultar por fecha, consultorio y médico
			return appointmentRepository.findByConsultationScheduleAndConsultingRoomIdAndDoctorId(
				fecha.atStartOfDay(),
				consultorioId,
				doctorId
			);
		}
		else if(fecha != null && consultorioId != null) {
			// Consultar por fecha y consultorio
			return appointmentRepository.findByConsultationScheduleAndConsultingRoomId(
				fecha.atStartOfDay(),
				consultorioId
			);
		}
		else if(fecha != null && doctorId != null) {
			// Consultar por fecha y médico
			return appointmentRepository.findByConsultationScheduleAndDoctorId(
				fecha.atStartOfDay(),
				doctorId
			);
		}
		else if(fecha != null) {
			// Consultar solo por fecha
			return appointmentRepository.findByConsultationScheduleBetween(
				fecha.atStartOfDay(),
				fecha.atTime(23, 59, 59)
			);
		}

		return Collections.EMPTY_LIST;
	}

	@Override
	public void delete(Long id) {
		Optional<Appointment> appointment = this.appointmentRepository.findById(id);

		if(appointment.isEmpty())
			throw new BadRequestException("Appointment not exist");

		this.appointmentRepository.delete(appointment.get());

	}

	public AppointmentResponse edit(Long id, AppointmentRequest appointmentRequest) {
		Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);

		if(optionalAppointment.isPresent()) {
			Appointment existingAppointment = optionalAppointment.get();
			appointmentRepository.save(existingAppointment);

			return new AppointmentResponse().setAppointmentId(existingAppointment.getId());
		}

		throw new BadRequestException("The appointment id not exist");
	}
}
