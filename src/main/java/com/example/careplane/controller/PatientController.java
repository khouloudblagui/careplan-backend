package com.example.careplane.controller;

import com.example.careplane.dto.AppointmentDto;
import com.example.careplane.dto.Response;
import com.example.careplane.dto.PatientDto;
import com.example.careplane.entity.Appointment;
import com.example.careplane.services.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patient")
@RequiredArgsConstructor
@PreAuthorize("hasRole('PATIENT')")
public class PatientController {
    private final PatientService patientService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addPatient(
            @RequestBody @Valid PatientDto patientDto
    ) {
        Response response = patientService.addPatient(patientDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{patientId}/appointments")
    public ResponseEntity<List<Appointment>> getPatientAppointments(@PathVariable Long patientId) {
        List<Appointment> appointments = patientService.getPatientAppointments(patientId);
        return ResponseEntity.ok(appointments);
    }

    @PutMapping("/appointments/{appointmentId}")
    public ResponseEntity<Response> updateAppointment(
            @PathVariable Long appointmentId,
            @RequestBody @Valid AppointmentDto appointmentDto
    ) {
        appointmentDto.setId(appointmentId);
        Response response = patientService.updateAppointment(appointmentDto);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(404).body(response);
        }
    }

    @DeleteMapping("/appointments/{appointmentId}")
    public ResponseEntity<Response> deleteAppointment(@PathVariable Long appointmentId) {
        Response response = patientService.deleteAppointment(appointmentId);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(404).body(response);
        }
    }
}
