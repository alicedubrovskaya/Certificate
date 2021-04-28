package com.epam.esm.controller.advice;

import java.util.Collections;
import java.util.List;

public class ApiException {
    private int errorCode;
    private List<String> errors;

    public ApiException(int errorCode, List<String> errors) {
        this.errorCode = errorCode;
        this.errors = errors;
    }

    public ApiException(int errorCode, String error) {
        this.errorCode = errorCode;
        errors = Collections.singletonList(error);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public List<String> getErrors() {
        return errors;
    }
}
