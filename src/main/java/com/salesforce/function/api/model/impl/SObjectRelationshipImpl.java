package com.salesforce.function.api.model.impl;

import com.salesforce.function.api.model.SObject;
import com.salesforce.function.api.model.SObjectRelationship;

public class SObjectRelationshipImpl implements SObjectRelationship {
    private final String fkName;
    private final SObject sobject;
    
    SObjectRelationshipImpl(String fkName, SObject sobject) {
        this.fkName = fkName;
        this.sobject = sobject;
    }
    
    @Override
    public String getFkName() {
        return this.fkName;
    }

    @Override
    public SObject getSObject() {
        return this.sobject;
    }
}
