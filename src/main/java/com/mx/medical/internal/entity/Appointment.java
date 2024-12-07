package com.mx.medical.internal.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "appointment")
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "consulting_room_id", nullable = false)
	private Long consultingRoomId;

	@Column(name = "doctor_id", nullable = false)
	private Long doctorId;

	@Column(name = "consultation_schedule")
	private LocalDateTime consultationSchedule;

	@Column(name = "patient_name")
	private String patientName;

	@ManyToOne
	@JoinColumn(name = "consulting_room_id", insertable = false, updatable = false)
	private ConsultingRoom consultingRoom;

	@ManyToOne
	@JoinColumn(name = "doctor_id", insertable = false, updatable = false)
	private Doctor doctor;

	// Getters and setters...

}
