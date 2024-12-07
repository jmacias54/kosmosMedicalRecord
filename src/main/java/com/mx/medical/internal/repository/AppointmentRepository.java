package com.mx.medical.internal.repository;

import com.mx.medical.internal.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	boolean existsByDoctorIdAndConsultationSchedule(Long doctorId, LocalDateTime consultationSchedule);

	boolean existsByConsultingRoomIdAndConsultationSchedule(Long consultingRoomId, LocalDateTime consultationSchedule);

	long countByDoctorIdAndConsultationScheduleBetween(
		Long doctorId, LocalDateTime startDateTime, LocalDateTime endDateTime
	);

	long countByPatientNameAndConsultationScheduleBetween(
		String patientName, LocalDateTime startDateTime, LocalDateTime endDateTime);

	List<Appointment> findByConsultationScheduleAndConsultingRoomIdAndDoctorId(
		LocalDateTime startDateTime, Long consultingRoomId, Long doctorId);

	List<Appointment> findByConsultationScheduleAndConsultingRoomId(
		LocalDateTime startDateTime, Long consultingRoomId);

	List<Appointment> findByConsultationScheduleAndDoctorId(
		LocalDateTime startDateTime, Long doctorId);

	List<Appointment> findByConsultationScheduleBetween(
		LocalDateTime startDateTime, LocalDateTime endDateTime);
}
