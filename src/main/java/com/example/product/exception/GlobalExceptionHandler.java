package com.example.product.exception;

import com.example.product.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUsernameNotFound(UsernameNotFoundException ex) {
        ApiResponse<Void> response = new ApiResponse<>(false,
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(),
                null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse(ex.getMessage());

        ApiResponse<Void> response = new ApiResponse<>(false,
                HttpStatus.BAD_REQUEST.value(),
                message,
                null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFound(ResourceNotFoundException ex) {
        ApiResponse<Void> response = new ApiResponse<>(false,
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        ApiResponse<Void> response = new ApiResponse<>(false,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred: " + ex.getMessage(),
                null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
