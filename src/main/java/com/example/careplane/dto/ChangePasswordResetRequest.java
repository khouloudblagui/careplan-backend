package com.example.careplane.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordResetRequest {
    private String newPassword;
    private String currentPassword;
    private String confirmationPassword;


}
