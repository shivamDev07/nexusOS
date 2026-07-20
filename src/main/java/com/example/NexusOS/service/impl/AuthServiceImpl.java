package com.example.NexusOS.service.impl;

import com.example.NexusOS.dto.request.RegisterRequestDTO;
import com.example.NexusOS.entity.EmailVerificationToken;
import com.example.NexusOS.entity.User;
import com.example.NexusOS.enums.AccountStatus;
import com.example.NexusOS.exception.EmailAlreadyExistsException;
import com.example.NexusOS.repository.EmailVerificationTokenRepository;
import com.example.NexusOS.repository.UserRepository;
import com.example.NexusOS.service.AuthService;
import com.example.NexusOS.service.EmailService;
import jakarta.transaction.Transactional;
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

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailVerificationTokenRepository emailVerificationTokenRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
        this.emailService = emailService;
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

        emailService.sendVerificationEmail(
                savedUser,
                verificationToken.getToken()
        );
    }
}
