package com.aurionpro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
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
    private Long id;

    @NotBlank(message = "Organization name is mandatory")
    @Size(max = 100)
    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Size(max = 100)
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "Phone number is mandatory")
    @Size(min = 10, max = 15)
    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @NotBlank(message = "Status is mandatory")
    @Column(nullable = false, length = 20)
    private String status; // Pending, Verified, Rejected

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<User> users;
}
