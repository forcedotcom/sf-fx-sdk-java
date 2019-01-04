package com.salesforce.function.api.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpStatus;
import org.junit.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesforce.function.TestUtils;
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
import com.salesforce.function.util.Constants;

/**
 * Integration tests for {@link CompositeAPI} This test requires a properties file as described in {@link TestUtils} and
 * a running server.
 */
public class CompositeAPIImplIntegrationTest extends Assert {
    private Config config;
    private CompositeAPI compositeAPI;

    @Before
    public void setup() throws Exception {
        this.config = TestUtils.login();
        this.compositeAPI = new CompositeAPIImpl(config);
    }

    /**
     * Insert an Account using the CompositeAPI. Assert that the correct HTTP method was invoked and that the Account
     * exists in the org with the correct name.
     */
    @Test
    public void testInsertAccount() throws Exception {
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
        validateInsertCompositeSubResponse(accountSubresponse, SObjectType.Account);

        // Verify the name
        TestUtils.assertAccountName(config, accountSubresponse.getId(), accountName);
    }

    /**
     * Insert an Account. Execute a GET request using the Id and verify it can be retrieved.
     */
    @Test
    public void testGetExistingAccountById() throws Exception {
        Pair<String, String> idName = TestUtils.insertAccount(config);
        String accountId = idName.getLeft();
        String accountName = idName.getRight();

        CompositeSubrequest getAccountSubrequest = CompositeSubrequestImpl.httpGETBuilder()
                .sobjecType(SObjectType.Account).id(accountId).build();
        CompositeRequest compositeRequest = new CompositeRequestImpl();
        compositeRequest.addSubRequest(getAccountSubrequest);
        CompositeResponse compositeResponse = compositeAPI.invoke(compositeRequest);
        assertNotNull(compositeResponse);
        List<CompositeSubresponse> compositeSubresponses = compositeResponse.getCompositeSubresponses();
        assertNotNull(compositeSubresponses);
        assertEquals(compositeSubresponses.toString(), 1, compositeSubresponses.size());
        CompositeSubresponse subresponse = compositeSubresponses.get(0);

        assertEquals(new Integer(HttpStatus.SC_OK), subresponse.getHttpStatusCode());
        assertEquals(accountId, subresponse.getBody().get("Id"));
        assertEquals(accountName, subresponse.getBody().get("Name"));
    }

    /**
     * Insert an Account. Execute a GET request using the Id and verify it can be updated.
     */
    @Test
    public void testUpdateExistingAccountById() throws Exception {
        Pair<String, String> idName = TestUtils.insertAccount(config);
        String accountId = idName.getLeft();
        String accountName = idName.getRight();
        String newAccountName = "Updated " + accountName;

        CompositeSubrequest getAccountSubrequest = CompositeSubrequestImpl.patchBuilder()
                .sobjecType(SObjectType.Account).id(accountId).name(newAccountName).build();
        CompositeRequest compositeRequest = new CompositeRequestImpl();
        compositeRequest.addSubRequest(getAccountSubrequest);
        CompositeResponse compositeResponse = compositeAPI.invoke(compositeRequest);
        assertNotNull(compositeResponse);
        List<CompositeSubresponse> compositeSubresponses = compositeResponse.getCompositeSubresponses();
        assertNotNull(compositeSubresponses);
        assertEquals(compositeSubresponses.toString(), 1, compositeSubresponses.size());
        CompositeSubresponse subresponse = compositeSubresponses.get(0);

        assertEquals(new Integer(HttpStatus.SC_NO_CONTENT), subresponse.getHttpStatusCode());
        TestUtils.assertAccountName(config, accountId, newAccountName);
    }

