package com.aurionpro.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class DocumentUploadService {

    private final Cloudinary cloudinary;

    public DocumentUploadService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadPdfFile(MultipartFile file) throws IOException {
        // Basic validations
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        String contentType = file.getContentType();
        if (contentType == null || !MediaType.APPLICATION_PDF_VALUE.equals(contentType)) {
            throw new IllegalArgumentException("Only PDF files are allowed");
        }

        // Upload with explicit resource_type and folder
        Map<String, Object> options = ObjectUtils.asMap(
                "resource_type", "raw",          // use 'raw' for PDFs or non-images
                "folder", "organization-docs"    // change folder structure per org if needed
        );

        Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), options);

        Object secureUrl = uploadResult.get("secure_url");
        if (secureUrl == null) {
            throw new IOException("Upload failed: secure_url missing from Cloudinary response");
        }
        return secureUrl.toString();
    }
}
