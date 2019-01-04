package com.salesforce.function.api.util;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * TODO
 *
 */
public interface Config {

    String getInstanceURL();

    String getApiVersion();

    String getSessionId();

    URI getRequestURI(String path, Map<String, String> parameters) throws URISyntaxException, UnsupportedEncodingException;

    URI getRequestURI(String path) throws URISyntaxException;

    void closeConnection();

    Integer getBatchSize();

    String getServiceEndpoint();
}
