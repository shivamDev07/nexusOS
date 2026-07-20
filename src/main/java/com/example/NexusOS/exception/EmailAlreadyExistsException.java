package com.example.NexusOS.exception;

import com.example.NexusOS.repository.UserRepository;

public class EmailAlreadyExistsException extends RuntimeException{

    public EmailAlreadyExistsException() {
    }

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
