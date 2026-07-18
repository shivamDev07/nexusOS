package com.example.NexusOS.response;


import java.time.Instant;
import java.util.List;

public class ApiResult<T> {

    private boolean success;
    private String message;
    private T data;
    private Instant timestamp;
    private List<String> errors;

    public ApiResult() {this.timestamp = Instant.now();}

    public ApiResult(boolean success, String message, T data, Instant timestamp) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }

    public boolean isSuccess() {return success;}

    public void setSuccess(boolean success) {this.success = success;}

    public String getMessage() {return message;}

    public void setMessage(String message) {this.message = message;}

    public T getData() {return data;}

    public void setData(T data) {this.data = data;}

    public Instant getTimestamp() {return timestamp;}

    public void setTimestamp(Instant timestamp) {this.timestamp = timestamp;}

    public List<String> getErrors() {return errors;}

    public void setErrors(List<String> errors) {this.errors = errors;}
}
