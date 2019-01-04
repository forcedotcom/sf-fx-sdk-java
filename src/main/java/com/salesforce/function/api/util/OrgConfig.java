package com.salesforce.function.api.util;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.http.client.utils.URIBuilder;

/**
 * TODO
 *
 */
public class OrgConfig implements Config {

    private final String instanceUrl;
    private final String apiVersion;
    private final String sessionId;

    public OrgConfig(String instanceUrl, String apiVersion, String sessionId) {
        this.instanceUrl = instanceUrl;
        this.apiVersion = apiVersion;
        this.sessionId = sessionId;
    }

    @Override
    public String getInstanceURL() {
        return this.instanceUrl;
    }

    @Override
    public String getApiVersion() {
        return this.apiVersion;
    }

    @Override
    public String getSessionId() {
        return this.sessionId;
    }

    @Override
    public URI getRequestURI(String path, Map<String, String> parameters) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(this.instanceUrl + path);
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            uriBuilder.addParameter(parameter.getKey(), parameter.getValue());
        }

        return uriBuilder.build();
    }

    @Override
    public URI getRequestURI(String path) {
        // TODO
        return URI.create(this.instanceUrl + "/" + path);
    }

    @Override
    public void closeConnection() {

    }

    @Override
    public Integer getBatchSize() {
        // TODO
        return 10;
    }

    @Override
    public String getServiceEndpoint() {
        return this.instanceUrl;
    }
}
