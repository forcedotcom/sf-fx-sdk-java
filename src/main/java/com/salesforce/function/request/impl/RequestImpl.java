package com.salesforce.function.request.impl;

import com.salesforce.function.context.Context;
import com.salesforce.function.request.Request;

import java.util.HashMap;
import java.util.Map;


/**
 * Encapsulates the request to invoke a function.
 */
public class RequestImpl implements Request {

    private Context context;
    private Map<String,Object> parameters;

    public RequestImpl() {
    }

    public RequestImpl(Context context, Map<String,Object> parameters) {
        this.context = context;
        this.parameters = parameters != null ? parameters : new HashMap<String,Object>();
    }

    @Override
    public Context getContext() {
        return this.context;
    }

    @Override
    public Map<String,Object> getParameters() {
        return this.parameters;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getParameter(String key) {
        return (T)this.parameters.get(key);
    }

}