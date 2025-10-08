package com.aurionpro.controller;

import com.aurionpro.dtos.DocumentResponseDto;
import com.aurionpro.dtos.DocumentVerifyRequest;
import com.aurionpro.dtos.UploadMetadata;
import com.aurionpro.service.DocumentService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documents;

    public DocumentController(DocumentService documents) {
        this.documents = documents;
    }

    @PreAuthorize("hasRole('ORGANIZATION')")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentResponseDto> upload(
            @RequestPart("file") MultipartFile file,
            @Valid @RequestPart("meta") UploadMetadata meta,
            Principal principal) {
        // Optionally validate the principal belongs to the organization in meta
        return ResponseEntity.ok(documents.uploadForOrganization(file, meta));
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{documentId}/verify")
    public ResponseEntity<DocumentResponseDto> verify(
            @PathVariable Long documentId,
            @Valid @RequestBody DocumentVerifyRequest req,
            Principal principal) {
        // Look up verifierUserId from principal if needed
        Long verifierUserId = null;
        return ResponseEntity.ok(documents.verify(documentId, req, verifierUserId));
    }
}

