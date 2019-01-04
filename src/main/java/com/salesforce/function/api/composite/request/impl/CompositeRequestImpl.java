package com.salesforce.function.api.composite.request.impl;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.salesforce.function.api.composite.request.CompositeRequest;
import com.salesforce.function.api.composite.request.CompositeSubrequest;

/**
 * 
 * Implementation of {@link CompositeRequest}
 *
 * @since 218
 */
public class CompositeRequestImpl implements CompositeRequest {
    private Boolean allOrNone;

    private final List<CompositeSubrequest> compositeSubRequests;

    public CompositeRequestImpl() {
        this.compositeSubRequests = new ArrayList<>();
    }

    @Override
    public void setAllOrNone(boolean allOrNone) {
        this.allOrNone = allOrNone;
    }

    @Override
    public Boolean isAllOrNone() {
        return this.allOrNone;
    }

    @Override
    public void addSubRequest(CompositeSubrequest compositeSubrequest) {
        this.compositeSubRequests.add(compositeSubrequest);
    }

    @Override
    @JsonProperty("compositeRequest")
    public List<CompositeSubrequest> getSubRequests() {
        return this.compositeSubRequests;
    }

    @Override
    public CompositeSubrequest getSubrequest(String referenceId) throws NoSuchElementException {
        return compositeSubRequests.stream().filter(cs -> cs.getReferenceId().equals(referenceId)).findFirst().get();
    }

    @Override
    public String toString() {
        return "CompositeRequestImpl [allOrNone=" + allOrNone + ", compositeSubRequests=" + compositeSubRequests
                + ", toString()=" + super.toString() + "]";
    }
}
