package com.salesforce.function.api.model.impl;

import com.salesforce.function.api.model.*;

public enum SObjectRelationshipFactory implements IRelationshipHandler {
    INSTANCE;

    @Override
    public SObjectRelationship getSObjectRelationship(SObjectType thisType, SObject relation) {
        return getSObjectRelationship(thisType.value(), relation);
    }

    @Override
    public SObjectRelationship getSObjectRelationship(String thisType, SObject relation) {
        String relationSObjectType = relation.getSObjectType();
        if (SObjectType.Contact.value().equals(thisType)) {
            if (SObjectType.Account.value()
                    .equals(relationSObjectType)) { return new SObjectRelationshipImpl("AccountId", relation); }
        } else if (SObjectType.Attachment.value().equals(thisType)) {
            if (SObjectType.Account.value()
                    .equals(relationSObjectType)) { return new SObjectRelationshipImpl("ParentId", relation); }
        }

        // TODO: Do we want a FunctionRuntimeException? What is our policy on checked vs. unchecked?
        throw new RuntimeException("Unable to determine relationship name for childType='" + thisType
                + "', relationType='" + relationSObjectType + "'");
    }
}
