package com.aurionpro.service.impl;

import java.util.Objects;

import com.aurionpro.dtos.EmployeeChangePasswordRequest;
import com.aurionpro.dtos.EmployeeProfileResponse;
import com.aurionpro.dtos.EmployeeProfileUpdateRequest;
import com.aurionpro.entity.Employee;
import com.aurionpro.exceptions.CustomException;
import com.aurionpro.repository.EmployeeRepository;
import com.aurionpro.service.EmployeeProfileService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeProfileServiceImpl implements EmployeeProfileService {

    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public EmployeeProfileServiceImpl(EmployeeRepository employeeRepository,
                                      BCryptPasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public EmployeeProfileResponse getProfile(Long employeeId) {
        Employee e = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new CustomException("Employee not found"));
        return toResponse(e);
    }

    @Override
    @Transactional
    public EmployeeProfileResponse updateProfile(Long employeeId, EmployeeProfileUpdateRequest req) {
        Employee e = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new CustomException("Employee not found"));

        if (req.getName() != null && !req.getName().isBlank()) {
            e.setName(req.getName().trim());
        }
        if (req.getContactNumber() != null && !req.getContactNumber().isBlank()) {
            e.setContactNumber(req.getContactNumber().trim());
        }

        Employee saved = employeeRepository.save(e);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public void changePassword(Long employeeId, EmployeeChangePasswordRequest req) {
        if (!Objects.equals(req.getNewPassword(), req.getConfirmNewPassword())) {
            throw new CustomException("New password and confirm password do not match");
        }
        Employee e = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new CustomException("Employee not found"));
        if (!passwordEncoder.matches(req.getCurrentPassword(), e.getPassword())) {
            throw new CustomException("Current password is incorrect");
        }
        if (passwordEncoder.matches(req.getNewPassword(), e.getPassword())) {
            throw new CustomException("New password must be different from current password");
        }
        e.setPassword(passwordEncoder.encode(req.getNewPassword()));
        employeeRepository.save(e);
    }

    private EmployeeProfileResponse toResponse(Employee e) {
        return EmployeeProfileResponse.builder()
                .id(e.getId())
                .name(e.getName())
                .email(e.getEmail())
                .contactNumber(e.getContactNumber())
                .organizationId(e.getOrganization() != null ? e.getOrganization().getId() : null)
                .organizationName(e.getOrganization() != null ? e.getOrganization().getName() : null)
                .role(e.getRole() != null ? e.getRole().name() : null)
                .build();
    }
}

