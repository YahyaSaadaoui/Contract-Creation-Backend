package com.hps.admindashboardservice.services;


import com.hps.admindashboardservice.entities.*;
import com.hps.admindashboardservice.repos.userRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private userRepo userRepository;

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve user from the database
        user user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.error("User not found: {}", username);
                    return new UsernameNotFoundException("User not found");
                });
        logger.debug("User found: {}", user.getUsername());
        logger.debug("User password hash: {}", user.getPassword());
        // Extract roles from UserRole and Role entities
        Set<String> roles = user.getUserRoles().stream()
                .map(userRole -> userRole.getRole().getLabel())
                .collect(Collectors.toSet());
        logger.debug("User roles: {}", roles);
        // Extract permissions from RolePermission through UserRole and Permission entities
        Set<String> permissions = user.getUserRoles().stream()
                .flatMap(userRole -> userRole.getRole().getRolePermissions().stream())
                .map(rolePermission -> rolePermission.getPermission().getLabel())
                .collect(Collectors.toSet());
        logger.debug("User permissions: {}", permissions);
        // Create authorities for Spring Security
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
        permissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));
        logger.debug("Granted authorities: {}", authorities);
        // Return the user details for Spring Security
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }

}
