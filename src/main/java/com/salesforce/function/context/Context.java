package com.salesforce.function.context;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.salesforce.function.context.impl.ContextImpl;
import com.salesforce.function.log.Logger;

/**
 * Encapsulates the user invoking a function.
 */
@JsonDeserialize(as = ContextImpl.class)
public interface Context {

    /**
     * TODO
     *
     * @return the userContext
     */
    UserContext getUserContext();

    /**
     * TODO
     *
     * @return
     */
    Logger getLogger();

    String getApiVersion();

}