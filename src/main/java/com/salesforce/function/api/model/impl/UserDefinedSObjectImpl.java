package com.salesforce.function.api.model.impl;

import com.salesforce.function.api.model.SObjectType;
import com.salesforce.function.api.model.UserDefinedSObject;

/**
 * 
 * A loosely typed SObject defined at runtime by the user.
 *
 * @since 218
 */
public class UserDefinedSObjectImpl extends SObjectImpl implements UserDefinedSObject {
    private String id;
    
    public UserDefinedSObjectImpl() {
    }
    
    public UserDefinedSObjectImpl(SObjectType sObjectType) {
        super(sObjectType);
    }

    public UserDefinedSObjectImpl(String sobjectType) {
        super(sobjectType);
    }

    @Override
    public String getId() {
        return this.id;
    }

    // Builder type methods
    public UserDefinedSObjectImpl id(String id) {
        this.id = id;
        return this;
    }

    public UserDefinedSObjectImpl id(UserDefinedSObject sobject) {
        this.id = sobject.getId();
        return this;
    }
}