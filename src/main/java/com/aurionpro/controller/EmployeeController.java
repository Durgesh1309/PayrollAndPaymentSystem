package com.aurionpro.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aurionpro.dtos.EmployeeAddRequest;
import com.aurionpro.dtos.EmployeeBulkAddResponse;
import com.aurionpro.exceptions.CustomException;
import com.aurionpro.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) { this.employeeService = employeeService; }
    @PostMapping("/add")
    public ResponseEntity<?> addSingle(@Valid @RequestBody EmployeeAddRequest req, Principal principal) {
        try {
            employeeService.addSingleEmployee(req, principal.getName());
            return ResponseEntity.ok("Employee added and credentials emailed");
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PreAuthorize("hasRole('ORGANIZATION')")
    @PostMapping("/add/bulk")
    public ResponseEntity<EmployeeBulkAddResponse> addBulk(
        @RequestParam Long organizationId,
        @RequestParam MultipartFile file,
        Principal principal) {
        String userEmail = principal.getName();
        EmployeeBulkAddResponse response = employeeService.addEmployeesBulk(organizationId, file, userEmail);
        return ResponseEntity.ok(response);
    }
}
