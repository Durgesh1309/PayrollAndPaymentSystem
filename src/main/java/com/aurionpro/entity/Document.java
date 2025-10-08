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
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Column(nullable = false)
    private String documentName;

    @NotNull
    @Column(nullable = false)
    private String documentType;  // e.g., PAN Card, License, Tax ID

    @NotNull
    @Column(nullable = false)
    private String documentUrl;

    @Column(length = 20)
    private String status;  // Pending, Approved, Rejected

    @Column
    private LocalDateTime uploadedAt;

    @Column
    private LocalDateTime verifiedAt;
    
    @Column(length = 255)
    private String rejectionReason;
    private Long verifiedByUserId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "vendor_id")
//    private Vendor vendor;
}
