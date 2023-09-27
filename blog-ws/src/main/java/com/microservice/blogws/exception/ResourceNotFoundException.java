package com.microservice.blogws.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@NoArgsConstructor
@RequiredArgsConstructor
public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String fieldValue;
    public ResourceNotFoundException(String resourceName, String fieldName){
        super(String.format("%s not found with %s: %s", resourceName,fieldName));
        this.resourceName = resourceName;
        this.fieldValue = fieldValue;
    }
}
