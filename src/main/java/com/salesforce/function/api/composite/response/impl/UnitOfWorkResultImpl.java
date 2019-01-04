package com.salesforce.function.api.composite.response.impl;

import java.util.*;

import com.salesforce.function.api.Method;
import com.salesforce.function.api.UnitOfWorkResult;

public class UnitOfWorkResultImpl implements UnitOfWorkResult {
    private final Method method;
    private final String id;
    private final boolean success;
    private final List<String> errors;
    
    public UnitOfWorkResultImpl(Method method, String id, boolean success, List<String> errors) {
        this.method = method;
        this.id = id;
        this.success = success;
        this.errors = Optional.ofNullable(errors).orElseGet(() -> Collections.emptyList());
    }
    
    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public boolean isSuccess() {
        return this.success;
    }
    
    @Override
    public List<String> getErrors() {
        return this.errors;
    }

    @Override
    public String toString() {
        return "UnitOfWorkResultImpl [method=" + method + ", id=" + id + ", success=" + success + ", errors=" + errors
                + "]";
    }
}
