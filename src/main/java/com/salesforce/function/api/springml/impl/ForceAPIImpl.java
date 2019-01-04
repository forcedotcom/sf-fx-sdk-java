/*
 * Copyright 2015 - 2017, salesforce-wave-api, springml, oolong
 * Author  :
 * 	  Samual Alexander, springml
 * Contributors:
 *    Kagan Turgut, oolong
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

import static com.salesforce.function.api.springml.util.WaveAPIConstants.PATH_QUERY;
import static com.salesforce.function.api.springml.util.WaveAPIConstants.PATH_SOBJECTS;
import static com.salesforce.function.api.springml.util.WaveAPIConstants.PATH_TASK;
import static com.salesforce.function.api.springml.util.WaveAPIConstants.QUERY_PARAM;
import static com.salesforce.function.api.springml.util.WaveAPIConstants.SERVICE_PATH;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesforce.function.api.model.Event;
import com.salesforce.function.api.model.SObject;
import com.salesforce.function.api.springml.ForceAPI;
import com.salesforce.function.api.springml.model.AddTaskRequest;
import com.salesforce.function.api.springml.model.AddTaskResponse;
import com.salesforce.function.api.springml.model.ForceResponse;
import com.salesforce.function.api.springml.model.SOQLResult;
import com.salesforce.function.api.util.Config;
import com.salesforce.function.log.LogFactory;
import com.salesforce.function.log.Logger;

/**
 * Default implementation for Salesforce REST API calls
 */
public class ForceAPIImpl extends AbstractAPIImpl implements ForceAPI {

    private static final Logger LOG = LogFactory.getInstance().getLogger(ForceAPIImpl.class);

    public ForceAPIImpl(Config config) throws Exception {
        super(config);
    }

    @Override
    public SOQLResult query(String soql) throws Exception {
        String queryPath = getQueryPath();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("q", soql);
        URI queryURI = config.getRequestURI(queryPath, parameters);

        return query(queryURI, 1);
    }

    @Override
    public SOQLResult queryMore(SOQLResult soqlResult) throws Exception {
        if (soqlResult.isDone()) {
            throw new Exception("Already all records are read");
        }

        URI requestURI = config.getRequestURI(soqlResult.getNextRecordsUrl());
        return query(requestURI, 1);
    }

    @Override
    public AddTaskResponse addTask(AddTaskRequest addTask) throws Exception {
        String taskPath = getTaskPath();
        URI taskURI = config.getRequestURI(taskPath);

        String request = getObjectMapper().writeValueAsString(addTask);
        String responseStr = getHttpHelper().post(taskURI, config.getSessionId(), request);

        AddTaskResponse response = null;
        try {
            response = getObjectMapper().readValue(responseStr.getBytes(), AddTaskResponse.class);
        } catch (IOException e) {
            response = new AddTaskResponse();
            response.setError(responseStr);
            response.setSuccess(false);
        }

        return response;
    }

    @Override
    public ForceResponse insertObject(String object, String jsonContent) throws Exception {
        String insertPath = getInsertPath(object);
        URI taskURI = config.getRequestURI(insertPath);

        String responseStr = getHttpHelper().post(taskURI, config.getSessionId(), jsonContent);

        ForceResponse response = null;
        try {
            response = getObjectMapper().readValue(responseStr.getBytes(), ForceResponse.class);
        } catch (IOException e) {
            response = new ForceResponse();
            response.setError(responseStr);
            response.setSuccess(false);
        }

        return response;
    }

    @Override
    public ForceResponse insertSObject(SObject sobject) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return insertObject(sobject.getSObjectType(), mapper.writeValueAsString(sobject));
    }

    @Override
    public ForceResponse sendEvent(Event event) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return insertObject(event.getName(), mapper.writeValueAsString(event));
    }

    @Override
    public String getSFEndpoint() throws Exception {
        URI seURI = new URI(config.getServiceEndpoint());
        return new URI(seURI.getScheme(),seURI.getUserInfo(), seURI.getHost(), seURI.getPort(),
                null, null, null).toString();
    }

    private String getInsertPath(String object) {
        StringBuilder objPath = new StringBuilder();
        objPath.append(SERVICE_PATH);
        objPath.append("v");
        objPath.append(config.getApiVersion());
        objPath.append(PATH_SOBJECTS);
        objPath.append(object);

        return objPath.toString();
    }

    private String getTaskPath() {
        StringBuilder taskPath = new StringBuilder();
        taskPath.append(SERVICE_PATH);
        taskPath.append("v");
        taskPath.append(config.getApiVersion());
        taskPath.append(PATH_TASK);

        return taskPath.toString();
    }

    private SOQLResult query(URI queryURI, int attempt) throws Exception {
        SOQLResult soqlResult = null;
        try {
            String response = getHttpHelper().get(queryURI, config.getSessionId(), config.getBatchSize());

            LOG.debug("Query Response from server " + response);
            soqlResult = getObjectMapper().readValue(response.getBytes(), SOQLResult.class);
        } catch (Exception e) {
            LOG.warn("Error while executing salesforce query ", e);
            if (e.getMessage().contains("QUERY_TIMEOUT") && attempt < 5) {
                LOG.info("Retrying salesforce query");
                LOG.info("Retry attempt " + attempt);
                //Retrying incase of Salesforce service timeout
                soqlResult = query(queryURI, ++attempt);
            } else if (e.getMessage().contains("INVALID_SESSION_ID") && attempt < 5) {
                config.closeConnection();
                LOG.info("Retrying with new connection...");
                soqlResult = query(queryURI, ++attempt);
            } else {
                throw e;
            }
        }

        return soqlResult;
    }

    private String getQueryPath() {
        StringBuilder queryPath = new StringBuilder();
        queryPath.append(SERVICE_PATH);
        queryPath.append("v");
        queryPath.append(config.getApiVersion());
        queryPath.append(PATH_QUERY);

        return queryPath.toString();
    }

}
