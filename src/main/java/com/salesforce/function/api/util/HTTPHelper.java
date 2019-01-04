package com.salesforce.function.api.util;

import java.net.URI;
import java.util.Map;

import org.apache.http.client.methods.HttpUriRequest;

/**
 * Helper class for HTTP requests
 */
public interface HTTPHelper {
    
    String post(URI uri, String request, Map<String, String> httpHeaders) throws Exception;

	String post(URI taskURI, String sessionId, String request) throws Exception;

    String get(URI queryURI, String sessionId, Integer batchSize) throws Exception;
    
    String execute(URI uri, HttpUriRequest httpReq) throws Exception;
}
