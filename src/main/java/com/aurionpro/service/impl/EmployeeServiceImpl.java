package com.aurionpro.service.impl;

import com.aurionpro.dtos.EmployeeAddRequest;
import com.aurionpro.dtos.EmployeeBulkAddResponse;
import com.aurionpro.entity.Employee;
import com.aurionpro.entity.Organization;
import com.aurionpro.entity.User;
import com.aurionpro.enums.RoleType;
import com.aurionpro.exceptions.CustomException;
import com.aurionpro.repository.EmployeeRepository;
import com.aurionpro.repository.OrganizationRepository;
import com.aurionpro.repository.UserRepository;
import com.aurionpro.service.EmailerService;
import com.aurionpro.service.EmployeeService;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final EmailerService emailerService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private static final int TEMP_PASSWORD_LENGTH = 12;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, OrganizationRepository organizationRepository,
                              UserRepository userRepository, EmailerService emailerService,
                              BCryptPasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.organizationRepository = organizationRepository;
        this.userRepository = userRepository;
        this.emailerService = emailerService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void addSingleEmployee(EmployeeAddRequest req, String loggedInUserEmail) {
        Long loggedInOrgId = getOrganizationIdForUser(loggedInUserEmail);
        if (loggedInOrgId == null || !loggedInOrgId.equals(req.getOrganizationId())) {
            throw new CustomException("Cannot add employees for other organizations");
        }
        Organization org = organizationRepository.findById(req.getOrganizationId())
                .orElseThrow(() -> new CustomException("Organization not found"));
        if (!"Verified".equalsIgnoreCase(org.getStatus())) {
            throw new CustomException("Organization is not verified");
        }
        if (employeeRepository.existsByEmail(req.getEmail())) {
            throw new CustomException("Employee with this email already exists");
        }
        String tempPassword = generateTemporaryPassword();
        Employee emp = modelMapper.map(req, Employee.class);
        emp.setOrganization(org);
        emp.setPassword(passwordEncoder.encode(tempPassword));
        emp.setRole(RoleType.EMPLOYEE);
        employeeRepository.save(emp);
        try {
            emailerService.sendWelcomeEmailWithTempPassword(emp.getEmail(), tempPassword, org.getName());
        } catch (MessagingException ex) {
            throw new CustomException("Failed to send credentials email: " + ex.getMessage());
        }
    }

    @Override
    @Transactional
    public EmployeeBulkAddResponse addEmployeesBulk(Long orgId, MultipartFile file, String loggedInUserEmail) {
        Long loggedInOrgId = getOrganizationIdForUser(loggedInUserEmail);
        if (loggedInOrgId == null || !loggedInOrgId.equals(orgId)) {
            throw new CustomException("Cannot add employees for other organizations");
        }
        Organization org = organizationRepository.findById(orgId)
                .orElseThrow(() -> new CustomException("Organization not found"));
        if (!"Verified".equalsIgnoreCase(org.getStatus())) {
            throw new CustomException("Organization is not verified");
        }
        List<String> success = new ArrayList<>();
        List<String> failed = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] cols = line.split(",");
                if (cols.length < 3) {
                    failed.add("Invalid line: " + line);
                    continue;
                }
                String name = cols[0].trim(), email = cols[1].trim(), phone = cols[2].trim();
                if (employeeRepository.existsByEmail(email)) {
                    failed.add(email);
                    continue;
                }
                String tempPassword = generateTemporaryPassword();
                Employee emp = new Employee();
                emp.setName(name);
                emp.setEmail(email);
                emp.setContactNumber(phone);
                emp.setOrganization(org);
                emp.setPassword(passwordEncoder.encode(tempPassword));
                emp.setRole(RoleType.EMPLOYEE);
                employeeRepository.save(emp);
                try {
                    emailerService.sendWelcomeEmailWithTempPassword(email, tempPassword, org.getName());
                    success.add(email);
                } catch (Exception ex) {
                    failed.add(email + " (mail error)");
                }
            }
        } catch (Exception e) {
            throw new CustomException("Error processing file: " + e.getMessage());
        }
        return EmployeeBulkAddResponse.builder()
                .successEmails(success)
                .failedEmails(failed)
                .build();
    }

    private Long getOrganizationIdForUser(String userEmail) {
        Optional<Employee> emp = employeeRepository.findByEmail(userEmail);
        if (emp.isPresent() && "Verified".equalsIgnoreCase(emp.get().getOrganization().getStatus())) {
            return emp.get().getOrganization().getId();
        }
        Optional<User> user = userRepository.findByEmail(userEmail);
        if (user.isPresent() && user.get().getOrganization() != null
                && "Verified".equalsIgnoreCase(user.get().getOrganization().getStatus())) {
            return user.get().getOrganization().getId();
        }
        return null;
    }

    private String generateTemporaryPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(TEMP_PASSWORD_LENGTH);
        for (int i = 0; i < TEMP_PASSWORD_LENGTH; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }
}
