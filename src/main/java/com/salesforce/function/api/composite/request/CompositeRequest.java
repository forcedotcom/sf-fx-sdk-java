package com.salesforce.function.api.composite.request;

import java.util.List;
import java.util.NoSuchElementException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.salesforce.function.api.composite.request.impl.CompositeRequestImpl;

/**
 * <p>
 * Encapsulates multiple API operations in a single API call. The entire list of API operations are executed in one
 * synchronous call with the option of rolling back if any fail.
 * </p>
 * Java wrapper around the Composite REST API. See <a href=
 * "https://developer.salesforce.com/docs/atlas.en-us.api_rest.meta/api_rest/resources_composite_composite.htm">documentation</a>
 *
 * @since 218
 */
@JsonDeserialize(as = CompositeRequestImpl.class)
public interface CompositeRequest {
    /**
     * See https://developer.salesforce.com/docs/atlas.en-us.api_rest.meta/api_rest/requests_composite.htm
     * 
     * @param allOrNone
     *            true if the transaction should be rolled back due to a failure in a single subrequest.
     * @throws IllegalStateException
     *             if this value conflicts with any value that was previously set
     */
    void setAllOrNone(boolean allOrNone) throws IllegalStateException;

    /**
     * See https://developer.salesforce.com/docs/atlas.en-us.api_rest.meta/api_rest/requests_composite.htm
     * 
     * @return value previously set by {@link CompositeRequest#setAllOrNone(boolean)} else null
     */
    Boolean isAllOrNone();

    /**
     * Add a {@link CompositeSubrequest} to the current request.
     */
    void addSubRequest(CompositeSubrequest compositeSubrequest);

    /**
     * @return list of {@link CompositeSubrequest}s that were added via
     *         {@link #addSubRequest(CompositeSubrequest)}
     */
    List<CompositeSubrequest> getSubRequests();

    /**
     * Get the {@link CompositeSubrequest} that corresponds to the referenceId
     * 
     * @param referenceId
     *            of a previously added CompositeSubrequest
     * @return {@link CompositeSubrequest}
     * @throws NoSuchElementException
     *             if no request can be found
     */
    @JsonIgnore()
    CompositeSubrequest getSubrequest(String referenceId) throws NoSuchElementException;
}
