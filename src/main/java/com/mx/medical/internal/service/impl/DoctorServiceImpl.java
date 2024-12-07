package com.mx.medical.internal.service.impl;

import com.mx.medical.internal.entity.Doctor;
import com.mx.medical.internal.repository.DoctorRepository;
import com.mx.medical.internal.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

	private final DoctorRepository doctorRepository;


	@Override
	public List<Doctor> getAllDoctors() {
		return doctorRepository.findAll();
	}

	public Optional<Doctor> getDoctorById(Long id) {
		return doctorRepository.findById(id);
	}
}
