package com.aurionpro.service.impl;

import com.aurionpro.dtos.DocumentResponseDto;
import com.aurionpro.dtos.DocumentVerifyRequest;
import com.aurionpro.dtos.UploadMetadata;
import com.aurionpro.entity.Document;
import com.aurionpro.entity.Organization;
import com.aurionpro.exceptions.CustomException;
import com.aurionpro.repository.DocumentRepository;
import com.aurionpro.repository.OrganizationRepository;
import com.aurionpro.service.DocumentService;
import com.aurionpro.service.DocumentUploadService;
import jakarta.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
public class DocumentServiceImpl implements DocumentService {
    
    private final DocumentUploadService documentUploadService;
    private final DocumentRepository documentRepository;
    private final OrganizationRepository organizationRepository;

    private final ModelMapper modelMapper;

    private static final long MAX_SIZE_BYTES = 10 * 1024 * 1024; // 10MB

    public DocumentServiceImpl(DocumentUploadService documentUploadService, DocumentRepository documentRepository,
                               OrganizationRepository organizationRepository, ModelMapper modelMapper) {
        this.documentUploadService = documentUploadService;
        this.documentRepository = documentRepository;
        this.organizationRepository = organizationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public DocumentResponseDto uploadForOrganization(MultipartFile file, UploadMetadata meta) {
        if (file == null || file.isEmpty()) throw new CustomException("File is required");
        String contentType = Objects.toString(file.getContentType(), "");
        if (!MediaType.APPLICATION_PDF_VALUE.equals(contentType))
            throw new CustomException("Only PDF files are allowed");
        if (file.getSize() > MAX_SIZE_BYTES)
            throw new CustomException("File exceeds max allowed size 10MB");
        if (meta == null || meta.getOrganizationId() == null)
            throw new CustomException("organizationId is required");

        Organization org = organizationRepository.findById(meta.getOrganizationId())
                .orElseThrow(() -> new CustomException("Organization not found"));

        try {
            String url = documentUploadService.uploadPdfFile(file);
            Document doc = Document.builder()
                    .documentName(meta.getDocumentName())
                    .documentType(meta.getDocumentType())
                    .documentUrl(url)
                    .status("Pending")
                    .uploadedAt(LocalDateTime.now())
                    .organization(org)
                    .build();
            Document saved = documentRepository.save(doc);
            return toDto(saved);
        } catch (Exception e) {
            throw new CustomException("Upload failed: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<DocumentResponseDto> listOrganizationDocs(Long orgId) {
        Organization org = organizationRepository.findById(orgId)
                .orElseThrow(() -> new CustomException("Organization not found"));
        return documentRepository.findByOrganizationOrderByUploadedAtDesc(org)
                .stream().map(this::toDto).toList();
    }

    @Override
    @Transactional
    public List<DocumentResponseDto> listPendingDocs() {
        return documentRepository.findByStatusOrderByUploadedAtAsc("Pending")
                .stream().map(this::toDto).toList();
    }

    @Override
    @Transactional
    public DocumentResponseDto verify(Long documentId, DocumentVerifyRequest req, Long verifierUserId) {
        Document doc = documentRepository.findById(documentId)
                .orElseThrow(() -> new CustomException("Document not found"));
        String action = Objects.toString(req.getAction(), "").toUpperCase(Locale.ROOT);
        switch (action) {
            case "APPROVE" -> {
                doc.setStatus("Approved");
                doc.setVerifiedAt(LocalDateTime.now());
                doc.setVerifiedByUserId(verifierUserId);
                if (!"Verified".equalsIgnoreCase(doc.getOrganization().getStatus())) {
                    doc.getOrganization().setStatus("Verified");
                    organizationRepository.save(doc.getOrganization());
                }
            }
            case "REJECT" -> {
                if (req.getRejectionReason() == null || req.getRejectionReason().isBlank()) {
                    throw new CustomException("Rejection reason is required");
                }
                doc.setStatus("Rejected");
                doc.setVerifiedAt(LocalDateTime.now());
                doc.setVerifiedByUserId(verifierUserId);
                doc.setRejectionReason(req.getRejectionReason());
            }
            default -> throw new CustomException("Invalid action, must be APPROVE or REJECT");
        }
        return toDto(documentRepository.save(doc));
    }

    private DocumentResponseDto toDto(Document d) {
        DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        DocumentResponseDto dto = new DocumentResponseDto();
        dto.setDocumentId(d.getDocumentId());
        dto.setDocumentName(d.getDocumentName());
        dto.setDocumentType(d.getDocumentType());
        dto.setDocumentUrl(d.getDocumentUrl());
        dto.setStatus(d.getStatus());
        dto.setRejectionReason(d.getRejectionReason());
        dto.setOrganizationId(d.getOrganization() != null ? d.getOrganization().getId() : null);
        dto.setUploadedAt(d.getUploadedAt() != null ? d.getUploadedAt().format(fmt) : null);
        dto.setVerifiedAt(d.getVerifiedAt() != null ? d.getVerifiedAt().format(fmt) : null);
        dto.setVerifiedByUserId(d.getVerifiedByUserId());
        return dto;
    }
}
