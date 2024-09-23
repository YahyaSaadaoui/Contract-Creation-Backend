package com.hps.admindashboardservice.services;

import com.hps.admindashboardservice.dto.AuthRequest;
import com.hps.admindashboardservice.dto.LoginRequest;

import com.hps.admindashboardservice.entities.user;
import com.hps.admindashboardservice.security.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public String authenticate(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        user user = (user) authentication.getPrincipal();
        return jwtTokenProvider.generateToken(user);
    }
}
