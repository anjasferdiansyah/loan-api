package com.enigmacamp.loanapp.controller;

import com.enigmacamp.loanapp.dto.response.CommonResponse;
import jakarta.servlet.ServletException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> responseStatusException(ResponseStatusException e){
        CommonResponse<?> response = CommonResponse.<String>builder()
                .status(e.getStatusCode().value())
                .message(e.getReason())
                .build();
        return ResponseEntity
                .status(e.getStatusCode())
                .body(response);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<?> constraintViolationException(ConstraintViolationException e){
        CommonResponse<?> response = CommonResponse.<String>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> authenticationException(AuthenticationException e){
        CommonResponse<?> response = CommonResponse.<String>builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(e.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

    @ExceptionHandler({AccessDeniedException.class, ServletException.class})
    public ResponseEntity<?> accessDeniedException(AccessDeniedException e){
        CommonResponse<?> response = CommonResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .message(e.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(response);
    }


}