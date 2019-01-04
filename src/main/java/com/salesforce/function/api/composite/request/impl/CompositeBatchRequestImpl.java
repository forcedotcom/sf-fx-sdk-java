package com.salesforce.function.api.composite.request.impl;

import java.util.Map;

import com.salesforce.function.api.Method;
import com.salesforce.function.api.composite.request.CompositeBatchRequest;

/**
 * TODO
 *
 */
public class CompositeBatchRequestImpl implements CompositeBatchRequest {

	private final String binaryPartName;
	private final String binaryPartNameAlias;
	private final Method method;
	private final Map<String, String> richInput;
	private final String url;

	public CompositeBatchRequestImpl(String binaryPartName, String binaryPartNameAlias, Method method,
			Map<String, String> richInput, String url) {
		this.binaryPartName = binaryPartName;
		this.binaryPartNameAlias = binaryPartNameAlias;
		this.method = method;
		this.richInput = richInput;
		this.url = url;
	}

	@Override
	public String getBinaryPartName() {
		return binaryPartName;
	}

	@Override
	public String getBinaryPartNameAlias() {
		return binaryPartNameAlias;
	}

	@Override
	public Method getMethod() {
		return method;
	}

	@Override
	public Map<String, String> getRichInput() {
		return richInput;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public void addInput(String key, String value) {
		richInput.put(key, value);
	}

}
