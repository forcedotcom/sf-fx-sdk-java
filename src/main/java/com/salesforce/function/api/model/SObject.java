package com.salesforce.function.api.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents a Salesforce Entity for CRUD operations. 
 */
public interface SObject {
    /**
     * @return Type such as "Account", "Contact"
     */
    String getSObjectType();
    
    /**
     * {@link java.util.UUID} that uniquely identifies this object in the context of the current code execution. This is 
     * a transient property that the server is not aware of.
     * 
     * @return the String representation of the UUID. 
     */
    String getUUID();

    /**
     * @return the map of values that has been set on this object in the context of the current code execution.
     */
    Map<String, Object> getValues();
    
    /**
     * 
     * @param key
     * @return
     */
    <T> T getValue(String key);
    
    /**
     * @return the previous value or null if one wasn't previously set
     */
    Object setValue(String key, Object value);

    /**
     * The referenceId that was used to INSERT this object if the INSERT was part of the unit of work. All other
     * operations should refer to this referenceId.
     */
    String getReferenceId();
    
    /**
     * Get the id that other SObjects should use to refer to this object
     */
    @JsonIgnore
    String getFkId();
}