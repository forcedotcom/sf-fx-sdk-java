package com.salesforce.function.api;

import com.salesforce.function.api.composite.CompositeAPI;
import com.salesforce.function.api.composite.impl.CompositeAPIImpl;
import com.salesforce.function.api.impl.UnitOfWorkImpl;
import com.salesforce.function.api.springml.ForceAPI;
import com.salesforce.function.api.springml.impl.ForceAPIImpl;
import com.salesforce.function.api.util.OrgConfig;
import com.salesforce.function.context.Context;
import com.salesforce.function.context.UserContext;

/**
 * Factory class to get WaveAPI
 */
public class APIFactory {

    private static APIFactory instance = null;

    private APIFactory() {}

    public static APIFactory getInstance() {
        if (instance == null) {
            instance = new APIFactory();
        }

        return instance;
    }

    public ForceAPI forceAPI(String instanceUrl, String apiVersion, String sessionId) throws Exception {
        return new ForceAPIImpl(new OrgConfig(instanceUrl, apiVersion, sessionId));
    }

    public UnitOfWork unitOfWork(Context ctx) throws Exception {
        UserContext userCtx = ctx.getUserContext();
        return unitOfWork(userCtx.getOrgDomainUrl(), ctx.getApiVersion(), userCtx.getSessionId());
    }

    public UnitOfWork unitOfWork(String instanceUrl, String apiVersion, String sessionId) throws Exception {
        return new UnitOfWorkImpl(new OrgConfig(instanceUrl, apiVersion, sessionId));
    }

    public CompositeAPI compositeAPI(String instanceUrl, String apiVersion, String sessionId) throws Exception {
        return new CompositeAPIImpl(new OrgConfig(instanceUrl, apiVersion, sessionId));
    }
}
