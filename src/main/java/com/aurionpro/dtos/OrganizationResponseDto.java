package com.aurionpro.dtos;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationResponseDto {

    private Long organizationId;
    private String organizationName;
    private String organizationEmail;
    private boolean status;
    private List<String> documents;
    private LocalDateTime createdAt;
}
