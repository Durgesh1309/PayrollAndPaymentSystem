package com.aurionpro.service;

import com.aurionpro.dtos.DocumentResponseDto;
import com.aurionpro.dtos.DocumentVerifyRequest;
import com.aurionpro.dtos.UploadMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {
    DocumentResponseDto uploadForOrganization(MultipartFile file, UploadMetadata meta);
    List<DocumentResponseDto> listOrganizationDocs(Long orgId);
    List<DocumentResponseDto> listPendingDocs();
    DocumentResponseDto verify(Long documentId, DocumentVerifyRequest req, Long verifierUserId);
}
