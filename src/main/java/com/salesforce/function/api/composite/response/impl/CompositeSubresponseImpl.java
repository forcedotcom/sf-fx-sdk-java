package com.salesforce.function.api.composite.response.impl;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.salesforce.function.api.composite.response.CompositeSubresponse;

/**
 * TODO
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompositeSubresponseImpl implements CompositeSubresponse {

	private Map<String, Object> body;
	private Map<String, String> httpHeaders;
	private Integer httpStatusCode;
	private String referenceId;

	public CompositeSubresponseImpl() {
	}

	public CompositeSubresponseImpl(Map<String, Object> body, 
										 Map<String, String> httpHeaders, 
										 Integer httpStatusCode, 
										 String referenceId) {
		this.body = body;
		this.httpHeaders = httpHeaders;
		this.httpStatusCode = httpStatusCode;
		this.referenceId = referenceId;
	}

	@Override
	public Map<String, Object> getBody() {
		return this.body;
	}

	@Override
	public Map<String, String> getHttpHeaders() {
		return this.httpHeaders;
	}

	@Override
	public Integer getHttpStatusCode() {
		return this.httpStatusCode;
	}

	@Override
	public String getReferenceId() {
		return this.referenceId;
	}

	@Override
	public String toString() {
		return "CompositeSubresponseImpl [body=" + body + ", httpHeaders=" + httpHeaders + ", httpStatusCode="
				+ httpStatusCode + ", referenceId=" + referenceId + "]";
	}

    @Override
    public boolean isSuccess() {
        return (boolean)Optional.ofNullable(body).map(b -> body.get("success")).orElse(false);
    }

    @Override
    public String getId() {
        return (String)Optional.ofNullable(body).map(b -> body.get("id")).orElse(null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getErrors() {
        return (List<String>)Optional.ofNullable(body).map(b -> body.get("errors")).orElse(null);
    }
    
    @Override
    public String getLocation() {
        return Optional.ofNullable(httpHeaders).map(h -> h.get("Location")).orElse(null);
    }
}