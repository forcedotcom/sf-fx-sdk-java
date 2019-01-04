package com.salesforce.function.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.salesforce.function.api.model.impl.SObjectRelationshipImpl;

@JsonDeserialize(as = SObjectRelationshipImpl.class)
public interface SObjectRelationship {
    public String getFkName();
    
    public SObject getSObject();
}
