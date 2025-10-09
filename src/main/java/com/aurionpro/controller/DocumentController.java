package com.aurionpro.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aurionpro.dtos.DocumentResponseDto;
import com.aurionpro.dtos.DocumentVerifyRequest;
import com.aurionpro.dtos.UploadMetadata;
import com.aurionpro.security.CustomUserDetails;
import com.aurionpro.service.DocumentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documents;

    public DocumentController(DocumentService documents) {
        this.documents = documents;
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ORGANIZATION')")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentResponseDto> upload(
            @RequestPart("file") MultipartFile file,
            @Valid @RequestPart("meta") UploadMetadata meta,
            Principal principal) {

        Long employeeId = getLoggedInEmployeeId(principal);
        Long organizationId = getLoggedInOrganizationId(principal);

        if (employeeId != null) {
            return ResponseEntity.ok(documents.uploadForOrganization(file, meta, employeeId));
        } else if (organizationId != null) {
            return ResponseEntity.ok(documents.uploadForOrganization(file, meta, organizationId));
        } else {
            throw new IllegalStateException("Logged in user has neither employee nor organization ID");
        }
    }
    
    private Long getLoggedInOrganizationId(Principal principal) {
        if (principal instanceof Authentication authentication) {
            Object userObject = authentication.getPrincipal();
            if (userObject instanceof CustomUserDetails customUserDetails) {
                return customUserDetails.getOrganizationId();
            }
        }
        return null;
    }


    @PreAuthorize("hasRole('ORGANIZATION')")
    @GetMapping("/organization/{orgId}")
    public ResponseEntity<List<DocumentResponseDto>> listOrg(@PathVariable Long orgId) {
        return ResponseEntity.ok(documents.listOrganizationDocs(orgId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pending")
    public ResponseEntity<List<DocumentResponseDto>> listPending() {
        return ResponseEntity.ok(documents.listPendingDocs());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZATION')")
    @PostMapping("/{documentId}/verify")
    public ResponseEntity<DocumentResponseDto> verify(
            @PathVariable Long documentId,
            @Valid @RequestBody DocumentVerifyRequest req,
            Principal principal) {

        Long verifierUserId = getLoggedInVerifierUserId(principal);
        return ResponseEntity.ok(documents.verify(documentId, req, verifierUserId));
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/employee/documents")
    public ResponseEntity<List<DocumentResponseDto>> listEmployeeDocs(Principal principal) {
        Long employeeId = getLoggedInEmployeeId(principal);
        return ResponseEntity.ok(documents.listEmployeeDocuments(employeeId));
    }

    private Long getLoggedInEmployeeId(Principal principal) {
        if (principal instanceof Authentication authentication) {
            Object userObject = authentication.getPrincipal();
            if (userObject instanceof CustomUserDetails customUserDetails) {
                return customUserDetails.getEmployeeId();
            }
        }
        return null;
    }

    private Long getLoggedInVerifierUserId(Principal principal) {
        if (principal instanceof Authentication authentication) {
            Object userObject = authentication.getPrincipal();
            if (userObject instanceof CustomUserDetails customUserDetails) {
                return customUserDetails.getUserId();
            }
        }
        return null;
    }
    @PreAuthorize("hasRole('ORGANIZATION')")
    @GetMapping("/organization/employee/{orgId}")
    public ResponseEntity<List<DocumentResponseDto>> listEmployeeDocsForOrg(@PathVariable Long orgId) {
        return ResponseEntity.ok(documents.listEmployeeUploadedDocs(orgId));
    }

}
