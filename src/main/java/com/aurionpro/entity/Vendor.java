package com.aurionpro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vendors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Document> documents = new HashSet<>();

    @Column(name = "status", nullable = false)
    @Builder.Default
    private boolean status = true;

    @NotBlank(message = "Contact number is mandatory")
    @Pattern(regexp = "\\d{10}", message = "Contact number must be exactly 10 digits")
    @Column(nullable = false)
    private String vendorContactNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @OneToOne(mappedBy = "vendor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private Account account;
}
