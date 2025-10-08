package com.aurionpro.security;

import com.aurionpro.entity.Employee;
import com.aurionpro.entity.User;
import com.aurionpro.repository.EmployeeRepository;
import com.aurionpro.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    public CustomUserDetailsService(EmployeeRepository employeeRepository,
                                    UserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Try Employee first
        Employee employee = employeeRepository.findByEmail(email).orElse(null);
        if (employee != null) {
            return new CustomUserDetails(employee);
        }
        // Then User (Organization/Admin) with roles fetch-joined
        User user = userRepository.findByEmailWithRoles(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new CustomUserDetails(user);
    }
}
