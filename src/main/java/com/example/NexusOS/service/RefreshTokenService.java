package com.example.NexusOS.service;

import com.example.NexusOS.entity.RefreshToken;
import com.example.NexusOS.entity.User;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(User user);

    RefreshToken verifyRefreshToken(String token);

    void revokeRefreshToken(String token);

    void revokeAllUserTokens(User user);
}
