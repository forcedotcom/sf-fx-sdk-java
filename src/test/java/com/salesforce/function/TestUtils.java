package com.salesforce.function;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.*;
import com.salesforce.function.api.composite.CompositeAPI;
import com.salesforce.function.api.composite.impl.CompositeAPIImpl;
import com.salesforce.function.api.composite.request.CompositeRequest;
import com.salesforce.function.api.composite.request.CompositeSubrequest;
import com.salesforce.function.api.composite.request.impl.CompositeRequestImpl;
import com.salesforce.function.api.composite.request.impl.CompositeSubrequestImpl;
import com.salesforce.function.api.composite.response.CompositeResponse;
import com.salesforce.function.api.composite.response.CompositeSubresponse;
import com.salesforce.function.api.model.SObjectType;
import com.salesforce.function.api.springml.ForceAPI;
import com.salesforce.function.api.springml.impl.ForceAPIImpl;
import com.salesforce.function.api.springml.model.SOQLResult;
import com.salesforce.function.api.util.Config;
import com.salesforce.function.api.util.OrgConfig;
import com.salesforce.function.util.Constants;

/**
 * <p>
 * <ol>
 * <li>Create a connected app in a test org</li>
 * <li>Create 'src/test/resources/credentials.properties' and with information similar to the following</li>
 * </ol>
 * </p>
 *
 * <p>
 * <code>
 * instance.url = http://jbartolott-wsl1.internal.salesforce.com:6109<br/>
 * client.id = 3MVG9AOp4kbriZOJ5l4bscyHWszj4Z4Ra9Qa523lGgOqNIvsHplzdt8tqkfPGSwanE.YJB_t6weATokN7xJL2<br/>
 * client.secret = 722F27579A76DD3CF4320275CFF4533FE8EA7159EA7F9F55019E5DF4F665DBFC<br/>
 * username = unit_of_work_ee_1@salesforce.com<br/>
 * password = test1234<br/>
 * </code>
 * </p>
 *
 * <p>
 * Invoke {@link #login()} to obtain an OrgConfig that gives tests access to the test org.
 * </p>
 *
 * @since 218
 */
public class TestUtils extends Assert {
    public static final String PROPERTY_FILENAME = "credentials.properties";
    public static final String PROPERTY_INSTANCE_URL = "instance.url";
    public static final String PROPERTY_ACCESS_TOKEN = "access.token";
    public static final String PROPERTY_CLIENT_ID = "client.id";
    public static final String PROPERTY_CLIENT_SECRET = "client.secret";
    public static final String PROPERTY_USERNAME = "username";
    public static final String PROPERTY_PASSWORD = "password";

    private static final Properties CREDENTIALS;

    static {
        CREDENTIALS = new Properties();
        try (InputStream is = TestUtils.class.getClassLoader().getResourceAsStream(PROPERTY_FILENAME)) {
            if (is != null) {
                CREDENTIALS.load(is);
            } else {
                throw new RuntimeException("Unable to find " + PROPERTY_FILENAME);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to find " + PROPERTY_FILENAME, e);
        }
    }

    public static Properties getCredentials() {
        return CREDENTIALS;
    }

    public static String getInstanceUrl() {
        return CREDENTIALS.getProperty(PROPERTY_INSTANCE_URL);
    }

    /**
     * @return <code>Bearer</code> authorization token obtained using the credentials found in {@link #PROPERTY_FILENAME}
     */
    public static OrgConfig login() throws Exception {
        String accessToken = CREDENTIALS.getProperty(PROPERTY_ACCESS_TOKEN);
        if (accessToken == null) {
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost post = new HttpPost(getInstanceUrl() + "/services/oauth2/token");

                MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create()
                        .addTextBody("grant_type", "password")
                        .addTextBody("client_id", CREDENTIALS.getProperty(PROPERTY_CLIENT_ID))
                        .addTextBody("client_secret", CREDENTIALS.getProperty(PROPERTY_CLIENT_SECRET))
                        .addTextBody("username", CREDENTIALS.getProperty(PROPERTY_USERNAME))
                        .addTextBody("password", CREDENTIALS.getProperty(PROPERTY_PASSWORD));
                post.setEntity(entityBuilder.build());

                try (CloseableHttpResponse response = httpClient.execute(post)) {
                    String responseBody = EntityUtils.toString(response.getEntity());
                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == HttpStatus.SC_OK) {
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode authResponse = mapper.readTree(responseBody);
                        accessToken = authResponse.get("access_token").asText();
                        return new OrgConfig(getInstanceUrl(), Constants.CURRENT_API_VERSION, accessToken);
                    } else {
                        throw new RuntimeException(responseBody);
                    }
                }
            }
        }

