package com.aurionpro.controller;

import java.security.Principal;

import com.aurionpro.dtos.EmployeeChangePasswordRequest;
import com.aurionpro.dtos.EmployeeProfileResponse;
import com.aurionpro.dtos.EmployeeProfileUpdateRequest;
import com.aurionpro.exceptions.CustomException;
import com.aurionpro.security.CustomUserDetails;
import com.aurionpro.service.EmployeeProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees/me")
@PreAuthorize("hasRole('EMPLOYEE')")
public class EmployeeProfileController {

    private final EmployeeProfileService profileService;

    public EmployeeProfileController(EmployeeProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<EmployeeProfileResponse> getMyProfile(Principal principal) {
        Long employeeId = extractEmployeeId(principal);
        return ResponseEntity.ok(profileService.getProfile(employeeId));
    }

    @PutMapping
    public ResponseEntity<EmployeeProfileResponse> updateMyProfile(
            @Valid @RequestBody EmployeeProfileUpdateRequest req,
            Principal principal) {
        Long employeeId = extractEmployeeId(principal);
        return ResponseEntity.ok(profileService.updateProfile(employeeId, req));
    }

    @PutMapping("/password")
    public ResponseEntity<?> changePassword(
            @Valid @RequestBody EmployeeChangePasswordRequest req,
            Principal principal) {
        Long employeeId = extractEmployeeId(principal);
        profileService.changePassword(employeeId, req);
        return ResponseEntity.ok("Password updated successfully");
    }

    private Long extractEmployeeId(Principal principal) {
        if (principal instanceof Authentication auth
                && auth.getPrincipal() instanceof CustomUserDetails cud) {
            Long eid = cud.getEmployeeId();
            if (eid == null) throw new CustomException("Employee identity not found in token");
            return eid;
        }
        throw new CustomException("Authentication principal missing");
    }
}

