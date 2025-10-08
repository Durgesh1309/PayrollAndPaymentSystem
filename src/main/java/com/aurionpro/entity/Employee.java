package com.aurionpro.entity;

import com.aurionpro.enums.RoleType;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Employee name is mandatory")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Size(max = 100)
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Contact number is mandatory")
    @Size(min = 10, max = 15)
    @Column(nullable = false, length = 15)
    private String contactNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="organization_id", nullable=false)
    private Organization organization;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RoleType role;
}
