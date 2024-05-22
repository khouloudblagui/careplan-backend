package com.example.careplane.dto;

import com.example.careplane.auth.RegisterRequest;
import com.example.careplane.entity.Doctor;
import com.example.careplane.entity.Role;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DoctorDto extends RegisterRequest {

    public static Doctor toEntity(DoctorDto request) {
        return Doctor.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .password(request.getPassword())
                .email(request.getEmail())
                .role(Role.valueOf(request.getRole()))
                .phone(request.getPhone())
                .build();
    }
}
