package com.salesforce.function.api.composite;

import com.salesforce.function.FunctionException;
import com.salesforce.function.api.composite.request.CompositeRequest;
import com.salesforce.function.api.composite.response.CompositeResponse;

/**
 * 
 * Java wrapper around the Composite REST API. See <a href=
 * "https://developer.salesforce.com/docs/atlas.en-us.api_rest.meta/api_rest/resources_composite_composite.htm">documentation</a>
 *
 * @since 218
 */
public interface CompositeAPI {
    /**
     * Synchronously invoke the Salesforce Composite API and return the result.
     * 
     * @param compositeRequest that defines the API request
     * @return See {@link CompositeResponse}
     * @throws FunctionException if any exceptions occur while invoking the API
     */
    CompositeResponse invoke(CompositeRequest compositeRequest) throws FunctionException;
}