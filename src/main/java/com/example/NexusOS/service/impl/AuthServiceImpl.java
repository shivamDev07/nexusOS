package com.example.NexusOS.service.impl;

import com.example.NexusOS.config.JwtProperties;
import com.example.NexusOS.dto.request.LoginRequestDTO;
import com.example.NexusOS.dto.request.RefreshTokenRequestDTO;
import com.example.NexusOS.dto.request.RegisterRequestDTO;
import com.example.NexusOS.dto.response.AuthResponseDTO;
import com.example.NexusOS.entity.EmailVerificationToken;
import com.example.NexusOS.entity.RefreshToken;
import com.example.NexusOS.entity.User;
import com.example.NexusOS.enums.AccountStatus;
import com.example.NexusOS.exception.EmailAlreadyExistsException;
import com.example.NexusOS.repository.EmailVerificationTokenRepository;
import com.example.NexusOS.repository.UserRepository;
import com.example.NexusOS.security.JwtService;
import com.example.NexusOS.service.AuthService;
import com.example.NexusOS.service.EmailService;
import com.example.NexusOS.service.RefreshTokenService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JwtProperties jwtProperties;
    private final RefreshTokenService refreshTokenService;
    private final UserDetailsService userDetailsService;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailVerificationTokenRepository emailVerificationTokenRepository, EmailService emailService, JwtService jwtService, AuthenticationManager authenticationManager, JwtProperties jwtProperties, RefreshTokenService refreshTokenService, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
        this.emailService = emailService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.jwtProperties = jwtProperties;
        this.refreshTokenService = refreshTokenService;
        this.userDetailsService = userDetailsService;
    }

    @Transactional
    @Override
    public void register(RegisterRequestDTO request) {

        // ==========================
        // Validate Registration Request
        // ==========================

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        // ==========================
        // Create User
        // ==========================

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getEmail());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        user.setEnabled(false);
        user.setAccountLocked(false);
        user.setFailedLoginAttempts(0);

        user.setEmailVerified(false);
        user.setPhoneVerified(false);
        user.setAccountStatus(AccountStatus.INACTIVE);

        User savedUser = userRepository.save(user);

        // ==========================
        // Create Email Verification Token
        // ==========================

        EmailVerificationToken verificationToken = new EmailVerificationToken();

        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setUser(savedUser);
        verificationToken.setExpiresAt(
                Instant.now().plus(Duration.ofHours(24))
        );

        emailVerificationTokenRepository.save(verificationToken);

        // ==========================
        // Send Verification Email
        // ==========================

        emailService.sendVerificationEmail(savedUser, verificationToken.getToken());


    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        String accessToken =
                jwtService.generateToken(userDetails);

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(user);

        return new AuthResponseDTO(
                accessToken,
                refreshToken.getToken(),
                "Bearer",
                jwtProperties.getAccessTokenExpiration()
        );
    }

    @Override
    public AuthResponseDTO refreshToken(RefreshTokenRequestDTO request) {

        // Verify refresh token
        RefreshToken refreshToken =
                refreshTokenService.verifyRefreshToken(
                        request.getRefreshToken()
                );

        // Get associated user
        User user = refreshToken.getUser();

        // Load UserDetails
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(
                        user.getEmail()
                );

        // Generate new access token
        String accessToken =
                jwtService.generateToken(userDetails);

        // Return response
        return new AuthResponseDTO(
                accessToken,
                refreshToken.getToken(),
                "Bearer",
                jwtProperties.getAccessTokenExpiration()
        );
    }
}
