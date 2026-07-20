package com.example.NexusOS.service;

import com.example.NexusOS.dto.request.RegisterRequestDTO;

public interface AuthService {
    void register(RegisterRequestDTO request);
}
