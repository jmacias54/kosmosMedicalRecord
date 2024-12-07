package com.mx.medical.internal.service.impl;

import com.mx.medical.internal.entity.ConsultingRoom;
import com.mx.medical.internal.repository.ConsultingRoomRepository;
import com.mx.medical.internal.service.ConsultingRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsultingRoomServiceImpl implements ConsultingRoomService {

	private final ConsultingRoomRepository consultingRoomRepository;


	public List<ConsultingRoom> getAllConsultingRooms() {
		return consultingRoomRepository.findAll();
	}


	public Optional<ConsultingRoom> getConsultingRoomById(Long id) {
		return consultingRoomRepository.findById(id);
	}
}
