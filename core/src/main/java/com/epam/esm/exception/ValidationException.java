package com.epam.esm.exception;

import com.epam.esm.model.enumeration.ErrorMessage;
import com.epam.esm.model.enumeration.RequestedResource;

import java.util.List;

public class ValidationException extends Exception {
    private List<ErrorMessage> errors;
    RequestedResource requestedResource;

    public ValidationException() {
    }

    public ValidationException(List<ErrorMessage> errors, RequestedResource requestedResource) {
        this.errors = errors;
        this.requestedResource = requestedResource;
    }

    public List<ErrorMessage> getErrors() {
        return errors;
    }

    public RequestedResource getRequestedResource() {
        return requestedResource;
    }
}
