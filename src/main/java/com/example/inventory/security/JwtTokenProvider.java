package com.example.inventory.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {
    
    @Value("${jwt.secret:my-super-secret-key-that-is-at-least-256-bits-long-for-hs256-algorithm}")
    private String jwtSecret;
    
    @Value("${jwt.expiration:86400000}")
    private long jwtExpirationInMs;
    
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
    
    public Map<String, Object> generateToken(String username, String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        return createToken(claims, username);
    }
    
    private Map<String, Object> createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        
        Map<String, Object> tokenInfo = new HashMap<>();
        tokenInfo.put("token", token);
        tokenInfo.put("issuedAt", convertToLocalDateTime(now));
        tokenInfo.put("expiresAt", convertToLocalDateTime(expiryDate));
        tokenInfo.put("expiresIn", jwtExpirationInMs);
        
        return tokenInfo;
    }
    
    public String getUsernameFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.getSubject();
    }
    
    public String getEmailFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get("email", String.class);
    }
    
    public Date getExpirationDateFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.getExpiration();
    }
    
    public LocalDateTime getExpirationLocalDateTimeFromToken(String token) {
        return convertToLocalDateTime(getExpirationDateFromToken(token));
    }
    
    public LocalDateTime getIssuedAtLocalDateTimeFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return convertToLocalDateTime(claims.getIssuedAt());
    }
    
    public Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    
    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
    
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    public long getExpirationTime() {
        return jwtExpirationInMs;
    }
    
    private LocalDateTime convertToLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
