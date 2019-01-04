/*
 * Copyright 2015 - 2017, salesforce-wave-api, springml, oolong
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
package com.salesforce.function.api.springml;

import com.salesforce.function.api.model.Event;
import com.salesforce.function.api.model.SObject;
import com.salesforce.function.api.springml.model.AddTaskRequest;
import com.salesforce.function.api.springml.model.AddTaskResponse;
import com.salesforce.function.api.springml.model.ForceResponse;
import com.salesforce.function.api.springml.model.QueryResult;
import com.salesforce.function.api.springml.model.SOQLResult;

/**
 * JAVA client for Salesforce REST API calls
 */
public interface ForceAPI {
    /**
     * Execute the given SOQL by using "/query" API
     * @param soql - SOQL to be executed.
     * @return {@link QueryResult}
     * @throws Exception
     */
    public SOQLResult query(String soql) throws Exception;

    /**
     * Query further records using nextRecordsURL
     * @param oldResult
     * @return
     * @throws Exception
     */
    public SOQLResult queryMore(SOQLResult oldResult) throws Exception;

    /**
     * Creates task with given details in salesforce
     * @return
     */
    public AddTaskResponse addTask(AddTaskRequest addTask) throws Exception;

    /**
     * Insert a salesforce object
     * @param object - Name of the salesforce object
     * @param content - Json content of the object to be saved
     */
    public ForceResponse insertObject(String object, String content) throws Exception;

    public ForceResponse insertSObject(SObject sobject) throws Exception;

    public ForceResponse sendEvent(Event event) throws Exception;

    /**
     * Insert a salesforce object
     * @param object - Name of the salesforce object
     * @param content - Json content of the object to be saved
     */
//    public InsertObjectsResponse insertObjects(String object, List<String> objects) throws Exception;

    public String getSFEndpoint() throws Exception;
}
