package com.example.careplane.dto;

import com.example.careplane.entity.Role;
import com.example.careplane.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Long id;
    private String email;
    private String firstname;
    private String lastname;
    private String phone;
    private String password;
    private Role role;
    private boolean isEnabled;

    public static UserDto fromEntity(User request) {

        return UserDto.builder()
                .id(request.getId())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .role(request.getRole())
                .phone(request.getPhone())
                .isEnabled(request.getIsEnabled())
                .build();
    }


}

