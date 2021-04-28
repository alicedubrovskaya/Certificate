package com.epam.esm.exception;

import com.epam.esm.model.enumeration.ErrorMessage;
import com.epam.esm.model.enumeration.RequestedResource;

public class ResourceNotFoundException extends RuntimeException {
    ErrorMessage errorMessage;
    RequestedResource requestedResource;

    public ResourceNotFoundException(ErrorMessage errorMessage, RequestedResource requestedResource) {
        this.errorMessage = errorMessage;
        this.requestedResource = requestedResource;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public RequestedResource getRequestedResource() {
        return requestedResource;
    }
}
