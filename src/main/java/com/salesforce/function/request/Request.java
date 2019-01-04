package com.salesforce.function.request;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.salesforce.function.context.Context;
import com.salesforce.function.request.impl.RequestImpl;


/**
 * Encapsulates the request to invoke a function.
 */
@JsonDeserialize(as = RequestImpl.class)
public interface Request {

	Context getContext();

	Map<String,Object> getParameters();

	<T> T getParameter(String key);
}