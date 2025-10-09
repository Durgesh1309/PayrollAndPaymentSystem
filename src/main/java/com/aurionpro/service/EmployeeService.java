package com.aurionpro.service;

import org.springframework.web.multipart.MultipartFile;

import com.aurionpro.dtos.EmployeeAddRequest;
import com.aurionpro.dtos.EmployeeBulkAddResponse;

public interface EmployeeService {
    void addSingleEmployee(EmployeeAddRequest req, String loggedInUserEmail);
    EmployeeBulkAddResponse addEmployeesBulk(Long orgId, MultipartFile file, String loggedInUserEmail);
}
