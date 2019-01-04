package com.salesforce.function.api.model.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesforce.function.api.model.Event;
import com.salesforce.function.api.model.SObject;

/**
 * TOD
 *
 */
public class EventImpl implements Event {

    @JsonIgnore
    private String id;
    @JsonIgnore
    private final String name;
    private final Map<String, String> values;

    public EventImpl(String name) {
        this(name, (Map<String, String>)null);
    }

    public EventImpl(String name, Map<String, String> values) {
        this.name = name;
        this.values = values != null ? values : new LinkedHashMap<String, String>();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Map<String, String> getValues() {
        return this.values;
    }

    @Override
    public void addValue(String key, String value) {
        this.values.put(key, value);
    }

}