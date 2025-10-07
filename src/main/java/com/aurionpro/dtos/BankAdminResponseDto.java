package com.aurionpro.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAdminResponseDto {

    private Long adminId;
    private String adminName;
    private String email;
    private boolean isSuperAdmin;
    private boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginDate;
}
