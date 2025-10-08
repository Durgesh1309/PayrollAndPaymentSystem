package com.aurionpro.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String documentName;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String documentType;  // e.g., "License", "Certificate", etc.

    @NotBlank
    @Column(nullable = false, length = 3000)
    private String documentUrl;   // Singluar URL or if multiple URLs use JSON string/list representation

    @Column(nullable = false)
    private LocalDateTime uploadedAt; // Stores upload date/time of the document

    @Column(nullable = false, length = 30)
    private String status; // e.g., "Pending", "Verified", "Rejected"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = true)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = true)
    private Employee employee;

}
