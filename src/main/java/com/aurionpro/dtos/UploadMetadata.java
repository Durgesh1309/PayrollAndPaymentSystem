package com.aurionpro.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UploadMetadata {
    @NotBlank
    private String documentType;  // e.g., GST_CERTIFICATE
    @NotBlank
    private String documentName;  // readable label
    @NotNull
    private Long organizationId;  // association
}

