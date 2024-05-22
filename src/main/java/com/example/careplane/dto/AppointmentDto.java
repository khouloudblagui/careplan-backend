package com.example.careplane.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentDto {
    private Long id;
    private Long doctorId;
    private Long patientId;
    private String appointmentDate;
    private String hour;
    private String status;


}