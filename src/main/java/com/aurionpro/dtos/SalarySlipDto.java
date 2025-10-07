package com.aurionpro.dtos;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalarySlipDto {

    private Long slipId;

    private Long employeeId;

    private int month;

    private int year;

    private Map<String, Double> salaryBreakdown; // keys: basicSalary, hra, pf, etc.

    private Double netSalary;

    private String paySlipUrl; // PDF download link
}
