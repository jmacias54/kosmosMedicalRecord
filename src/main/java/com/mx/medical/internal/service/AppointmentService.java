package com.mx.medical.internal.service;

import com.mx.medical.internal.controller.appoiment.request.AppointmentRequest;
import com.mx.medical.internal.controller.appoiment.request.AppointmentResponse;
import com.mx.medical.internal.entity.Appointment;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {

	AppointmentResponse create(AppointmentRequest request);
	List<Appointment> search(LocalDate fecha, Long consultorioId, Long doctorId);
	void delete(Long id);
	AppointmentResponse edit(Long id, AppointmentRequest appointmentRequest);
}
