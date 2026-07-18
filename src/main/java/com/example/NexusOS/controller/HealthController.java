package com.example.NexusOS.controller;

import com.example.NexusOS.dto.response.HealthResponseDTO;
import com.example.NexusOS.response.ApiResult;
import com.example.NexusOS.service.HealthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.health")
public class HealthController {

    private final HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @Operation(
            summary = "Application Health Check",
            description = "Returns the current health status of NexusOS"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Application is running")
    })
    @GetMapping
    public ResponseEntity<ApiResult<HealthResponseDTO>> getHealth() {

        HealthResponseDTO health = healthService.getHealth();

        ApiResult<HealthResponseDTO> response = new ApiResult<>();
        response.setSuccess(true);
        response.setMessage("Application is running");
        response.setData(health);

        return ResponseEntity.ok(response);
    }
}
