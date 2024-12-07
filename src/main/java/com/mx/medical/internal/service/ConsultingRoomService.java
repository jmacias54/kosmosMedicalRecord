package com.mx.medical.internal.service;

import com.mx.medical.internal.entity.ConsultingRoom;

import java.util.List;
import java.util.Optional;

public interface ConsultingRoomService {

	List<ConsultingRoom> getAllConsultingRooms();
	Optional<ConsultingRoom> getConsultingRoomById(Long id);
}
