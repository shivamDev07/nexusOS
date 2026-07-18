package com.example.NexusOS.response;

import java.time.Instant;
import java.util.List;

public class ErrorResponse {

    private boolean success;
    private String message;
    private List<String> errors;
    private Instant timestamp;

    public ErrorResponse() {this.timestamp = Instant.now();}

    public ErrorResponse(boolean success, String message,
                         List<String> errors,
                         Instant timestamp) {
        this.success = success;
        this.message = message;
        this.errors = errors;
        this.timestamp = timestamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
