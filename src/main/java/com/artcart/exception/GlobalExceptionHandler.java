package com.artcart.exception;


import com.artcart.response.InvalidDetailResponse;
import jakarta.servlet.ServletException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<ErrorDetails> userNotFoundHandler(UserNotFound exception , WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(),request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SignInException.class)
    public ResponseEntity<InvalidDetailResponse> userNotFoundHandler(SignInException exception , WebRequest request){
        InvalidDetailResponse invalidDetailResponse = new InvalidDetailResponse(exception.getMessage());
        return new ResponseEntity<>(invalidDetailResponse, HttpStatus.OK);
    }

    @ExceptionHandler(CloudinaryImageUploadException.class)
    public ResponseEntity<ErrorDetails> cloudinaryImageUploadException(CloudinaryImageUploadException exception , WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(),request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}
