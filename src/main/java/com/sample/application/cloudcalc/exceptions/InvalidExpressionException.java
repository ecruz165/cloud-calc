package com.sample.application.cloudcalc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid expression")
public class InvalidExpressionException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidExpressionException(String errorMessage) {
        super(errorMessage);
    }
}
