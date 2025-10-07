package com.aurionpro.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "accounts", indexes = @Index(columnList = "accountNumber", unique = true))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @NotBlank(message = "Account number is mandatory")
    @Column(nullable = false, unique = true, length = 20)
    private String accountNumber;

    @NotNull(message = "Account type must be specified")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountType accountType;

    @PositiveOrZero(message = "Balance cannot be negative")
    @Column(name = "balance", precision = 19, scale = 4)
    private BigDecimal balance;

    @Column(nullable = false)
    private boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = true)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = true)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = true)
    private Vendor vendor;

    public enum AccountType {
        ORGANIZATION,
        EMPLOYEE,
        VENDOR
    }
}
