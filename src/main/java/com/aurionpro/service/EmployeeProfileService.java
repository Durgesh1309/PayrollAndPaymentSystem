package com.aurionpro.service;

import com.aurionpro.dtos.EmployeeChangePasswordRequest;
import com.aurionpro.dtos.EmployeeProfileResponse;
import com.aurionpro.dtos.EmployeeProfileUpdateRequest;

public interface EmployeeProfileService {
    EmployeeProfileResponse getProfile(Long employeeId);
    EmployeeProfileResponse updateProfile(Long employeeId, EmployeeProfileUpdateRequest req);
    void changePassword(Long employeeId, EmployeeChangePasswordRequest req);
}

