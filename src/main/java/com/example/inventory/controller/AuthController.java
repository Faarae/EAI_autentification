package com.example.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventory.dto.AuthResponse;
import com.example.inventory.dto.LoginRequest;
import com.example.inventory.dto.LoginResponse;
import com.example.inventory.dto.RegisterRequest;
import com.example.inventory.service.AuthService;
import com.example.inventory.service.TokenBlacklistService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private TokenBlacklistService tokenBlacklistService;
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse loginResponse = authService.login(loginRequest);
            return ResponseEntity.ok(
                AuthResponse.builder()
                    .status("success")
                    .message(loginResponse.getMessage())
                    .data(loginResponse)
                    .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(AuthResponse.builder()
                    .status("error")
                    .message(e.getMessage())
                    .build()
                );
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            LoginResponse loginResponse = authService.register(registerRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(AuthResponse.builder()
                    .status("success")
                    .message(loginResponse.getMessage())
                    .data(loginResponse)
                    .build()
                );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(AuthResponse.builder()
                    .status("error")
                    .message(e.getMessage())
                    .build()
                );
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(AuthResponse.builder()
                        .status("error")
                        .message("Authorization header is missing or invalid")
                        .build()
                    );
            }
            
            String token = authHeader.substring(7);
            
            // Add token to blacklist
            tokenBlacklistService.blacklistToken(token);
            
            return ResponseEntity.ok(
                AuthResponse.builder()
                    .status("success")
                    .message("Logout successful. Token has been invalidated.")
                    .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(AuthResponse.builder()
                    .status("error")
                    .message("Logout failed: " + e.getMessage())
                    .build()
                );
        }
    }
    
    @GetMapping("/validate-token")
    public ResponseEntity<AuthResponse> validateToken(@RequestHeader("Authorization") String token) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(AuthResponse.builder()
                        .status("error")
                        .message("Invalid token format")
                        .build()
                    );
            }
            
            String jwtToken = token.substring(7);
            
            // Check if token is blacklisted
            if (tokenBlacklistService.isTokenBlacklisted(jwtToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(AuthResponse.builder()
                        .status("error")
                        .message("Token is blacklisted (logged out)")
                        .build()
                    );
            }
            
            return ResponseEntity.ok(
                AuthResponse.builder()
                    .status("success")
                    .message("Token is valid")
                    .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(AuthResponse.builder()
                    .status("error")
                    .message("Token validation failed: " + e.getMessage())
                    .build()
                );
        }
    }
}
