package com.salesforce.function.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.salesforce.function.request.impl.ErrorResponseImpl;

/**
 * Encapsulates an error response to a Function invocation.
 */
@JsonDeserialize(as = ErrorResponseImpl.class)
public interface ErrorResponse {

    /**
     * TODO
     *
     * @return
     */
    String getMessage();

    /**
     * TODO
     *
     * @return
     */
    String getStacktrace();

    /**
     * TODO
     *
     * @return
     */
    String getType();

}