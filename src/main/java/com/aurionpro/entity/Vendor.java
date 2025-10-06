package com.aurionpro.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="vendors")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vendorId;
    
    @NotBlank
    @Size(min = 2, max = 120)
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Vendor name must contain only letters and spaces")
    @Column(name = "vendor_name", nullable = false, length = 120)
    private String vendorName;
    
    @NotBlank
    @Email
    @Size(max = 254)
    @Column(nullable = false, length = 254, unique = true)
    private String vendorEmail;
    
    @OneToMany(mappedBy="vendor",cascade=CascadeType.ALL)
    @Builder.Default
    private Set<Document>documents=new HashSet<>();
    
    @Column(name = "status", nullable = false)
    @Builder.Default
    private boolean status = true;
    
    @NotBlank(message = "Contact number is mandatory")
    @Pattern(regexp = "\\d{10}", message = "Contact number must be exactly 10 digits")
    @Column(nullable = false)
    private String vendorContactno;
    
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="organizationId",nullable=false)
    private Organization organization;
    
    @OneToOne(mappedBy = "vendor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private Account account;
	
}
