package com.aurionpro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurionpro.entity.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    boolean existsByEmail(String email);
    Optional<Organization> findByEmail(String email);
}
