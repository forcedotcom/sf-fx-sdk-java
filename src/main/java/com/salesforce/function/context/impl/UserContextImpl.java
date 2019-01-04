package com.salesforce.function.context.impl;

import com.salesforce.function.context.UserContext;

/**
 * Encapsulates the user invoking a function.
 */
public class UserContextImpl implements UserContext {

    private String sessionId;
    private String orgId;
    private String userId;
    private String username;
    private String salesforceBaseUrl;
    private String orgDomainUrl;

    public UserContextImpl() {
    }

    public UserContextImpl(String orgId,
            String userId,
            String username,
            String salesforceBaseUrl,
            String orgDomainUrl) {
        this.orgId = orgId;
        this.userId = userId;
        this.username = username;
        this.salesforceBaseUrl = salesforceBaseUrl;
        this.orgDomainUrl = orgDomainUrl;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String getOrgId() {
        return orgId;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getSalesforceBaseUrl() {
        return salesforceBaseUrl;
    }

    @Override
    public String getOrgDomainUrl() {
        return orgDomainUrl;
    }
}