    /**
     * Insert an Account. Execute a DELETE request using the Id and verify it has been deleted.
     */
    @Test
    public void testDeleteExistingAccountById() throws Exception {
        Pair<String, String> idName = TestUtils.insertAccount(config);
        String accountId = idName.getLeft();

        ForceAPI forceApi = new ForceAPIImpl(config);

        SOQLResult soqlResult = forceApi.query(String.format("SELECT Name FROM Account WHERE Id='%s'", accountId));
        assertNotNull(soqlResult);
        assertNotNull(soqlResult.getRecords());
        assertEquals(1, soqlResult.getTotalSize());

        CompositeSubrequest getAccountSubrequest = CompositeSubrequestImpl.deleteBuilder()
                .sobjecType(SObjectType.Account).id(accountId).build();
        CompositeRequest compositeRequest = new CompositeRequestImpl();
        compositeRequest.addSubRequest(getAccountSubrequest);
        CompositeResponse compositeResponse = compositeAPI.invoke(compositeRequest);
        assertNotNull(compositeResponse);
        List<CompositeSubresponse> compositeSubresponses = compositeResponse.getCompositeSubresponses();
        assertNotNull(compositeSubresponses);
        assertEquals(compositeSubresponses.toString(), 1, compositeSubresponses.size());
        CompositeSubresponse subresponse = compositeSubresponses.get(0);

        assertEquals(new Integer(HttpStatus.SC_NO_CONTENT), subresponse.getHttpStatusCode());
        soqlResult = forceApi.query(String.format("SELECT Name FROM Account WHERE Id='%s'", accountId));
        assertNotNull(soqlResult);
        assertNotNull(soqlResult.getRecords());
        assertEquals(0, soqlResult.getTotalSize());
    }

    /**
     * Insert an Account and retrieve it using the same CompositeAPI. Assert that the correct HTTP method was invoked
     * and that the Account exists in the org with the correct name.
     */
    @Test
    public void testInsertAndGetAccount() throws Exception {
        String accountName = "Account - " + System.currentTimeMillis();
        CompositeRequest compositeRequest = new CompositeRequestImpl();

        CompositeSubrequest insertAccountSubrequest = CompositeSubrequestImpl.insertBuilder()
                .sobjecType(SObjectType.Account).name(accountName).build();
        String accountRefId = insertAccountSubrequest.getReferenceId();
        compositeRequest.addSubRequest(insertAccountSubrequest);
        compositeRequest = mapToFromJson(compositeRequest);

        CompositeSubrequest accountSubRequest = compositeRequest.getSubrequest(accountRefId);

        CompositeSubrequest getAccountSubrequest = CompositeSubrequestImpl.httpGETBuilder()
                .sobjecType(SObjectType.Account).id(accountSubRequest).build();
        compositeRequest.addSubRequest(getAccountSubrequest);

        CompositeResponse compositeResponse = compositeAPI.invoke(compositeRequest);
        assertNotNull(compositeResponse);
        List<CompositeSubresponse> compositeSubresponses = compositeResponse.getCompositeSubresponses();
        assertNotNull(compositeSubresponses);
        assertEquals(compositeSubresponses.toString(), 2, compositeSubresponses.size());

        CompositeSubresponse insertAccountSubresponse = compositeSubresponses.get(0);
        validateInsertCompositeSubResponse(insertAccountSubresponse, SObjectType.Account);
        String accountId = insertAccountSubresponse.getId();

        CompositeSubresponse getAccountSubresponse = compositeSubresponses.get(1);
        assertEquals(new Integer(HttpStatus.SC_OK), getAccountSubresponse.getHttpStatusCode());
        assertEquals(accountId, getAccountSubresponse.getBody().get("Id"));
        assertEquals(accountName, getAccountSubresponse.getBody().get("Name"));
    }

