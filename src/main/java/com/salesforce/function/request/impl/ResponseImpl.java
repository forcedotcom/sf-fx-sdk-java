package com.salesforce.function.request.impl;

import com.salesforce.function.request.Response;

/**
 * Encapsulates a successful response to a Function invocation.
 */
public class ResponseImpl implements Response {

    private Object result;

    public ResponseImpl() {
    }

    public ResponseImpl(Object result) {
        this.result = result;
    }

    @Override
    public Object getResult() {
        return this.result;
    }

}