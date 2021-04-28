package com.epam.esm.model.enumeration;

public enum RequestedResource {
    CERTIFICATE("01"), TAG("02");

    private final String errorCode;

    RequestedResource(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
