package com.aurionpro.dtos;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationRequestDto {

    @NotBlank(message = "Organization name is required")
    @Size(min = 2, max = 120, message = "Organization name must be between 2 and 120 characters")
    private String organizationName;

    @NotBlank(message = "Organization email is required")
    @Email(message = "Invalid organization email format")
    private String organizationEmail;

    @NotEmpty(message = "At least one document is required")
    private List<@NotBlank(message = "Document name cannot be blank") String> documents;
}
