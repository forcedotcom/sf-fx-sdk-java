package com.salesforce.function.api.composite.request;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.salesforce.function.api.Method;
import com.salesforce.function.api.composite.request.impl.CompositeSubrequestImpl;

/**
 * A CompositeSubrequest encapsulates a single API operation in a larger {@link CompositeRequest}.
 * 
 * <p>
 * See
 * <a href="https://developer.salesforce.com/docs/atlas.en-us.api_rest.meta/api_rest/requests_composite.htm">Composite
 * Subrequest Documentation</a>
 * </p>
 */
@JsonDeserialize(as = CompositeSubrequestImpl.class)
public interface CompositeSubrequest {
    /**
     * <a href="https://developer.salesforce.com/docs/atlas.en-us.api_rest.meta/api_rest/requests_composite.htm">See
     * public docs</a;
     */
    Map<String, String> getHttpHeaders();

    /**
     * @return The http method type for this subrequest. See {@link Method}
     */
    Method getMethod();

    /**
     * @return This subrequests referenceId. All subrequests need to have a uniqure referenceId. A subrequests
     *         referenceId can be used by future subrequests if the object doesn't exist in the database yet.
     */
    String getReferenceId();

    /**
     * @return The url that this subrequest will invoke. This is typically the SObject's Enterprise API url.
     */
    String getUrl();

    /**
     * @return
     */
    Map<String, Object> getBody();
}