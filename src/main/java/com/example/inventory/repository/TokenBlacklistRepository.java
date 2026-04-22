package com.example.inventory.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.inventory.entity.TokenBlacklist;

@Repository
public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Long> {
    Optional<TokenBlacklist> findByToken(String token);
    
    boolean existsByToken(String token);
    
    void deleteByExpiresAtBefore(LocalDateTime dateTime);
    
    @Query("DELETE FROM TokenBlacklist tb WHERE tb.expiresAt < CURRENT_TIMESTAMP")
    void deleteExpiredTokens();
}
