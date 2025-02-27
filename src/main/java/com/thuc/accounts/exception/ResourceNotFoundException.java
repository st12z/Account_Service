package com.thuc.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName,
                                     String fieldName,
                                     String fieldValue
    ) {
        super(String.format("%s with field %s of %s not found", resourceName, fieldName, fieldValue));
    }
}
