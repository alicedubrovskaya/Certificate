package com.epam.esm.exception;

import com.epam.esm.model.enumeration.ErrorMessage;
import com.epam.esm.model.enumeration.RequestedResource;

public class ResourceAlreadyExistsException extends RuntimeException {
    private final ErrorMessage errorMessage;
    private final RequestedResource requestedResource;

    public ResourceAlreadyExistsException(ErrorMessage errorMessage, RequestedResource requestedResource) {
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
