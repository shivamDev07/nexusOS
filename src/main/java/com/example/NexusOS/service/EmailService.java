package com.example.NexusOS.service;

import com.example.NexusOS.entity.User;

public interface EmailService {
    void sendVerificationEmail(User user, String token);
}
