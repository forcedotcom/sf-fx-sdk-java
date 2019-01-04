package com.salesforce.function.api.composite.request;

import java.util.Map;

import com.salesforce.function.api.Method;

/**
 * TODO
 *
 */
public interface CompositeBatchRequest {

	String getBinaryPartName();

	String getBinaryPartNameAlias();

	Method getMethod();

	Map<String, String> getRichInput();

	String getUrl();

	void addInput(String key, String value);

}