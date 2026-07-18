package com.example.NexusOS.service.impl;

import com.example.NexusOS.dto.response.HealthResponseDTO;
import com.example.NexusOS.service.HealthService;
import org.springframework.stereotype.Service;

@Service
public class HealthServiceImpl implements HealthService {

    @Override
    public HealthResponseDTO getHealth() {
        return new HealthResponseDTO("UP", "NexusOS", "1.0.0");
    }
}
