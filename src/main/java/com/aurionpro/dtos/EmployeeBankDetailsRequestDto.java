package com.aurionpro.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeBankDetailsRequestDto {

    @NotBlank
    @Size(max = 20)
    private String accountNumber;

    @NotBlank
    private String bankName;

    @NotBlank
    @Size(min = 11, max = 11) // IFSC code length
    private String ifscCode;
}
