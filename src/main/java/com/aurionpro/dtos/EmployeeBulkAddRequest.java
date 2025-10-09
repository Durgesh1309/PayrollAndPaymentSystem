package com.aurionpro.dtos;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EmployeeBulkAddRequest {
    @NotNull
    private Long organizationId;

    @NotNull
    private MultipartFile file;  // CSV file containing employees data
}
