package com.aurionpro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "organizations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long organizationId;

    @NotBlank
    @Size(min = 2, max = 120)
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Organization name must contain only letters and spaces")
    @Column(name = "organization_name", nullable = false, length = 120)
    private String organizationName;

    @NotBlank
    @Email
    @Size(max = 254)
    @Column(nullable = false, length = 254, unique = true)
    private String organizationEmail;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Vendor> vendors = new HashSet<>();

    @OneToMany(mappedBy = "organization")
    @Builder.Default
    private Set<Document> documents = new HashSet<>();

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Request> requests = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