        return new OrgConfig(getInstanceUrl(), Constants.CURRENT_API_VERSION, accessToken);
    }
    
    /**
     * Insert an Account and return an AccountId/AccountName Pair.
     */
    public static Pair<String, String> insertAccount(Config config) throws Exception {
        CompositeAPI compositeAPI = new CompositeAPIImpl(config);;
        String accountName = "Account - " + System.currentTimeMillis();

        CompositeSubrequest insertAccountSubrequest = CompositeSubrequestImpl.insertBuilder()
                .sobjecType(SObjectType.Account).name(accountName).build();
        CompositeRequest compositeRequest = new CompositeRequestImpl();
        compositeRequest.addSubRequest(insertAccountSubrequest);

        CompositeResponse compositeResponse = compositeAPI.invoke(compositeRequest);
        assertNotNull(compositeResponse);
        List<CompositeSubresponse> compositeSubresponses = compositeResponse.getCompositeSubresponses();
        assertNotNull(compositeSubresponses);
        assertEquals(compositeSubresponses.toString(), 1, compositeSubresponses.size());

        CompositeSubresponse accountSubresponse = compositeSubresponses.get(0);
        assertNotNull(accountSubresponse);
        assertEquals(new Integer(HttpStatus.SC_CREATED), accountSubresponse.getHttpStatusCode());
        String accountId = accountSubresponse.getId();

        return Pair.of(accountId, accountName);
    }

    public static void assertAccountName(Config config, String accountId, String expectedAccountName) throws Exception {
        ForceAPI forceApi = new ForceAPIImpl(config);
        
        SOQLResult soqlResult = forceApi.query(String.format("SELECT Name FROM Account WHERE Id='%s'", accountId));
        assertNotNull(soqlResult);
        assertNotNull(soqlResult.getRecords());
        assertEquals(1, soqlResult.getTotalSize());
        
        Map<String, Object> record = soqlResult.getRecords().get(0);
        assertEquals(expectedAccountName, record.get("Name"));
    }
    
    public static void assertContact(Config config, String contactId, String expectedAccountId, String expectedLastName) throws Exception {
        ForceAPI forceApi = new ForceAPIImpl(config);
        
        SOQLResult soqlResult = forceApi.query(String.format("SELECT Account.Id, LastName FROM Contact WHERE Id='%s'", contactId));
        assertNotNull(soqlResult);
        assertNotNull(soqlResult.getRecords());
        assertEquals(1, soqlResult.getTotalSize());
        
        Map<String, Object> record = soqlResult.getRecords().get(0);
        assertEquals(expectedLastName, record.get("LastName"));
        
        // Assert that the Contact is associated with the correct Account
        @SuppressWarnings("unchecked")
        Map<String, Object> account = (Map<String, Object>)record.get("Account");
        assertNotNull(account);
        assertEquals(expectedAccountId, account.get("Id"));
    }

    /**
     * Pretty print a string as json object
     */
    public static void prettyPrint(String out) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        Object object = mapper.readValue(out, Object.class);;
        System.out.print(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object));
    }
}
