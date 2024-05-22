package com.example.careplane.controller;

import com.example.careplane.dto.AppointmentDto;
import com.example.careplane.dto.Response; // Correct import for your custom Response class
import com.example.careplane.entity.Appointment;
import com.example.careplane.services.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('DOCTOR') or hasAuthority('PATIENT')")
    public ResponseEntity<Response> addAppointment(
            @RequestBody @Valid AppointmentDto appointmentDto
    ) {
        Response response = appointmentService.addAppointment(appointmentDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatientId(@PathVariable Long patientId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByPatientId(patientId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctorId(@PathVariable Long doctorId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctorId(doctorId);
        return ResponseEntity.ok(appointments);
    }

    @PutMapping("/update/{appointmentId}")
    @PreAuthorize("hasAuthority('DOCTOR') or hasAuthority('PATIENT')")
    public ResponseEntity<Response> updateAppointment(
            @PathVariable Long appointmentId,
            @RequestBody @Valid AppointmentDto appointmentDto
    ) {
        appointmentDto.setId(appointmentId);
        Response response = appointmentService.updateAppointment(appointmentDto);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(404).body(response);
        }
    }

    @DeleteMapping("/delete/{appointmentId}")
    @PreAuthorize("hasAuthority('DOCTOR') or hasAuthority('PATIENT')")
    public ResponseEntity<Response> deleteAppointment(@PathVariable Long appointmentId) {
        Response response = appointmentService.deleteAppointment(appointmentId);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(404).body(response);
        }
    }
}
