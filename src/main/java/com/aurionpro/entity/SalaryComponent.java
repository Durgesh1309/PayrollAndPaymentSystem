package com.aurionpro.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "salary_components")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salaryComponentId;

    
    @OneToOne(mappedBy="salaryComponent")
    private SalaryStructure salaryStructure;
    
    // Fixed salary components
    @Column(nullable = false)
    private Double basicSalary;

    @Column(nullable = false)
    private Double hra;

    @Column(nullable = false)
    private Double dearnessAllowance;

    @Column(nullable = false)
    private Double pf;

    @Column(nullable = false)
    private Double otherAllowances;
    

    // Optional: store net salary for historical record
    @Column(nullable = false)
    private Double netSalary;

    // Method to calculate net salary dynamically if needed
    public Double calculateNetSalary() {
        return basicSalary + hra + dearnessAllowance + otherAllowances - pf;
    }
    
    // Equals & hashCode based on PK
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SalaryComponent)) return false;
        SalaryComponent sc = (SalaryComponent) o;
        return salaryComponentId != null && salaryComponentId.equals(sc.salaryComponentId);
    }

    @Override
    public int hashCode() {
        return 31;
    }
    
    
}
