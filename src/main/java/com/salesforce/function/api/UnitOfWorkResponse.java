package com.salesforce.function.api;

import java.util.List;

import com.salesforce.function.api.model.SObject;

/**
 * Represents the outcome of an invocation of the {@link UnitOfWork#commit()} method.
 *
 * @since 218
 */
public interface UnitOfWorkResponse {
    /**
     * Retrieve the results associated with an SObject.
     * 
     * @param sobject
     *            that was previously passed to a {@link UnitOfwork} register* method.
     * @return the list of results. This may be more than one if multiple operations were performed against the same
     *         SObject
     * @throws NoSuchElementException
     *             if the list of results for sobject is empty
     */
    List<UnitOfWorkResult> getResults(SObject sobject);

    /**
     * Equivalent to UnitOfWorkResponse#getResults(sobject).get(0).getId()
     *
     * @throws NoSuchElementException
     *             if the list of results for sobject is empty
     */
    String getId(SObject sobject);
}
