package com.aurionpro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

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

    @OneToOne(mappedBy = "salaryComponent")
    private SalaryStructure salaryStructure;

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

    @Column(nullable = false)
    private Double netSalary;

    public Double calculateNetSalary() {
        return basicSalary + hra + dearnessAllowance + otherAllowances - pf;
    }

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
