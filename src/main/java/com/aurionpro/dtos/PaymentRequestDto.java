package com.aurionpro.dtos;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto {

    @NotNull
    private Long organizationId;

    private Long employeeId; // Optional if vendor payment

    @NotBlank
    private String requestType; // "Salary", "VendorPayment"
    
    @NotNull
    @Positive
    private Double amount;

    private String description;

    private List<String> documents;
}
