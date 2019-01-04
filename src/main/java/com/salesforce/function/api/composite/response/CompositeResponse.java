package com.salesforce.function.api.composite.response;

import java.util.List;
import java.util.NoSuchElementException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.salesforce.function.api.composite.request.CompositeSubrequest;
import com.salesforce.function.api.composite.response.impl.CompositeResponseImpl;

/**
 * TODO
 *
 */
@JsonDeserialize(as = CompositeResponseImpl.class)
public interface CompositeResponse {
	@JsonProperty("compositeResponse")
    List<CompositeSubresponse> getCompositeSubresponses();
	
	/**
	 * Get the {@link CompositeSubresponse} that corresponds to the {@link CompositeSubrequest}
	 * @param compositeSubrequest original request that was sent 
	 * @return {@link CompositeSubresponse}
	 * @throws NoSuchElementException if no response can be found
	 */
	@JsonIgnore()
	CompositeSubresponse getCompositeSubresponse(CompositeSubrequest compositeSubrequest) throws NoSuchElementException;
}
