package com.example.NexusOS.service;

import com.example.NexusOS.dto.request.LoginRequestDTO;
import com.example.NexusOS.dto.request.RefreshTokenRequestDTO;
import com.example.NexusOS.dto.request.RegisterRequestDTO;
import com.example.NexusOS.dto.response.AuthResponseDTO;

public interface AuthService {
    void register(RegisterRequestDTO request);

    AuthResponseDTO login(LoginRequestDTO request);

    AuthResponseDTO refreshToken(RefreshTokenRequestDTO request);
}
