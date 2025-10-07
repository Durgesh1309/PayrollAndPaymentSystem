package com.aurionpro.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestResponseDto {

    private Long requestId;

    private Long organizationId;

    private Long employeeId;

    private String requestType;

    private Double amount;

    private String requestStatus;

    private LocalDate requestDate;

    private LocalDate actionDate;

    private Long adminId;

    private String description;
}
