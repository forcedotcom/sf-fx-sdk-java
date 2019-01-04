package com.salesforce.function.api.composite;

import com.salesforce.function.FunctionException;
import com.salesforce.function.api.composite.request.CompositeBatchRequest;
import com.salesforce.function.api.composite.response.CompositeResponse;

/**
 * TODO
 *
 */
public interface CompositeBatchAPI {

	void addRequest(CompositeBatchRequest request);

	CompositeResponse invoke() throws FunctionException;

}