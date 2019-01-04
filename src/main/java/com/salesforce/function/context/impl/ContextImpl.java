package com.salesforce.function.context.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesforce.function.context.Context;
import com.salesforce.function.context.UserContext;
import com.salesforce.function.log.LogFactory;
import com.salesforce.function.log.Logger;

/**
 * Encapsulates the user invoking a function.
 */
public class ContextImpl implements Context {

    @JsonIgnore
    private Logger logger = LogFactory.getInstance().getDefault();
    private UserContext userContext;
    private String apiVersion = "44.0";

    public ContextImpl() {
    }

    public ContextImpl(UserContext userContext) {
        this.userContext = userContext;
    }

    public ContextImpl(String apiVersion, UserContext userContext) {
        this.apiVersion = apiVersion;
        this.userContext = userContext;
    }

    @Override
    public UserContext getUserContext() {
        return this.userContext;
    }

    public void setUserContext(UserContext userContext) {
        this.userContext = userContext;
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }

    @Override
    public String getApiVersion() {
        return apiVersion;
    }
    
    // REVIEME: do we need this?
    public void setCurrentApiVersion(String currentApiVersion) {
    	this.apiVersion = currentApiVersion;
	}

}