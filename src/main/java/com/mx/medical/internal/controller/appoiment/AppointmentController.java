package com.mx.medical.internal.controller.appoiment;

import com.mx.medical.internal.configuration.constant.GlobalConstants;
import com.mx.medical.internal.controller.appoiment.request.AppointmentRequest;
import com.mx.medical.internal.controller.appoiment.request.AppointmentResponse;
import com.mx.medical.internal.entity.Appointment;
import com.mx.medical.internal.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = GlobalConstants.APPOIMENTS_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
public class AppointmentController {

	private final AppointmentService appointmentService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppointmentResponse> create(@RequestBody @Valid AppointmentRequest request) {
		return ResponseEntity.ok(this.appointmentService.create(request));
	}

	@PutMapping("/{id}")
	public ResponseEntity<AppointmentResponse> edit(@PathVariable Long id,@RequestBody @Valid AppointmentRequest request) {
		return ResponseEntity.ok(this.appointmentService.edit(id,request));
	}


	@GetMapping("/search")
	public ResponseEntity<List<Appointment>> search(
		@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
		@RequestParam(required = false) Long consultorioId,
		@RequestParam(required = false) Long doctorId
	) {
		return ResponseEntity.ok(appointmentService.search(fecha, consultorioId, doctorId));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		appointmentService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