    /**
     * Insert an Account and Contact using the same CompositeAPI. Assert that the correct HTTP methods were invoked and
     * that the relationship has been established between the Contact and Account.
     */
    @Test
    public void testInsertAccountAndContact() throws Exception {
        String accountName = "Account - " + System.currentTimeMillis();
        String contactLastName = "Sample Contact - " + System.currentTimeMillis();
        CompositeRequest compositeRequest = new CompositeRequestImpl();

        CompositeSubrequest insertAccountSubrequest = CompositeSubrequestImpl.insertBuilder()
                .sobjecType(SObjectType.Account).name(accountName).build();
        String accountRefId = insertAccountSubrequest.getReferenceId();
        compositeRequest.addSubRequest(insertAccountSubrequest);
        // Ensure that a request can be mapped to and from json. This verifies that we aren't keeping information in
        // Java objects that are lost by serialization
        compositeRequest = mapToFromJson(compositeRequest);

        // Ensure that the deserialized CompositeSubrequest has preserved the list of subrequets.
        CompositeSubrequest indirectSubRequest = compositeRequest.getSubrequest(accountRefId);
        CompositeSubrequest insertContactSubrequest = CompositeSubrequestImpl.insertBuilder()
                .sobjecType(SObjectType.Contact).relationship("AccountId", indirectSubRequest)
                .value("LastName", contactLastName).build();
        compositeRequest.addSubRequest(insertContactSubrequest);
        compositeRequest = mapToFromJson(compositeRequest);

        CompositeResponse compositeResponse = compositeAPI.invoke(compositeRequest);
        assertNotNull(compositeResponse);
        List<CompositeSubresponse> compositeSubresponses = compositeResponse.getCompositeSubresponses();
        assertNotNull(compositeSubresponses);
        assertEquals(compositeSubresponses.toString(), 2, compositeSubresponses.size());

        CompositeSubresponse accountSubresponse = compositeSubresponses.get(0);
        validateInsertCompositeSubResponse(accountSubresponse, SObjectType.Account);
        String accountId = accountSubresponse.getId();
        // Verify the name
        TestUtils.assertAccountName(config, accountId, accountName);

        CompositeSubresponse contactsSubresponse = compositeSubresponses.get(1);
        validateInsertCompositeSubResponse(contactsSubresponse, SObjectType.Contact);

        // Verify the name
        TestUtils.assertContact(config, contactsSubresponse.getId(), accountId, contactLastName);
    }

    /**
     * Translate to json and back to ensure that the objects will work when shared across JVMs as json.
     */
    private CompositeRequest mapToFromJson(CompositeRequest compositeRequest) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(mapper.writeValueAsString(compositeRequest), CompositeRequest.class);
    }

    @SuppressWarnings("unchecked")
    private void validateInsertCompositeSubResponse(CompositeSubresponse compositeSubRespose, SObjectType sObjectType) {
        assertNotNull(compositeSubRespose);
        assertEquals(new Integer(HttpStatus.SC_CREATED), compositeSubRespose.getHttpStatusCode());

        String referenceId = compositeSubRespose.getReferenceId();
        assertNotNull(referenceId);
        assertTrue(referenceId, referenceId.startsWith(sObjectType.value()));

        Map<String, Object> body = compositeSubRespose.getBody();
        assertNotNull(body);

        Boolean success = (Boolean)body.get("success");
        assertNotNull(success);
        assertTrue(success);
        assertEquals(success, compositeSubRespose.isSuccess());

        List<String> errors = (List<String>)body.get("errors");
        assertNotNull(errors);
        assertEquals(errors.toString(), 0, errors.size());
        assertEquals(errors, compositeSubRespose.getErrors());

        String id = (String)body.get("id");
        assertNotNull(id);
        assertTrue(id, id.startsWith(sObjectType.keyPrefix()));
        assertEquals(id, compositeSubRespose.getId());

        Map<String, String> httpHeaders = compositeSubRespose.getHttpHeaders();
        assertNotNull(httpHeaders);
        assertEquals(httpHeaders.toString(), 1, httpHeaders.size());
        assertTrue(httpHeaders.toString(), httpHeaders.containsKey("Location"));
        String location = httpHeaders.get("Location");
        assertNotNull(location);
        assertEquals(String.format("/services/data/v%s/sobjects/%s/%s", Constants.CURRENT_API_VERSION,
                sObjectType.value(), id), location);
        assertEquals(location, compositeSubRespose.getLocation());
    }
}
