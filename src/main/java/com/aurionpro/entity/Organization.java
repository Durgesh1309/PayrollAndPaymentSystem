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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="organizations")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Organization {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long organizationId;
	
	    @NotBlank
	    @Size(min = 2, max = 120)
	    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Organization name must contain only letters and spaces")
	    @Column(name = "organization_name", nullable = false, length = 120)
	    private String organizationName;
	    
	    @NotBlank
	    @Email
	    @Size(max = 254)
	    @Column(nullable = false, length = 254, unique = true)
	    private String organizationEmail;
	    
	    @OneToMany(mappedBy="organization",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	    @Builder.Default
	    private Set<Account> accounts = new HashSet<>();
	      
	    @OneToMany(mappedBy="organization",cascade=CascadeType.ALL)
	    @Builder.Default
	    private Set<Vendor> vendors=new HashSet<>();
	    
	    @OneToMany(mappedBy="organization")
	    @Builder.Default
	    private Set<Document> documents=new HashSet<>();
	    
	    @OneToMany(mappedBy="organization",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	    @Builder.Default
	    private Set<Request> requests = new HashSet<>();
	    
	    @OneToOne(fetch=FetchType.LAZY)
	    @JoinColumn(name="userId",nullable=false)
	    private User user;
	    
}

