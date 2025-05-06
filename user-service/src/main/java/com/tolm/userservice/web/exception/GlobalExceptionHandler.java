package com.tolm.userservice.web.exception;

import com.tolm.userservice.domain.exception.RegistrationException;
import com.tolm.userservice.domain.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                "An unexpected error occurred",
                request.getDescription(false),
                LocalDateTime.now()
        );
        log.error("Unhandled Exception: ", ex);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                ex.getMessage(),
                request.getDescription(false),
                LocalDateTime.now()
        );

        log.warn("UserNotFoundException: {}", ex.getMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<ErrorDetails> handleRegistrationException(RegistrationException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                ex.getMessage(),
                request.getDescription(false),
                LocalDateTime.now()
        );

        log.warn("RegistrationException: {}", ex.getMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        {
            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("timestamp", LocalDateTime.now());
            errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
            errorResponse.put("error", "Validation Failed");
            errorResponse.put("errors", errors);
            errorResponse.put("path", request.getDescription(false));

            log.warn("Validation failed: {}", errors);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                "Access Denied: You do not have permission.",
                request.getDescription(false),
                LocalDateTime.now()
        );
        log.warn("AccessDeniedException (Controller Level): {}", ex.getMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorDetails> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
        HttpStatusCode status = ex.getStatusCode();
        ErrorDetails errorDetails = new ErrorDetails(
                ex.getReason() != null ? ex.getReason() : status.toString(),
                request.getDescription(false),
                LocalDateTime.now()
        );
        log.warn("ResponseStatusException [{}]: {}", status, ex.getReason());

        return new ResponseEntity<>(errorDetails, status);
    }
}
