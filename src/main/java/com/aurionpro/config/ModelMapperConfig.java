package com.aurionpro.config;

import com.aurionpro.dtos.OrganizationRegistrationDto;
import com.aurionpro.entity.Organization;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // Mapping DTO organizationName -> Organization name, contactNumber -> phoneNumber
        mapper.addMappings(new PropertyMap<OrganizationRegistrationDto, Organization>() {
            @Override
            protected void configure() {
                map().setName(source.getOrganizationName());
                map().setPhoneNumber(source.getContactNumber());
            }
        });

        return mapper;
    }
}
