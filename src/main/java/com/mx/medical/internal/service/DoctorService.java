package com.mx.medical.internal.service;

import com.mx.medical.internal.entity.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorService {

	Optional<Doctor> getDoctorById(Long id);
	List<Doctor> getAllDoctors();
}
