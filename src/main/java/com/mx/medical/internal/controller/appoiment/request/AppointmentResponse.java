package com.mx.medical.internal.controller.appoiment.request;


import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AppointmentResponse {

	private  Long appointmentId;


}
