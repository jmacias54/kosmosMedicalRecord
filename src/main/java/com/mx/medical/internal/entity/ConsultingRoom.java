package com.mx.medical.internal.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "consulting_room")
public class ConsultingRoom {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "number")
	private Integer number;

	@Column(name = "floor")
	private Integer floor;

	@OneToMany(mappedBy = "consultingRoom")
	private List<Appointment> appointments;


}