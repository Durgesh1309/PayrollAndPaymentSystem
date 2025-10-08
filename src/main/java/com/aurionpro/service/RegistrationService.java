package com.aurionpro.service;

import java.util.HashSet;

import org.springframework.stereotype.Service;

import com.aurionpro.dtos.OrganizationRegistrationDto;
import com.aurionpro.entity.Organization;
import com.aurionpro.entity.Role;
import com.aurionpro.entity.User;
import com.aurionpro.exceptions.CustomException;
import com.aurionpro.repository.OrganizationRepository;
import com.aurionpro.repository.RoleRepository;
import com.aurionpro.repository.UserRepository;
import com.aurionpro.util.PasswordEncoderUtil;

import jakarta.transaction.Transactional;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
   private final OrganizationRepository organizationRepository;
    public RegistrationService(UserRepository userRepository, RoleRepository roleRepository,OrganizationRepository organizationRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.organizationRepository=organizationRepository;
    }

    @Transactional
    public User registerOrganization(OrganizationRegistrationDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new CustomException("Email already exists");
        }

        if (organizationRepository.existsByEmail(dto.getEmail())) {
            throw new CustomException("Organization email already exists");
        }

        // Save Organization entity
        Organization org = new Organization();
        org.setName(dto.getOrganizationName());
        org.setEmail(dto.getEmail());
        org.setPhoneNumber(dto.getContactNumber());
        org.setStatus("Pending");
        organizationRepository.save(org);

        Role orgRole = roleRepository.findByRoleName("ROLE_ORGANIZATION")
                .orElseThrow(() -> new CustomException("Organization Role not defined"));

        // Create User mapped to Organization entity - no duplicate name/status fields
        User user = User.builder()
                .userName(dto.getUserName())
                .email(dto.getEmail())
                .password(PasswordEncoderUtil.encodePassword(dto.getPassword()))
                .organization(org)  // ManyToOne mapping
                .contactNumber(dto.getContactNumber())
                .roles(new HashSet<>())
                .build();

        user.getRoles().add(orgRole);

        return userRepository.save(user);
    }

}
