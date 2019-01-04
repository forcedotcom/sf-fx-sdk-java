package com.salesforce.function.api.model;

/**
 * 
 * A loosely typed SObject defined at runtime by the user.
 *
 * @since 218
 */
public interface UserDefinedSObject extends SObject {
    /**
     * @return id associated with this object
     */
    String getId();
}
