package com.aurionpro.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponseDto {

    private Long documentId;

    private String documentName;

    private String documentType;

    private String documentUrl;
}
