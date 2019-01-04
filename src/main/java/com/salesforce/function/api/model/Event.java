package com.salesforce.function.api.model;

import java.util.Map;

/**
 * TOD
 *
 */
public interface Event {

    String getName();

    Map<String, String> getValues();

    void addValue(String key, String value);

}