package com.devsuperior.bds04.dto;

public class CustomFieldError {

    private String fieldName;
    private String message;

    public CustomFieldError(String field, String message){
        this.fieldName = field;
        this.message = message;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getMessage() {
        return message;
    }
}
