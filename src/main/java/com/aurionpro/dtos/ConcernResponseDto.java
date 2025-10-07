package com.aurionpro.dtos;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConcernResponseDto {

    private Long concernId;

    private Long employeeId;

    private String description;

    private String status;

    private String remarks;

    private LocalDateTime submittedAt;

    private LocalDateTime resolvedAt;

    private List<String> attachments;
}

