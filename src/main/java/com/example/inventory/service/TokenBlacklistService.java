package com.example.inventory.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.inventory.entity.TokenBlacklist;
import com.example.inventory.repository.TokenBlacklistRepository;
import com.example.inventory.security.JwtTokenProvider;

@Service
public class TokenBlacklistService {
    
    @Autowired
    private TokenBlacklistRepository tokenBlacklistRepository;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    /**
     * Add token to blacklist when user logs out
     */
    public void blacklistToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new RuntimeException("Invalid token");
        }
        
        String username = jwtTokenProvider.getUsernameFromToken(token);
        LocalDateTime expiresAt = jwtTokenProvider.getExpirationLocalDateTimeFromToken(token);
        
        TokenBlacklist blacklistedToken = new TokenBlacklist();
        blacklistedToken.setToken(token);
        blacklistedToken.setUsername(username);
        blacklistedToken.setExpiresAt(expiresAt);
        
        tokenBlacklistRepository.save(blacklistedToken);
    }
    
    /**
     * Check if token is blacklisted
     */
    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklistRepository.existsByToken(token);
    }
    
    /**
     * Clean up expired tokens from blacklist (run daily at 2 AM)
     */
    @Scheduled(cron = "0 0 2 * * *")
    public void cleanupExpiredTokens() {
        tokenBlacklistRepository.deleteByExpiresAtBefore(LocalDateTime.now());
    }
}
