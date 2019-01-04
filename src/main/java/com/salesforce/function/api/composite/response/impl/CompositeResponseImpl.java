package com.salesforce.function.api.composite.response.impl;

import java.util.List;
import java.util.NoSuchElementException;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.salesforce.function.api.composite.request.CompositeSubrequest;
import com.salesforce.function.api.composite.response.CompositeResponse;
import com.salesforce.function.api.composite.response.CompositeSubresponse;

/**
 * TODO
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompositeResponseImpl implements CompositeResponse {

    @JsonProperty(value = "compositeResponse")
    private List<CompositeSubresponse> compositeResults;

    public CompositeResponseImpl() {}

    public CompositeResponseImpl(List<CompositeSubresponse> compositeResults) {
        this.compositeResults = compositeResults;
    }

    @Override
    public List<CompositeSubresponse> getCompositeSubresponses() {
        return compositeResults;
    }

    public void setCompositeResults(List<CompositeSubresponse> compositeResults) {
        this.compositeResults = compositeResults;
    }

    @Override
    public String toString() {
        return "CompositeResponseImpl [compositeResults=" + compositeResults + "]";
    }

    @Override
    public CompositeSubresponse getCompositeSubresponse(CompositeSubrequest compositeSubrequest)
            throws NoSuchElementException {
        // TODO: This might be more efficient to do one time and build up a map, but this would leak some abstractions
        if (compositeResults != null) {
            return compositeResults.stream()
                    .filter(cr -> cr.getReferenceId().equals(compositeSubrequest.getReferenceId())).findFirst().get();
        } else {
            throw new NoSuchElementException();
        }
    }
}