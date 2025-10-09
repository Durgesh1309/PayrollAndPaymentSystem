package com.aurionpro.repository;

import com.aurionpro.entity.Document;
import com.aurionpro.entity.Employee;
import com.aurionpro.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    
    List<Document> findByOrganizationOrderByUploadedAtDesc(Organization organization);

    List<Document> findByStatusOrderByUploadedAtAsc(String status);

    List<Document> findByEmployeeOrderByUploadedAtDesc(Employee employee);  // Add this method
    List<Document> findByOrganizationAndEmployeeIsNotNullOrderByUploadedAtDesc(Organization organization);

    
}
