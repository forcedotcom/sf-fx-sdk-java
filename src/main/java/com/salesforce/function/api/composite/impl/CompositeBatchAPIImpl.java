package com.salesforce.function.api.composite.impl;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.salesforce.function.FunctionException;
import com.salesforce.function.api.composite.CompositeBatchAPI;
import com.salesforce.function.api.composite.request.CompositeBatchRequest;
import com.salesforce.function.api.composite.request.impl.CompositeBatchRequestImpl;
import com.salesforce.function.api.composite.response.CompositeResponse;
import com.salesforce.function.api.composite.response.impl.CompositeResponseImpl;
import com.salesforce.function.api.springml.util.impl.HTTPHelperImpl;
import com.salesforce.function.api.util.Config;
import com.salesforce.function.api.util.HTTPHelper;
import com.salesforce.function.log.LogFactory;
import com.salesforce.function.log.Logger;

/**
 * TODO
 *
 */
public class CompositeBatchAPIImpl implements CompositeBatchAPI {

	private static final Logger logger = LogFactory.getInstance().getLogger(CompositeBatchAPIImpl.class);

	public static final String API_URL = "/services/data/v%s/composite/batch";

	private final HTTPHelper httpHelper;
	private final Config config;
	private final Map<String, String> httpHeaders;
	private final Payload payload;

	private static class Payload {

		private final boolean haltOnError;
		@JsonProperty(value = "batchRequests")
		private final Set<CompositeBatchRequest> requests;

		Payload(boolean haltOnError,
				Set<CompositeBatchRequest> requests) {
			this.haltOnError = haltOnError;
			this.requests = requests != null ? requests : new LinkedHashSet<CompositeBatchRequest>();
		}

		public boolean isHaltOnError() {
			return this.haltOnError;
		}

		public Set<CompositeBatchRequest> getRequests() {
			return this.requests;
		}

		void addRequest(CompositeBatchRequest request) {
			requests.add(request);
		}
	}

	public CompositeBatchAPIImpl(HTTPHelper httpHelper,
			Config config,
			boolean haltOnError,
			Set<CompositeBatchRequest> requests) {
		this.config = config;
		this.httpHeaders = new LinkedHashMap<String,String>();
		this.payload = new Payload(haltOnError, requests);
		this.httpHelper = httpHelper != null ? httpHelper : new HTTPHelperImpl();

		// TODO
		this.httpHeaders.put("Accept", "");
		this.httpHeaders.put("Authorization", "Bearer " + this.config.getSessionId());
		this.httpHeaders.put("Content-Type", "");
	}

	@Override
	public void addRequest(CompositeBatchRequest request) {
		payload.addRequest(request);
	}

	@Override
	public CompositeResponse invoke() throws FunctionException {

		URI endpoint = URI.create(config.getInstanceURL() + String.format(API_URL, config.getApiVersion()));

		logger.info(
				"Invoking Composite Batch API " + endpoint + " with [" + payload.getRequests().size() + "] requests");

		CompositeResponse result;
		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonPayload = mapper.writeValueAsString(payload);

			String response = httpHelper.post(endpoint, jsonPayload, httpHeaders);

			// TODO
			SimpleModule module = new SimpleModule("CustomModel", Version.unknownVersion());
			SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();
			resolver.addMapping(CompositeBatchRequest.class, CompositeBatchRequestImpl.class);
			module.setAbstractTypes(resolver);
			mapper.registerModule(module);

			result = mapper.readValue(response, CompositeResponseImpl.class);
		} catch (Exception ex) {
			throw new FunctionException("Composite request failed: " + ex.getMessage(), ex);
		}

		return result;
	}

}