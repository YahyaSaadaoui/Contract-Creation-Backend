package com.hps.admindashboardservice.controllers;

import com.hps.admindashboardservice.dto.JwtResponse;
import com.hps.admindashboardservice.dto.LoginRequest;
import com.hps.admindashboardservice.dto.UserDTO;

import com.hps.admindashboardservice.security.JwtTokenProvider;
import com.hps.admindashboardservice.services.AdminDashboardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class AdminDashboardController {

    @Autowired
    private AdminDashboardService adminDashboardService;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;



    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = adminDashboardService.createUser(userDTO);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable long id, @RequestBody UserDTO userDTO) {
        userDTO.setId(id); // Ensure ID is set in DTO
        UserDTO updatedUser = adminDashboardService.updateUser(userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        adminDashboardService.deleteUser(id);
        return ResponseEntity.ok("User deleted event published");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(loginRequest.getUsername(), authentication.getAuthorities().toString());

        return ResponseEntity.ok(new JwtResponse(token));
    }
}
