package com.example.NexusOS.service.impl;

import com.example.NexusOS.entity.User;
import com.example.NexusOS.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendVerificationEmail(User user, String token) {
        String verificationLink = frontendUrl + "/auth/verify-email?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Verify Your NexusOS Account");

        message.setText("Welcome to NexusOS!\n\n"
                        + "Click the link below to verify your email:\n\n"
                        + verificationLink
        );

        mailSender.send(message);
    }
}
