package com.aurionpro.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeBankDetailsResponseDto {

    private Long accountId;

    private String accountNumber;

    private String bankName;

    private String ifscCode;

    private Double balance;

    private boolean active;
}
