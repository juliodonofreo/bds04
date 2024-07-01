package com.devsuperior.bds04.controllers.handler;

import com.devsuperior.bds04.dto.CustomError;
import com.devsuperior.bds04.dto.CustomFieldError;
import com.devsuperior.bds04.dto.ValidationError;
import com.devsuperior.bds04.services.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> notFound(ResourceNotFoundException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomError customError = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(customError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> notValid(MethodArgumentNotValidException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError validationError = new ValidationError(Instant.now(), status.value(), "Falha na validação", request.getRequestURI());
        e.getFieldErrors()
                .forEach(x -> {
                    validationError.addError(x.getField(), x.getDefaultMessage());
                });
        return ResponseEntity.status(status).body(validationError);
    }
}
