/*
 * Copyright 2015 - 2017, salesforce-wave-api, springml
 * Contributors  :
 * 	  Samual Alexander, springml
 *    Kagan Turgut
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.salesforce.function.api.springml.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.salesforce.function.api.util.HTTPHelper;
import com.salesforce.function.api.springml.util.impl.HTTPHelperImpl;
import com.salesforce.function.api.util.Config;

/**
 * Abstract class to have common methods
 */
public abstract class AbstractAPIImpl {
    
    private ObjectMapper objectMapper;
    private XmlMapper xmlMapper;
    private CsvMapper csvMapper;
    private HTTPHelper httpHelper;
    protected Config config;
    private ObjectReader objectReader;

    public AbstractAPIImpl(Config config) throws Exception {
        setHttpHelper(new HTTPHelperImpl());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE);
        setObjectMapper(objectMapper);

        this.xmlMapper = new XmlMapper();

        this.csvMapper = new CsvMapper();
        CsvSchema schema = CsvSchema.builder().setColumnSeparator(',').build();
        csvMapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
        objectReader = csvMapper.readerFor(String[].class).with(schema);

        this.config = config;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public HTTPHelper getHttpHelper() {
        return httpHelper;
    }

    public void setHttpHelper(HTTPHelper httpHelper) {
        this.httpHelper = httpHelper;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public XmlMapper getXmlMapper() {
        return xmlMapper;
    }

    public void setXmlMapper(XmlMapper xmlMapper) {
        this.xmlMapper = xmlMapper;
    }

    public CsvMapper getCsvMapper() {
        return csvMapper;
    }

    public ObjectReader getObjectReader() {
        return objectReader;
    }

    public void setObjectReader(ObjectReader reader) {
        this.objectReader=reader;
    }

    public void setCsvMapper(CsvMapper csvMapper) {
        this.csvMapper = csvMapper;
    }

}
