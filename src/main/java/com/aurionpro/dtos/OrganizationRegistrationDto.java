package com.aurionpro.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationRegistrationDto {

    @NotBlank
    @Size(max = 120)
    private String userName;

    @NotBlank
    @Email
    @Size(max = 254)
    private String email;

    @NotBlank
    @Size(min = 8, max = 120)
    private String password;

    @NotBlank
    @Size(max = 120)
    private String organizationName;

    @NotBlank(message = "Contact number is required")
    @Size(min = 10, max = 15)
    private String contactNumber;
}
