package com.example.careplane.dto;

import com.example.careplane.auth.RegisterRequest;
import com.example.careplane.entity.Admin;
import com.example.careplane.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDto extends RegisterRequest {

    public static Admin toEntity(AdminDto request) {
        return Admin.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .password(request.getPassword())
                .email(request.getEmail())
                .role(convertRole(request.getRole()))
                .phone(request.getPhone())
                .build();
    }

    private static Role convertRole(String role) {
        try {
            return Role.valueOf(role.substring(0, 1).toUpperCase() + role.substring(1).toLowerCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role value: " + role);
        }
    }
}
