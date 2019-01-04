package com.salesforce.function.api.composite.response.impl;

import java.util.List;

import com.salesforce.function.api.composite.response.CompositeBatchResponse;
import com.salesforce.function.api.composite.response.CompositeBatchResult;


/**
 * TODO
 *
 */
public class CompositeBatchResponseImpl implements CompositeBatchResponse {

	private List<CompositeBatchResult> results;

	public CompositeBatchResponseImpl() {
	}

	public CompositeBatchResponseImpl(List<CompositeBatchResult> results) {
		this.results = results;
	}

	@Override
	public List<CompositeBatchResult> getCompositeResults() {
		return results;
	}

	public void setresults(List<CompositeBatchResult> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		int idx = 0;
		StringBuilder str = new StringBuilder();
		for (CompositeBatchResult compositeResult : results) {
			str.append("(").append(++idx).append(") ").append(compositeResult.toString());
		}

		return str.toString();
	}
}