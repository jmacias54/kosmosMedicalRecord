package com.mx.medical.internal.controller.appoiment.request;

import com.mx.medical.internal.controller.validator.UniqueConsultingRoomTime;
import com.mx.medical.internal.controller.validator.UniqueDoctorTime;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@UniqueConsultingRoomTime
@UniqueDoctorTime
@Accessors(chain = true)
public class AppointmentRequest {


	@NotNull(message = "consultingRoomId is required.")
	private final Long consultingRoomId;

	@NotNull(message = "doctorId is required.")
	private final Long doctorId;

	@NotNull(message = "Patien name is required.")
	private final String patientName;

	@NotNull(message = "consultationSchedule is required.")
	private final LocalDateTime consultationSchedule;

}
