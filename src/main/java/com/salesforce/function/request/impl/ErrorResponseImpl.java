package com.salesforce.function.request.impl;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.salesforce.function.request.ErrorResponse;

/**
 * Encapsulates an error response to a Function invocation.
 */
public class ErrorResponseImpl implements ErrorResponse {

    private String message;
    private String type;
    private Throwable throwable;

    public ErrorResponseImpl(String message, Throwable throwable) {
        this(message, throwable, throwable.getClass().getSimpleName());
    }

    public ErrorResponseImpl(String message, Throwable throwable, String type) {
        this.message = message;
        this.throwable = throwable;
        this.type = type;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String getStacktrace() {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    @Override
    public String getType() {
        return this.type;
    }

}