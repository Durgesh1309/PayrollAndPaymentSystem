package com.aurionpro.dtos;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDto {

    @NotBlank(message = "Employee name is required")
    @Size(min = 2, max = 120, message = "Employee name must be between 2 and 120 characters")
    private String employeeName;

    @NotBlank(message = "Employee email is required")
    @Email(message = "Invalid employee email format")
    private String employeeEmail;

    @NotBlank(message = "Employee contact number is required")
    @Pattern(regexp = "\\d{10}", message = "Employee contact number must be exactly 10 digits")
    private String employeeContactNo;

    @NotNull(message = "Organization ID is required")
    private Long organizationId;

    @NotEmpty(message = "At least one document is required")
    private List<@NotBlank(message = "Document name cannot be blank") String> documents;
}
