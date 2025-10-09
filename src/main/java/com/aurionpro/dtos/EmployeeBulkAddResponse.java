package com.aurionpro.dtos;

import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EmployeeBulkAddResponse {
    private List<String> successEmails;
    private List<String> failedEmails;
}
