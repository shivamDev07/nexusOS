package com.example.NexusOS.controller;

import com.example.NexusOS.dto.request.RegisterRequestDTO;
import com.example.NexusOS.response.ApiResult;
import com.example.NexusOS.service.AuthService;
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
}
