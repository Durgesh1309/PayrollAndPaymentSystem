package com.aurionpro.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "accounts", indexes = @Index(columnList = "accountnumber", unique = true))
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
    @Column(name="balance")
    private BigDecimal  balance;

    @Column(nullable = false)
    private boolean active = true;
    
  
    @ManyToOne
    @JoinColumn(name="organizationId",nullable=false)
    private Organization organization;
    
    @ManyToOne
    @JoinColumn(name="employeeId",nullable=false)
    private Employee employee;
    
    @OneToOne
    @JoinColumn(name="vendor_id",nullable=false)
    private Vendor vendor;
    
    
    
    public enum AccountType {
        ORGANIZATION,
        EMPLOYEE,
       
    }
}