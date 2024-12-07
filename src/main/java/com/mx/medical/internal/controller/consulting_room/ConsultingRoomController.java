package com.mx.medical.internal.controller.consulting_room;

import com.mx.medical.internal.configuration.constant.GlobalConstants;
import com.mx.medical.internal.entity.ConsultingRoom;
import com.mx.medical.internal.service.ConsultingRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = GlobalConstants.CONSULTING_ROOM_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
public class ConsultingRoomController {

	private final ConsultingRoomService consultingRoomService;

	@GetMapping
	public ResponseEntity<List<ConsultingRoom>> getAllConsultingRooms() {
		List<ConsultingRoom> consultingRooms = consultingRoomService.getAllConsultingRooms();
		return ResponseEntity.ok(consultingRooms);
	}


	@GetMapping("/{id}")
	public ResponseEntity<ConsultingRoom> getConsultingRoomById(@PathVariable Long id) {
		return consultingRoomService.getConsultingRoomById(id)
			.map(ResponseEntity::ok)
			.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
}
