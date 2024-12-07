package com.mx.medical.internal.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "doctors")
public class Doctor {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "second_last_name")
	private String secondLastName;

	@Column(name = "specialty")
	private String specialty;

	@OneToMany(mappedBy = "doctor")
	private List<Appointment> appointments;

}