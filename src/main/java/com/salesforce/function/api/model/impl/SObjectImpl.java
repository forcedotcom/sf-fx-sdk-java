package com.salesforce.function.api.model.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import org.apache.commons.lang3.StringUtils;

import com.salesforce.function.api.model.*;

import javax.xml.bind.annotation.XmlType;

/**
 * Implementation of {@link SObject}
 *
 * @since 218
 */
public abstract class SObjectImpl implements SObject {
    private final String uuid;
    private final String sobjectType;
    private final Map<String, Object> values;
    private final String referenceId;

    // used by wsdl2java to support sobject types in sdk
    public SObjectImpl() {
        if (!getClass().isAnnotationPresent(XmlType.class)) {
            throw new IllegalStateException("XmlType annotation is required");
        }
        String tmpSobjectType = ((XmlType)getClass().getAnnotation(XmlType.class)).name();
        this.uuid = UUID.randomUUID().toString();
        this.sobjectType = tmpSobjectType;
        this.values = new LinkedHashMap<>();
        this.referenceId = generateReferenceId(tmpSobjectType);
    }

    public SObjectImpl(SObjectType sObjectType) {
        this(sObjectType.value());
    }

    public SObjectImpl(String sobjectType) {
        if (StringUtils.isBlank(sobjectType)) { throw new IllegalStateException("Type is required"); }
        this.uuid = UUID.randomUUID().toString();
        this.sobjectType = sobjectType;
        this.values = new LinkedHashMap<>();
        this.referenceId = generateReferenceId(sobjectType);
    }

    public static String generateReferenceId(String type) {
        return type + "_" + UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public Object setValue(String key, Object value) {
        return this.values.put(key, value);
    }

    @Override
    public String getUUID() {
        return this.uuid;
    }

    @Override
    public Map<String, Object> getValues() {
        return Collections.unmodifiableMap(this.values);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getValue(String key) {
        return (T)values.get(key);
    }

    @Override
    public String getSObjectType() {
        return this.sobjectType;
    }

    @Override
    public String getReferenceId() {
        return this.referenceId;
    }

    public SObjectImpl value(String key, Object value) {
        this.values.put(key, value);
        return this;
    }

    @Override
    public String getFkId() {
        String id = getId(this);
        if (id != null) {
            return id;
        } else {
            return String.format("@{%s.id}", getReferenceId());
        }
    }

    /**
     * Work around the fact that the wsdl generated classes contain a getId method.
     */
    public static String getId(SObject sobject) {
        try {
            Method getMethod = sobject.getClass().getMethod("getId");
            return (String)getMethod.invoke(sobject);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Unions the values with the existing values. DOES NOT replace the map.
     */
    public SObjectImpl values(Map<String, String> values) {
        this.values.putAll(values);
        return this;
    }

    public SObjectImpl name(String name) {
        value("Name", name);
        return this;
    }

    public SObjectImpl relationship(SObject relation) {
        return relationship(relation, SObjectRelationshipFactory.INSTANCE);
    }

    public SObjectImpl relationship(SObject relation, IRelationshipHandler relationshipHandler) {
        SObjectRelationship sObjectRelationship = relationshipHandler.getSObjectRelationship(sobjectType, relation);
        return relationship(sObjectRelationship.getFkName(), sObjectRelationship.getSObject());
    }

    public SObjectImpl relationship(String fkName, SObject relation) {
        this.values.put(fkName, relation.getFkId());
        return this;
    }
}