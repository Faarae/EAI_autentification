package com.example.inventory.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.inventory.dto.LoginRequest;
import com.example.inventory.dto.LoginResponse;
import com.example.inventory.dto.RegisterRequest;
import com.example.inventory.entity.User;
import com.example.inventory.repository.UserRepository;
import com.example.inventory.security.JwtTokenProvider;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    @Autowired
    private TokenBlacklistService tokenBlacklistService;
    
    public LoginResponse login(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());
        
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        
        User foundUser = user.get();
        
        if (!foundUser.getIsActive()) {
            throw new RuntimeException("User account is inactive");
        }
        
        if (!passwordEncoder.matches(loginRequest.getPassword(), foundUser.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        
        Map<String, Object> tokenInfo = jwtTokenProvider.generateToken(foundUser.getUsername(), foundUser.getEmail());
        
        return LoginResponse.builder()
                .token((String) tokenInfo.get("token"))
                .username(foundUser.getUsername())
                .email(foundUser.getEmail())
                .fullName(foundUser.getFullName())
                .tokenType("Bearer")
                .expiresIn((Long) tokenInfo.get("expiresIn"))
                .expiresAt((LocalDateTime) tokenInfo.get("expiresAt"))
                .issuedAt((LocalDateTime) tokenInfo.get("issuedAt"))
                .message("Login successful")
                .build();
    }
    
    public LoginResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        User newUser = new User();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setFullName(registerRequest.getFullName());
        newUser.setIsActive(true);
        
        User savedUser = userRepository.save(newUser);
        
        Map<String, Object> tokenInfo = jwtTokenProvider.generateToken(savedUser.getUsername(), savedUser.getEmail());
        
        return LoginResponse.builder()
                .token((String) tokenInfo.get("token"))
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .fullName(savedUser.getFullName())
                .tokenType("Bearer")
                .expiresIn((Long) tokenInfo.get("expiresIn"))
                .expiresAt((LocalDateTime) tokenInfo.get("expiresAt"))
                .issuedAt((LocalDateTime) tokenInfo.get("issuedAt"))
                .message("Registration successful")
                .build();
    }
    
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
