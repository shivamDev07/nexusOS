package com.example.NexusOS.controller;

import com.example.NexusOS.dto.request.LoginRequestDTO;
import com.example.NexusOS.dto.request.RefreshTokenRequestDTO;
import com.example.NexusOS.dto.request.RegisterRequestDTO;
import com.example.NexusOS.dto.request.ApiResult;
import com.example.NexusOS.dto.response.AuthResponseDTO;
import com.example.NexusOS.service.AuthService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {this.authService = authService;}

    @PostMapping("/register")
    public ResponseEntity<ApiResult> register(
            @Valid @RequestBody RegisterRequestDTO request) {

        authService.register(request);

        return ResponseEntity.ok(
                ApiResult.success("Registration successful. Please verify your email.")
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResult<AuthResponseDTO>> refreshToken(
            @Valid @RequestBody RefreshTokenRequestDTO request) {

        AuthResponseDTO response = authService.refreshToken(request);

        return ResponseEntity.ok(
                ApiResult.success("Access token refreshed successfully.", response)
        );
    }
}
