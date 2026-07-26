package com.example.NexusOS.service.impl;

import com.example.NexusOS.config.JwtProperties;
import com.example.NexusOS.entity.RefreshToken;
import com.example.NexusOS.entity.User;
import com.example.NexusOS.repository.RefreshTokenRepositiry;
import com.example.NexusOS.service.RefreshTokenService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepositiry refreshTokenRepository;
    private final JwtProperties jwtProperties;

    public RefreshTokenServiceImpl(
            RefreshTokenRepositiry refreshTokenRepository,
            JwtProperties jwtProperties
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtProperties = jwtProperties;
    }


    @Override
    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken.setExpiryDate(
                Instant.now()
                        .plusSeconds(jwtProperties.getRefreshTokenExpiration() / 1000)
        );

        refreshToken.setRevoked(false);

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        if (refreshToken.isRevoked()) {
            throw new RuntimeException("Refresh token has been revoked");
        }

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            throw new RuntimeException("Refresh token has expired");
        }

        return refreshToken;
    }

    @Override
    public void revokeRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        refreshToken.setRevoked(true);

        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public void revokeAllUserTokens(User user) {
        List<RefreshToken> refreshTokens =
                refreshTokenRepository.findByUser(user);

        for (RefreshToken token : refreshTokens) {
            token.setRevoked(true);
        }

        refreshTokenRepository.saveAll(refreshTokens);
    }
}
