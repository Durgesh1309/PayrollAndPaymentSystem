package com.aurionpro.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
public class Document {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long documentId;
	  
	    @NotNull
	    private String documentName;
	    
	    @NotNull
	    private String documentType;
	    
	    @NotNull
	    private String documentUrls;
	    
	    @ManyToOne
	    @JoinColumn(name = "organizationId", nullable = false)
	    private Organization organization;
	    
	    @ManyToOne
	    @JoinColumn(name = "employeeId", nullable = false)
	    private Employee employee;
	    
	    @ManyToOne
	    @JoinColumn(name = "vendorId", nullable = false)
	    private Vendor vendor;
	    

	  
}