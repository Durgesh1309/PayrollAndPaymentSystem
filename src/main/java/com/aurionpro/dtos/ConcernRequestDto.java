package com.aurionpro.dtos;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConcernRequestDto {

    @NotNull
    private Long employeeId;

    @NotBlank
    @Size(max = 500)
    private String description;

    private List<String> attachments;
}
