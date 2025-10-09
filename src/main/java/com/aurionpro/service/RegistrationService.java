package com.aurionpro.service;

import com.aurionpro.dtos.OrganizationRegistrationDto;
import com.aurionpro.entity.User;

public interface RegistrationService {
    User registerOrganization(OrganizationRegistrationDto dto);
}
