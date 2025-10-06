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
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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

//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    @Builder.Default
//    private Set<Account> accounts = new HashSet<>();

//    @OneToMany(mappedBy = "admin", fetch = FetchType.LAZY)
//    @Builder.Default
//    private Set<Request> approvals = new HashSet<>();

//    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, optional = true)
//    private Document document;
    
    
    @OneToOne(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY,optional=true)
    private Organization organization;
    
    @OneToOne(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY,optional=true)
    private Admin admin;
    
    @OneToOne(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY,optional=true)
    private Employee employee;

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

