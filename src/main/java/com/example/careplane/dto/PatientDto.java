package com.example.careplane.dto;

import com.example.careplane.auth.RegisterRequest;
import com.example.careplane.entity.Patient;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatientDto extends RegisterRequest {
    private LocalDate dateOfBirth; // Ajout du champ date de naissance

    public static Patient toEntity(PatientDto request) {
        return Patient.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .password(request.getPassword())
                .email(request.getEmail())
                .role(request.getRole())
                .phone(request.getPhone())
                .dateOfBirth(request.getDateOfBirth())
                .build();
    }
}