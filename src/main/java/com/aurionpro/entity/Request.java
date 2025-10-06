package com.aurionpro.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;
    
    @Column(name="request_type")
    private String requestType;
    
    @Column(name="request_status")
    private String requestStatus;
    
    @Column(name="description")
    private String description;
    
    @Column(name="request_date")
    private LocalDate requestDate;
    
    @Column(name="action_date")
    private LocalDate actionDate;
    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adminId")
    private Admin admin;


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="organizationId",nullable=false)
    private Organization organization;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeId", nullable = false)
    private Employee employee;
    
    
}

