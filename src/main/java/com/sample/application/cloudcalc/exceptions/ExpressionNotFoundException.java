package com.sample.application.cloudcalc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Expression is not found")
public class ExpressionNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public ExpressionNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
