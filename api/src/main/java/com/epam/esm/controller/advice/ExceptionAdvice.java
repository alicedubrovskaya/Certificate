package com.epam.esm.controller.advice;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.model.enumeration.ErrorMessage;
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
        int errorCode = Integer.parseInt("400" + e.getRequestedResource().getErrorCode());
        List<String> errors = e.getErrors().stream().map(ErrorMessage::getType)
                .collect(Collectors.toList());
        ApiException apiException = new ApiException(errorCode, errors);
        return new ResponseEntity<>(
                apiException, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Object> exception(ResourceNotFoundException e) {
        int errorCode = Integer.parseInt("404" + e.getRequestedResource().getErrorCode());
        ApiException apiException = new ApiException(errorCode, e.getErrorMessage().getType());
        return new ResponseEntity<>(
                apiException, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
