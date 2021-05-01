package com.epam.esm.controller.advice;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.model.enumeration.ErrorMessage;
import com.epam.esm.model.enumeration.RequestedResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleServiceException(ValidationException e) {
        List<String> errors = e.getErrors().stream().map(ErrorMessage::getType)
                .collect(Collectors.toList());
        ApiException apiException = new ApiException(getErrorCode("400", e.getRequestedResource()), errors);
        return new ResponseEntity<>(
                apiException, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Object> exception(ResourceNotFoundException e) {
        ApiException apiException = new ApiException(getErrorCode("404", e.getRequestedResource()), getErrorMessage(e));
        return new ResponseEntity<>(
                apiException, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    private int getErrorCode(String code, RequestedResource requestedResource) {
        return Integer.parseInt(code + requestedResource.getErrorCode());
    }

    private String getErrorMessage(ResourceNotFoundException e) {
        return e.getErrorMessage().getType() + String.format(" (id=%d)", e.getRequestedResourceId());
    }
}
