package com.aurionpro.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryStructureRequestDto {

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Organization ID is required")
    private Long organizationId;

    @NotNull(message = "Month is required")
    @Min(value = 1, message = "Month must be between 1 and 12")
    @Max(value = 12, message = "Month must be between 1 and 12")
    private Integer month;

    @NotNull(message = "Year is required")
    @Min(value = 2000, message = "Year must be 2000 or later")
    private Integer year;

    @NotNull(message = "Basic salary is required")
    @PositiveOrZero(message = "Basic salary cannot be negative")
    private Double basicSalary;

    @NotNull(message = "HRA is required")
    @PositiveOrZero(message = "HRA cannot be negative")
    private Double hra;

    @NotNull(message = "Dearness allowance is required")
    @PositiveOrZero(message = "Dearness allowance cannot be negative")
    private Double dearnessAllowance;

    @NotNull(message = "PF is required")
    @PositiveOrZero(message = "PF cannot be negative")
    private Double pf;

    @NotNull(message = "Other allowances are required")
    @PositiveOrZero(message = "Other allowances cannot be negative")
    private Double otherAllowances;
}
