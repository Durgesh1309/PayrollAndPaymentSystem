package com.aurionpro.service.impl;

import com.aurionpro.dtos.OrganizationRegistrationDto;
import com.aurionpro.entity.Organization;
import com.aurionpro.entity.Role;
import com.aurionpro.entity.User;
import com.aurionpro.exceptions.CustomException;
import com.aurionpro.repository.OrganizationRepository;
import com.aurionpro.repository.RoleRepository;
import com.aurionpro.repository.UserRepository;
import com.aurionpro.service.RegistrationService;
import com.aurionpro.util.PasswordEncoderUtil;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final OrganizationRepository organizationRepository;
    private final ModelMapper modelMapper;

    public RegistrationServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                                   OrganizationRepository organizationRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.organizationRepository = organizationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public User registerOrganization(OrganizationRegistrationDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new CustomException("Email already exists");
        }
        if (organizationRepository.existsByEmail(dto.getEmail())) {
            throw new CustomException("Organization email already exists");
        }
        Organization org = modelMapper.map(dto, Organization.class);
        org.setStatus("Pending");
        organizationRepository.save(org);
        Role orgRole = roleRepository.findByRoleName("ROLE_ORGANIZATION")
                .orElseThrow(() -> new CustomException("Organization Role not defined"));

        User user = User.builder()
                .userName(dto.getUserName())
                .email(dto.getEmail())
                .password(PasswordEncoderUtil.encodePassword(dto.getPassword()))
                .organization(org)
                .contactNumber(dto.getContactNumber())
                .roles(new HashSet<>())
                .build();
        user.getRoles().add(orgRole);

        return userRepository.save(user);
    }
}
