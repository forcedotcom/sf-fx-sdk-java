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
package com.salesforce.function.api.springml.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.salesforce.function.api.springml.util.WaveAPIConstants;

@JsonIgnoreProperties
public class BatchInfo implements Serializable {
    private static final long serialVersionUID = 3366976509102917086L;

    private String id;
    private String jobId;
    private String state;
    private String stateMessage;
    private String createdDate;
    private String systemModstamp;
    private long numberRecordsProcessed;
    private long numberRecordsFailed;
    private long totalProcessingTime;
    private long apiActiveProcessingTime;
    private long apexProcessingTime;

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("BatchInfo (job:");
        sb.append(jobId);
        sb.append(" batch:");
        sb.append(id);
        sb.append(" state:");
        sb.append(state);
        sb.append(")");
    	return sb.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getState() {
        return state;
    }

    public Boolean isQueued() {
    	return state.equals(WaveAPIConstants.STR_QUEUED);
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getSystemModstamp() {
        return systemModstamp;
    }

    public void setSystemModstamp(String systemModstamp) {
        this.systemModstamp = systemModstamp;
    }

    public long getNumberRecordsProcessed() {
        return numberRecordsProcessed;
    }

    public void setNumberRecordsProcessed(long numberRecordsProcessed) {
        this.numberRecordsProcessed = numberRecordsProcessed;
    }

    public long getNumberRecordsFailed() {
        return numberRecordsFailed;
    }

    public void setNumberRecordsFailed(long numberRecordsFailed) {
        this.numberRecordsFailed = numberRecordsFailed;
    }

    public long getTotalProcessingTime() {
        return totalProcessingTime;
    }

    public void setTotalProcessingTime(long totalProcessingTime) {
        this.totalProcessingTime = totalProcessingTime;
    }

    public long getApiActiveProcessingTime() {
        return apiActiveProcessingTime;
    }

    public void setApiActiveProcessingTime(long apiActiveProcessingTime) {
        this.apiActiveProcessingTime = apiActiveProcessingTime;
    }

    public long getApexProcessingTime() {
        return apexProcessingTime;
    }

    public void setApexProcessingTime(long apexProcessingTime) {
        this.apexProcessingTime = apexProcessingTime;
    }

    public String getStateMessage() {
        return stateMessage;
    }

    public void setStateMessage(String stateMessage) {
        this.stateMessage = stateMessage;
    }

    public boolean hasDataToLoad() {
    	return !("NotProcessed".equals(this.state) || "Failed".equals(this.state));
    }

    public boolean isFailed() {
    	return "Failed".equals(this.state);
    }

    public boolean isCompleted() {
    	return "Completed".equals(this.state);
    }

    public boolean needsTime() {
    	return isQueued() || isInProgress();
    }

    public boolean isInProgress() {
    	return "InProgress".equals(this.state);
    }




}
