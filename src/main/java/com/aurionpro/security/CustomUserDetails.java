package com.aurionpro.security;

import com.aurionpro.entity.Employee;
import com.aurionpro.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private final Employee employee;
    private final User user;

    public CustomUserDetails(Employee employee) {
        this.employee = employee;
        this.user = null;
    }

    public CustomUserDetails(User user) {
        this.user = user;
        this.employee = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (employee != null) {
            return List.of(new SimpleGrantedAuthority("ROLE_" + employee.getRole().name()));
        } else if (user != null) {
            return user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    @Override
    public String getPassword() {
        return employee != null ? employee.getPassword() : user.getPassword();
    }

    @Override
    public String getUsername() {
        return employee != null ? employee.getEmail() : user.getEmail();
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
