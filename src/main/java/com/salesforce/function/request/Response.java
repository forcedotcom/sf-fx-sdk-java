package com.salesforce.function.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.salesforce.function.request.impl.ResponseImpl;

/**
 * Encapsulates a successful response to a Function invocation.
 */
@JsonDeserialize(as = ResponseImpl.class)
public interface Response {

    Object getResult();
}