package com.salesforce.function.api.impl;

import java.util.*;

import org.apache.http.HttpStatus;

import com.salesforce.function.api.*;
import com.salesforce.function.api.composite.request.CompositeSubrequest;
import com.salesforce.function.api.composite.response.CompositeResponse;
import com.salesforce.function.api.composite.response.CompositeSubresponse;
import com.salesforce.function.api.composite.response.impl.UnitOfWorkResultImpl;
import com.salesforce.function.api.model.SObject;

/**
 * Implementation of {@link UnitOfWorkResponse}
 *
 * @since 218
 */
public class UnitOfWorkResponseImpl implements UnitOfWorkResponse {
    private static final String KEY_ERRORS = "errors";
    private static final String KEY_ID = "id";
    private static final String KEY_SUCCESS = "success";

    private final Map<String, List<String>> uuidToReferenceIds;
    private final CompositeResponse compositeResponse;
    private final Map<String, CompositeSubrequest> referenceIdToCompositeSubrequests;

    UnitOfWorkResponseImpl(Map<String, CompositeSubrequest> referenceIdToCompositeSubrequests,
            Map<String, List<String>> uuidToReferenceIds, CompositeResponse compositeResponse) {
        this.referenceIdToCompositeSubrequests = referenceIdToCompositeSubrequests;
        this.uuidToReferenceIds = uuidToReferenceIds;
        this.compositeResponse = compositeResponse;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UnitOfWorkResult> getResults(SObject sobject) {
        List<UnitOfWorkResult> results = new ArrayList<>();
        if (!uuidToReferenceIds.containsKey(sobject.getUUID())) {
            throw new NoSuchElementException("No Such Object: " + sobject);
        }
        Set<String> referenceIds = new HashSet<>(uuidToReferenceIds.get(sobject.getUUID()));
        if (!referenceIds.isEmpty()) {
            for (CompositeSubresponse compositeSubresponse : compositeResponse.getCompositeSubresponses()) {
                String referenceId = compositeSubresponse.getReferenceId();
                if (referenceIds.contains(referenceId)) {
                    CompositeSubrequest compositeSubrequest = referenceIdToCompositeSubrequests.get(referenceId);
                    if (compositeSubrequest == null) { throw new IllegalStateException(
                            "Unable to find CompositeSubrequest with referenceId=" + referenceId); }

                    Map<String, Object> body = Optional.ofNullable(compositeSubresponse.getBody())
                            .orElseGet(() -> new HashMap<String, Object>());
                    Method method = compositeSubrequest.getMethod();
                    String id = (String)body.get(KEY_ID);
                    boolean success = body.containsKey(KEY_SUCCESS) && (boolean)body.get(KEY_SUCCESS)
                            || compositeSubresponse.getHttpStatusCode() == HttpStatus.SC_NO_CONTENT;
                    List<String> errors = null;
                    if (body.containsKey(KEY_ERRORS)) {
                        errors = (List<String>)body.get(KEY_ERRORS);
                    }

                    UnitOfWorkResult unitOfWorkResult = new UnitOfWorkResultImpl(method, id, success, errors);
                    results.add(unitOfWorkResult);
                    // 1:1 relationship. Exit if we have found everything
                    if (results.size() == referenceIds.size()) {
                        break;
                    }
                }
            }
        }
        if (results.isEmpty()) {
            throw new NoSuchElementException("No Such Object: " + sobject);
        }
        return results;
    }

    @Override
    public String getId(SObject sobject) {
        return getResults(sobject).get(0).getId();
    }

    @Override
    public String toString() {
        return "UnitOfWorkResponseImpl [uuidToReferenceIds=" + uuidToReferenceIds + ", compositeResponse="
                + compositeResponse + ", referenceIdToCompositeSubrequests=" + referenceIdToCompositeSubrequests + "]";
    }
}