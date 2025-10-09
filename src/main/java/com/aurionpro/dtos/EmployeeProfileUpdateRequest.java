package com.aurionpro.dtos;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmployeeProfileUpdateRequest {
    @Size(min = 2, max = 100, message = "Name must be 2-100 chars")
    @Pattern(regexp = "^[A-Za-z\\s.\\-']+$", message = "Name contains invalid characters")
    private String name;

    @Size(min = 10, max = 15, message = "Contact number must be 10-15 digits")
    @Pattern(regexp = "^[0-9+\\-()\\s]+$", message = "Contact number contains invalid characters")
    private String contactNumber;
}

