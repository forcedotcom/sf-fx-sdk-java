package com.salesforce.function.api.composite.impl;

import java.net.URI;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesforce.function.FunctionException;
import com.salesforce.function.api.composite.CompositeAPI;
import com.salesforce.function.api.composite.request.CompositeRequest;
import com.salesforce.function.api.composite.response.CompositeResponse;
import com.salesforce.function.api.composite.response.impl.CompositeResponseImpl;
import com.salesforce.function.api.springml.util.impl.HTTPHelperImpl;
import com.salesforce.function.api.util.Config;
import com.salesforce.function.api.util.HTTPHelper;
import com.salesforce.function.log.LogFactory;
import com.salesforce.function.log.Logger;

/**
 * Implementation of {@link CompositeAPI}
 *
 * @since 218
 */
public class CompositeAPIImpl implements CompositeAPI {

    private static final Logger logger = LogFactory.getInstance().getLogger(CompositeAPIImpl.class);

    public static final String API_URL = "/services/data/v%s/composite/";

    private final HTTPHelper httpHelper;
    private final Config config;
    private final Map<String, String> httpHeaders;

    public CompositeAPIImpl(Config config) {
        this(null, config);
    }

    public CompositeAPIImpl(HTTPHelper httpHelper, Config config) {
        this.config = config;
        this.httpHeaders = new LinkedHashMap<String, String>();
        this.httpHelper = Optional.ofNullable(httpHelper).orElseGet(() -> new HTTPHelperImpl());

        this.httpHeaders.put("Accept", "application/json");
        this.httpHeaders.put("Authorization", "Bearer " + this.config.getSessionId());
        this.httpHeaders.put("Content-Type", "application/json");
    }

    @Override
    public CompositeResponse invoke(CompositeRequest compositeRequest) throws FunctionException {
        URI endpoint = URI.create(config.getInstanceURL() + String.format(API_URL, config.getApiVersion()));

        logger.info("Invoking Composite API " + endpoint + " with [" + compositeRequest.getSubRequests().size()
                + "] requests");

        CompositeResponse result;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonPayload = mapper.writeValueAsString(compositeRequest);

            String response = httpHelper.post(endpoint, jsonPayload, httpHeaders);
            result = mapper.readValue(response, CompositeResponseImpl.class);
        } catch (Exception ex) {
            throw new FunctionException("Composite request failed: " + ex.getMessage(), ex);
        }

        return result;
    }

}