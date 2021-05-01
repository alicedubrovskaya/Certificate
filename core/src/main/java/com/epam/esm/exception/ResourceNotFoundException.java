package com.epam.esm.exception;

import com.epam.esm.model.enumeration.ErrorMessage;
import com.epam.esm.model.enumeration.RequestedResource;

public class ResourceNotFoundException extends RuntimeException {
    private final ErrorMessage errorMessage;
    private final RequestedResource requestedResource;
    private final Long requestedResourceId;

    public ResourceNotFoundException(ErrorMessage errorMessage, RequestedResource requestedResource, Long requestedResourceId) {
        this.errorMessage = errorMessage;
        this.requestedResource = requestedResource;
        this.requestedResourceId = requestedResourceId;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public RequestedResource getRequestedResource() {
        return requestedResource;
    }

    public Long getRequestedResourceId() {
        return requestedResourceId;
    }
}
