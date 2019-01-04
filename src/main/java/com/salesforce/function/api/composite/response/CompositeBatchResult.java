package com.salesforce.function.api.composite.response;

import java.util.Map;

/**
 * TODO
 *
 */
public interface CompositeBatchResult {

	Integer getHttpStatusCode();

	Map<String, Object> getResult();

}