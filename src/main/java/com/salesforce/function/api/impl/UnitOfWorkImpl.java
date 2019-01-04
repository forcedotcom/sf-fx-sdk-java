package com.salesforce.function.api.impl;

import java.util.*;

import javax.annotation.concurrent.NotThreadSafe;

import com.salesforce.function.FunctionException;
import com.salesforce.function.api.UnitOfWork;
import com.salesforce.function.api.UnitOfWorkResponse;
import com.salesforce.function.api.composite.CompositeAPI;
import com.salesforce.function.api.composite.impl.CompositeAPIImpl;
import com.salesforce.function.api.composite.request.CompositeRequest;
import com.salesforce.function.api.composite.request.CompositeSubrequest;
import com.salesforce.function.api.composite.request.impl.CompositeRequestImpl;
import com.salesforce.function.api.composite.request.impl.CompositeSubrequestImpl;
import com.salesforce.function.api.composite.response.CompositeResponse;
import com.salesforce.function.api.model.SObject;
import com.salesforce.function.api.model.impl.SObjectImpl;
import com.salesforce.function.api.util.Config;
import com.salesforce.function.log.LogFactory;
import com.salesforce.function.log.Logger;

/**
 * Implementation of {@link UnitOfWork}
 */
@NotThreadSafe
public class UnitOfWorkImpl implements UnitOfWork {

    private static final Logger logger = LogFactory.getInstance().getLogger(UnitOfWorkImpl.class);

    private final Dependencies dependencies;
    private final Config config;
    
    /**
     * TODO: Not currently supported. Not sure this makes sense for a unitOfWork.
     */
    private final boolean allOrNone;
    
    /**
     * The container which accumulates all {@link CompositeSubrequest}s by recording the state of the {@link SObject} 
     * passed into the various register* methods.
     */
    private final CompositeRequest compositeRequest;

    /**
     * Tracks relationship between SObjects and CompositeSubrequests by linking {@link SObject#getUUID()} to
     * {@link CompositeSubrequest#getReferenceId()} via a 1:1..N relationship. A given SObject may have multiple operations
     * performed upon it, such as an initial insert and then an update.
     */
    private final Map<String, List<String>> uuidToReferenceIds;

    /**
     * Provides a map of {@link CompositeSubrequest#getReferenceId()} to {@link CompositeSubrequest} so that the results
     * can be easily mapped.
     */
    private final Map<String, CompositeSubrequest> referenceIdToCompositeSubrequests;

    public UnitOfWorkImpl(Config config) {
        this(new Dependencies(), config, true);
    }

    public UnitOfWorkImpl(Dependencies dependencies, Config config, boolean allOrNone) {
        this.dependencies = dependencies != null ? dependencies : new Dependencies();
        this.config = config;
        this.allOrNone = allOrNone;
        this.compositeRequest = new CompositeRequestImpl();
        this.uuidToReferenceIds = new HashMap<>();
        this.referenceIdToCompositeSubrequests = new HashMap<>();
        if (!allOrNone) { throw new RuntimeException("Not implemented"); }
    }

    @Override
    public void registerNew(SObject sobject) {
        CompositeSubrequest compositeSubrequest = CompositeSubrequestImpl.insertBuilder().sobject(sobject).build();
        addCompositeSubrequest(sobject, compositeSubrequest);

        // TOOD:
        // - count "subrequests", max 25
        // - check for dup
        // - support relationships
    }

    /**
     * Register update operation.
     * 
     * @throws FunctionException
     */
    @Override
    public void registerModified(SObject sobject) throws FunctionException {
        CompositeSubrequest compositeSubrequest = CompositeSubrequestImpl.patchBuilder().sobject(sobject).build();
        addCompositeSubrequest(sobject, compositeSubrequest);

        // TOOD:
        // - count "subrequests", max 25
        // - check for dup
        // - support relationships
    }

    /**
     * Register delete operation.
     * 
     * @throws FunctionException
     */
    @Override
    public void registerDeleted(SObject sobject) throws FunctionException {
        String id = SObjectImpl.getId(sobject);
        if (id == null) { throw new FunctionException("ID not provided"); }

        CompositeSubrequest compositeSubrequest = CompositeSubrequestImpl.deleteBuilder()
                .sobjecType(sobject.getSObjectType()).id(id).build();
        addCompositeSubrequest(sobject, compositeSubrequest);

        // TOOD:
        // - count "subrequests", max 25
        // - check for dup
    }

    /***
     * All operations batched together executed in single transaction.
     * 
     * @throws FunctionException
     */
    @Override
    public UnitOfWorkResponse commit() throws FunctionException {
        compositeRequest.setAllOrNone(allOrNone);

        CompositeAPI compositeAPI = dependencies.newCompositeAPI(config);
        CompositeResponse compositeResponse = compositeAPI.invoke(compositeRequest);
        logger.info(compositeResponse.toString());

        UnitOfWorkResponse unitOfWorkResponse = new UnitOfWorkResponseImpl(referenceIdToCompositeSubrequests,
                uuidToReferenceIds, compositeResponse);
        logger.info(unitOfWorkResponse.toString());

        return unitOfWorkResponse;
    }

    private void addCompositeSubrequest(SObject sobject, CompositeSubrequest compositeSubrequest) {
        ;
        String referenceId = compositeSubrequest.getReferenceId();
        String uuid = sobject.getUUID();
        List<String> referenceIds = uuidToReferenceIds.get(uuid);
        if (referenceIds == null) {
            referenceIds = new ArrayList<>();
            uuidToReferenceIds.put(uuid, referenceIds);
        }
        referenceIds.add(referenceId);
        compositeRequest.addSubRequest(compositeSubrequest);
        referenceIdToCompositeSubrequests.put(referenceId, compositeSubrequest);
    }

    static class Dependencies {

        public CompositeAPI newCompositeAPI(Config config) {
            return new CompositeAPIImpl(null, config);
        }
    }
}