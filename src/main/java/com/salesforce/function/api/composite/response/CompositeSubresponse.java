package com.salesforce.function.api.composite.response;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.salesforce.function.api.composite.response.impl.CompositeSubresponseImpl;

/**
 * TODO
 *
 */
//TODO: Use SimpleAbstractTypeResolver so that impl and interface can be in different jars
@JsonDeserialize(as = CompositeSubresponseImpl.class) 
public interface CompositeSubresponse {

    Map<String, Object> getBody();

    Map<String, String> getHttpHeaders();

    Integer getHttpStatusCode();

    String getReferenceId();

    boolean isSuccess();
    
    String getId();

    List<String> getErrors();
    
    String getLocation();
}