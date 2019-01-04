package com.salesforce.function.api.composite.response.impl;

import java.util.Map;

import com.salesforce.function.api.composite.response.CompositeBatchResult;

/**
 * TODO
 *
 */
public class CompositeBatchResultImpl implements CompositeBatchResult {

	private Integer httpStatusCode;
	private Map<String, Object> result;

	public CompositeBatchResultImpl() {
	}

	public CompositeBatchResultImpl(Integer httpStatusCode, Map<String, Object> result) {
		this.httpStatusCode = httpStatusCode;
		this.result = result;
	}

	@Override
	public Map<String, Object> getResult() {
		return this.result;
	}

	@Override
	public Integer getHttpStatusCode() {
		return this.httpStatusCode;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("statusCode: ").append(httpStatusCode);

		return str.toString();
	}

}