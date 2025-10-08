package com.aurionpro.controller;

import com.aurionpro.dtos.OrganizationRegistrationDto;
import com.aurionpro.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    private final RegistrationService registrationService;

    public OrganizationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerOrganization(@Valid @RequestBody OrganizationRegistrationDto dto) {
        registrationService.registerOrganization(dto);
        return ResponseEntity.ok("Organization registered successfully and status is Pending.");
    }
}
