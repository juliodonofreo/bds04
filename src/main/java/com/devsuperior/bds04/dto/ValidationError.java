package com.devsuperior.bds04.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends CustomError{
    private List<CustomFieldError> errors = new ArrayList<>();

    public ValidationError(Instant timestamp, int status, String error, String path) {
        super(timestamp, status, error, path);
    }

    public List<CustomFieldError> getErrors() {
        return errors;
    }

    public void addError(String field, String message){
        errors.add(new CustomFieldError(field, message));
    }
}
