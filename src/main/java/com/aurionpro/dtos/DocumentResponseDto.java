package com.aurionpro.dtos;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DocumentResponseDto {
    private Long documentId;
    private String documentName;
    private String documentType;
    private String documentUrl;
    private String status;
    private String rejectionReason;
    private Long organizationId;
    private String uploadedAt;
    private String verifiedAt;
    private Long verifiedByUserId;
}
