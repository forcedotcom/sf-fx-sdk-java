/*
 * Copyright 2015 - 2017, salesforce-wave-api, oolong, springml
 * Contributors  :
 *    Kagan Turgut, oolong
 * 	  Samual Alexander, springml
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
package com.salesforce.function.api.springml.model.dataset;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "datasets",
    "nextPageUrl",
    "totalSize",
    "url"
})
public class DatasetsResponse {

    @JsonProperty("datasets")
    private List<Dataset> datasets = null;
    @JsonProperty("nextPageUrl")
    private Object nextPageUrl;
    @JsonProperty("totalSize")
    private Integer totalSize;
    @JsonProperty("url")
    private String url;

    @JsonProperty("datasets")
    public List<Dataset> getDatasets() {
        return datasets;
    }

    @JsonProperty("datasets")
    public void setDatasets(List<Dataset> datasets) {
        this.datasets = datasets;
    }

    @JsonProperty("nextPageUrl")
    public Object getNextPageUrl() {
        return nextPageUrl;
    }

    @JsonProperty("nextPageUrl")
    public void setNextPageUrl(Object nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    @JsonProperty("totalSize")
    public Integer getTotalSize() {
        return totalSize;
    }

    @JsonProperty("totalSize")
    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
