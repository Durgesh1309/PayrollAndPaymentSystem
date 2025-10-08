//package com.aurionpro.entity;
//
//import java.math.BigDecimal;
//
//import com.aurionpro.enums.AccountType;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Index;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.PositiveOrZero;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Table(name = "accounts", indexes = @Index(columnList = "accountNumber", unique = true))
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class Account {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long accountId;
//
//    @NotBlank(message = "Account number is mandatory")
//    @Column(nullable = false, unique = true, length = 20)
//    private String accountNumber;
//
////    @NotNull(message = "Account type must be specified")
////    @Enumerated(EnumType.STRING)
////    @Column(nullable = false, length = 20)
////    private AccountType accountType;
//
//    @PositiveOrZero(message = "Balance cannot be negative")
//    @Column(name = "balance", precision = 19, scale = 4)
//    private BigDecimal balance;
//
//    @Column(nullable = false)
//    private boolean active = true;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "organization_id", nullable = true)
//    private Organization organization;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "employee_id", nullable = true)
//    private Employee employee;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "vendor_id", nullable = true)
//    private Vendor vendor;
//
//   
//}
