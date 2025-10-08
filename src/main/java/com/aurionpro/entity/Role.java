package com.aurionpro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @NotBlank
    @Size(min = 3, max = 50)
    @Pattern(regexp = "^[A-Z_]+$")
    @Column(name = "role_name", nullable = false, length = 50, unique = true)
    private String roleName;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<User> users = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return roleId != null && roleId.equals(role.roleId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
