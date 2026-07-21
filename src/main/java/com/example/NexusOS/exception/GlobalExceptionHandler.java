package com.example.NexusOS.exception;

import com.example.NexusOS.dto.request.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.List;

@RestController
public class GlobalExceptionHandler {

    public ResponseEntity<ApiResult> handleException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResult());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResult> handleBadRequestException(BadRequestException ex) {
        ApiResult<Object> response = new ApiResult();

        response.setSuccess(false);
        response.setMessage(ex.getMessage());
        response.setData(null);
        response.setTimestamp(Instant.now());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResult<Object>>
    handleResourceNotFound(ResourceNotFoundException ex) {

        ApiResult<Object> response = new ApiResult<>();

        response.setSuccess(false);
        response.setMessage(ex.getMessage());
        response.setData(null);
        response.setTimestamp(Instant.now());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);

    }

//    Manage Validation Exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResult<Object>>
    handleValidation(MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .toList();

        ApiResult<Object> response = new ApiResult<>();

        response.setSuccess(false);
        response.setMessage("Validation Failed");
        response.setErrors(errors);
        response.setTimestamp(Instant.now());

        return ResponseEntity
                .badRequest()
                .body(response);

    }

//    Manage Global Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResult<Object>> handleException(Exception ex) {
        ApiResult<Object> response = new ApiResult<>();

        response.setSuccess(false);
        response.setMessage(ex.getMessage());
        response.setData(null);
        response.setTimestamp(Instant.now());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
