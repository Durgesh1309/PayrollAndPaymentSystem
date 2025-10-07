package com.aurionpro.dtos;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDto {

    private Long employeeId;
    private String employeeName;
    private String employeeEmail;
    private String employeeContactNo;
    private Long organizationId;
    private List<String> documents;
    private boolean status;
}
