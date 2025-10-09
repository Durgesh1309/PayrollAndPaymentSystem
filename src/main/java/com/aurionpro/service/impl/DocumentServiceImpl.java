package com.aurionpro.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aurionpro.dtos.DocumentResponseDto;
import com.aurionpro.dtos.DocumentVerifyRequest;
import com.aurionpro.dtos.UploadMetadata;
import com.aurionpro.entity.Document;
import com.aurionpro.entity.Employee;
import com.aurionpro.entity.Organization;
import com.aurionpro.exceptions.CustomException;
import com.aurionpro.repository.DocumentRepository;
import com.aurionpro.repository.EmployeeRepository;
import com.aurionpro.repository.OrganizationRepository;
import com.aurionpro.service.DocumentService;
import com.aurionpro.service.DocumentUploadService;

import jakarta.transaction.Transactional;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentUploadService documentUploadService;
    private final DocumentRepository documentRepository;
    private final OrganizationRepository organizationRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    private static final long MAX_SIZE_BYTES = 10 * 1024 * 1024; // 10MB

    public DocumentServiceImpl(DocumentUploadService documentUploadService, DocumentRepository documentRepository,
                               OrganizationRepository organizationRepository,
                               EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.documentUploadService = documentUploadService;
        this.documentRepository = documentRepository;
        this.organizationRepository = organizationRepository;
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public DocumentResponseDto uploadForOrganization(MultipartFile file, UploadMetadata meta, Long id) {
        Organization org;
        Employee emp = null;

        if (id == null) {
            throw new CustomException("Invalid ID provided");
        }

        // Determine if ID is employee or organization
        emp = employeeRepository.findById(id).orElse(null);
        if (emp != null) {
            org = emp.getOrganization();
        } else {
            org = organizationRepository.findById(id)
                    .orElseThrow(() -> new CustomException("Organization not found"));
        }

        try {
            String url = documentUploadService.uploadPdfFile(file);
            Document doc = Document.builder()
                    .documentName(meta.getDocumentName())
                    .documentType(meta.getDocumentType())
                    .documentUrl(url)
                    .status("Pending")
                    .uploadedAt(LocalDateTime.now())
                    .organization(org)
                    .employee(emp)
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

        // Handle both organization and employee
        boolean belongsToEmployee = doc.getEmployee() != null;
        boolean belongsToOrganization = doc.getOrganization() != null;

        if (!belongsToEmployee && !belongsToOrganization) {
            throw new CustomException("Document is not linked to any employee or organization");
        }

        String action = Objects.toString(req.getAction(), "").toUpperCase(Locale.ROOT);

        switch (action) {
            case "APPROVE" -> {
                doc.setStatus("Approved");
                doc.setVerifiedAt(LocalDateTime.now());
                doc.setVerifiedByUserId(verifierUserId);

                // If document belongs to an organization, mark org as verified
                if (belongsToOrganization && !"Verified".equalsIgnoreCase(doc.getOrganization().getStatus())) {
                    doc.getOrganization().setStatus("Verified");
                    organizationRepository.save(doc.getOrganization());
                }

                // If belongs to employee, we can later add employee verification logic
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

    @Override
    public List<DocumentResponseDto> listEmployeeDocuments(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new CustomException("Employee not found"));
        return documentRepository.findByEmployeeOrderByUploadedAtDesc(employee)
                .stream()
                .map(this::toDto)
                .toList();
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

        if (d.getEmployee() != null) {
            dto.setEmployeeId(d.getEmployee().getId());
            dto.setEmployeeName(d.getEmployee().getName());
            dto.setEmployeeEmail(d.getEmployee().getEmail());
        }
        return dto;
    }
    @Override
    @Transactional
    public List<DocumentResponseDto> listEmployeeUploadedDocs(Long organizationId) {
        Organization org = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new CustomException("Organization not found"));

        List<Document> docs = documentRepository.findByOrganizationAndEmployeeIsNotNullOrderByUploadedAtDesc(org);

        return docs.stream().map(this::toDto).toList();
    }

}
