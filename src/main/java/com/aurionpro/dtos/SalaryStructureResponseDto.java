package com.aurionpro.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryStructureResponseDto {

	   private Long slipId;

	    private Long employeeId;

	    private Long organizationId;

	    private int month;

	    private int year;

	    private Double basicSalary;

	    private Double hra;

	    private Double dearnessAllowance;

	    private Double pf;

	    private Double otherAllowances;

	    private Double netSalary;
}
