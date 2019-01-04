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
package com.salesforce.function.api.springml.util;

import static com.salesforce.function.api.springml.util.WaveAPIConstants.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.http.client.utils.URIBuilder;

import com.salesforce.function.api.util.Config;
import com.salesforce.function.log.LogFactory;
import com.salesforce.function.log.Logger;

import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class SFConfig implements Config {

    private static final Logger LOG = LogFactory.getInstance().getLogger(SFConfig.class);

    private String username;
    private String password;
    private String loginURL;
    private String apiVersion;
    private Integer batchSize;
    private PartnerConnection partnerConnection;
    private Integer maxRetry = 5;

    public SFConfig(String username, String password, String loginURL,
            String apiVersion) throws Exception {
        this(username, password, loginURL, apiVersion, null);
    }

    public SFConfig(String username, String password, String loginURL,
            String apiVersion, Integer batchSize) throws Exception {
        this.username = username;
        this.password = password;
        this.loginURL = loginURL;
        this.apiVersion = apiVersion;
        this.batchSize = batchSize;

        this.partnerConnection = createPartnerConnection();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getInstanceURL() {
        return loginURL;
    }

    public String getLoginURL() {
        return loginURL;
    }

    public void setLoginURL(String loginURL) {
        this.loginURL = loginURL;
    }

    @Override
    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    @Override
    public Integer getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }

    public Integer getMaxRetry() {
        return maxRetry;
    }

    public void setMaxRetry(Integer maxRetry) {
        this.maxRetry = maxRetry;
    }

    @Override
    public String getSessionId() {
        return partnerConnection.getConfig().getSessionId();
    }

    @Override
    public URI getRequestURI(String path) throws URISyntaxException {
        return getRequestURI(path, null);
    }

    @Override
    public URI getRequestURI(String path, Map<String, String> parameters) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(partnerConnection.getConfig().getServiceEndpoint());
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            uriBuilder.addParameter(parameter.getKey(), parameter.getValue());
        }

        return uriBuilder.build();
    }

    @Override
    public String getServiceEndpoint()  {
        return partnerConnection.getConfig().getServiceEndpoint();
    }

    private String getAuthEndpoint(String loginURL) throws Exception {
        URI loginURI = new URI(loginURL);

        return new URI(loginURI.getScheme(), loginURI.getUserInfo(), loginURI.getHost(),
                loginURI.getPort(), PATH_SOAP_ENDPOINT, null, null).toString();
    }

    @Override
    public void closeConnection() {
        try {
            if (this.partnerConnection != null) {
                this.partnerConnection.logout();
            }
        } catch (Exception e) {
            // Ignore it
        }

        this.partnerConnection = null;
    }

    private PartnerConnection createPartnerConnection() throws Exception {
        ConnectorConfig config = new ConnectorConfig();
        LOG.debug("Connecting SF Partner Connection using " + username);
        config.setUsername(username);
        config.setPassword(password);
        String authEndpoint = getAuthEndpoint(loginURL);
        LOG.info("loginURL : " + authEndpoint);
        config.setAuthEndpoint(authEndpoint);
        config.setServiceEndpoint(authEndpoint);

        try {
            return Connector.newConnection(config);
        } catch (ConnectionException ce) {
            LOG.error("Exception while creating connection", ce);
            throw new Exception(ce);
        }
    }

}
