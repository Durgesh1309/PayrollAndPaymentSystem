package com.aurionpro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
    name = "user",
    indexes = @Index(name = "idx_users_email", columnList = "email"),
    uniqueConstraints = @UniqueConstraint(name = "uk_users_email", columnNames = {"email"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank
    @Size(min = 2, max = 120)
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Username must contain only letters and spaces")
    @Column(name = "user_name", nullable = false, length = 120)
    private String userName;

    @NotBlank
    @Email
    @Size(max = 254)
    @Column(nullable = false, length = 254, unique = true)
    private String email;

    @NotBlank
    @Size(min = 8, max = 120)
    @Column(nullable = false, length = 120)
    private String password;

    @Size(max = 80)
    @Column(length = 80)
    private String title;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @Column(name = "contact_number", length = 15)
    private String contactNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return userId != null && userId.equals(user.userId);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
