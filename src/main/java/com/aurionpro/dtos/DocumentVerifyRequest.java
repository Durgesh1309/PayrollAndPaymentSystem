package com.aurionpro.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DocumentVerifyRequest {
    @NotBlank
    private String action; // APPROVE or REJECT
    private String rejectionReason; // required if REJECT
}

