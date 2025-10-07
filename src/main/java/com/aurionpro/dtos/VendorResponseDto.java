package com.aurionpro.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorResponseDto {

    private Long vendorId;

    private String vendorName;

    private String vendorEmail;

    private String vendorContactNo;

    private boolean status;

    private List<String> documents;
}
