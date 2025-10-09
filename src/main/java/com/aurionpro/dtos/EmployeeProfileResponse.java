package com.aurionpro.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmployeeProfileResponse {
    private Long id;
    private String name;
    private String email;
    private String contactNumber;
    private Long organizationId;
    private String organizationName;
    private String role;
}

