package com.example.careplane.controller;

import com.example.careplane.dto.DoctorDto;
import com.example.careplane.dto.Response; // Import your custom Response class
import com.example.careplane.entity.Appointment;
import com.example.careplane.services.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/adddoctor")
    public ResponseEntity<Response> addDoctor(
            @RequestBody @Valid DoctorDto doctorDTO
    ) {
        Response response = adminService.addDoctor(doctorDTO);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/doctors/{doctorId}/appointments")
    public ResponseEntity<List<Appointment>> getDoctorAppointments(@PathVariable Long doctorId) {
        List<Appointment> appointments = adminService.getDoctorAppointments(doctorId);
        return ResponseEntity.ok(appointments);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/doctors/{doctorId}")
    public ResponseEntity<Response> deleteDoctor(@PathVariable Long doctorId) {
        Response response = adminService.deleteDoctor(doctorId);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(404).body(response);
        }
    }
}
