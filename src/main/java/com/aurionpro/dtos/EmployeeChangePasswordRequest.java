package com.aurionpro.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmployeeChangePasswordRequest {

    @NotBlank
    private String currentPassword;

    @NotBlank
    @Size(min = 8, max = 120, message = "New password must be 8-120 chars")
    private String newPassword;

    @NotBlank
    private String confirmNewPassword;
}

