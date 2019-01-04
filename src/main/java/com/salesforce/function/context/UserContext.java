package com.salesforce.function.context;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.salesforce.function.context.impl.UserContextImpl;

/**
 * Encapsulates the user invoking a function.
 */
@JsonDeserialize(as = UserContextImpl.class)
public interface UserContext {

    /**
     * TODO
     *
     * @return the orgId
     */
    String getOrgId();

    /**
     * TODO
     *
     * @return the username
     */
    String getUsername();

    /**
     * TODO
     *
     * @return the userId
     */
    String getUserId();

    /**
     * TODO
     *
     * @return the salesforceBaseUrl
     */
    String getSalesforceBaseUrl();

    /**
     * TODO
     *
     * @return the orgDomain
     */
    String getOrgDomainUrl();

    String getSessionId();
}