package com.example.careplane.controller;

import com.example.careplane.entity.Doctor;
import com.example.careplane.services.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.example.careplane.dto.PatientDto;
import com.example.careplane.entity.Appointment;
import com.example.careplane.services.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/{doctorId}/appointments")
    public ResponseEntity<List<Appointment>> getAllAppointments(@PathVariable Long doctorId) {
        List<Appointment> appointments = doctorService.getAllAppointments(doctorId);
        return ResponseEntity.ok(appointments);
    }

    @PostMapping("/{doctorId}/patients")
    public ResponseEntity<String> createPatientAccount(@PathVariable Long doctorId, @RequestBody PatientDto patientDto) {
        doctorService.createPatientAccount(doctorId, patientDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Patient account created successfully");
    }

    // Ajoutez d'autres méthodes du contrôleur pour gérer d'autres fonctionnalités spécifiques au médecin
}


/*@RestController
@RequestMapping("/api/v1/medecin")
public class DoctorController {
    @GetMapping
    public String get() {
        return "GET:: medecin controller";
    }
    @PostMapping
    public String post() {
        return "POST:: medecin controller";
    }
    @PutMapping
    public String put() {
        return "PUT:: medecin controller";
    }
    @DeleteMapping
    public String delete() {
        return "DELETE:: medecin controller";
    }
}*/
