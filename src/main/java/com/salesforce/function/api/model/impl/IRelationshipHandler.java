package com.salesforce.function.api.model.impl;

import com.salesforce.function.api.model.*;

public interface IRelationshipHandler {
    SObjectRelationship getSObjectRelationship(SObjectType thisType, SObject relation);
    SObjectRelationship getSObjectRelationship(String thisType, SObject relation);
}
