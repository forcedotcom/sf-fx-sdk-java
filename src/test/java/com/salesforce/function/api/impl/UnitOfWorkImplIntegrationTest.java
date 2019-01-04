package com.salesforce.function.api.impl;

import java.util.*;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.*;

import com.salesforce.function.TestUtils;
import com.salesforce.function.api.*;
import com.salesforce.function.api.model.SObject;
import com.salesforce.function.api.model.SObjectType;
import com.salesforce.function.api.model.impl.UserDefinedSObjectImpl;
import com.salesforce.function.api.springml.ForceAPI;
import com.salesforce.function.api.springml.impl.ForceAPIImpl;
import com.salesforce.function.api.springml.model.SOQLResult;
import com.salesforce.function.api.util.Config;
import com.sforce.soap.enterprise.sobject.*;

/**
 * Integration tests for {@link UnitOfWork} This test requires a properties file as described in {@link TestUtils} and a
 * running server.
 */
public class UnitOfWorkImplIntegrationTest extends Assert {
    private Config config;
    private UnitOfWork unitOfWork;

    @Before
    public void setup() throws Exception {
        this.config = TestUtils.login();
        this.unitOfWork = new UnitOfWorkImpl(config);
    }

    /**
     * Insert an Account using the UnitOfWork. Assert that the correct HTTP method was invoked and that the Account
     * exists in the org with the correct name.
     */
    @Test
    public void testInsertAccount() throws Exception {
        String accountName = "Account - " + System.currentTimeMillis();

        SObject account = new UserDefinedSObjectImpl(SObjectType.Account).name(accountName);
        unitOfWork.registerNew(account);

        UnitOfWorkResponse unitOfWorkResponse = unitOfWork.commit();
        assertNotNull(unitOfWorkResponse);
        List<UnitOfWorkResult> unitOfWorkResults = unitOfWorkResponse.getResults(account);
        assertNotNull(unitOfWorkResults);
        assertEquals(unitOfWorkResults.toString(), 1, unitOfWorkResults.size());

        UnitOfWorkResult unitOfWorkResult = unitOfWorkResults.get(0);
        assertNoErrors(unitOfWorkResult);

        // Id
        assertNotNull(unitOfWorkResult.getId());
        assertTrue(unitOfWorkResult.getId(), unitOfWorkResult.getId().startsWith(SObjectType.Account.keyPrefix()));

        // Method
        assertEquals(Method.POST, unitOfWorkResult.getMethod());

        // Verify the name
        TestUtils.assertAccountName(config, unitOfWorkResult.getId(), accountName);
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

        SObject account = new UserDefinedSObjectImpl(SObjectType.Account).id(accountId).name(newAccountName);
        unitOfWork.registerModified(account);

        UnitOfWorkResponse unitOfWorkResponse = unitOfWork.commit();
        assertNotNull(unitOfWorkResponse);
        List<UnitOfWorkResult> unitOfWorkResults = unitOfWorkResponse.getResults(account);
        assertNotNull(unitOfWorkResults);
        assertEquals(unitOfWorkResults.toString(), 1, unitOfWorkResults.size());

        UnitOfWorkResult unitOfWorkResult = unitOfWorkResults.get(0);
        assertNoErrors(unitOfWorkResult);

        // Id is null for updates
        assertNull(unitOfWorkResult.getId());

        // Method
        assertEquals(Method.PATCH, unitOfWorkResult.getMethod());

        // Verify the name
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


        SObject account = new UserDefinedSObjectImpl(SObjectType.Account).id(accountId);
        unitOfWork.registerDeleted(account);

        UnitOfWorkResponse unitOfWorkResponse = unitOfWork.commit();
        assertNotNull(unitOfWorkResponse);
        List<UnitOfWorkResult> unitOfWorkResults = unitOfWorkResponse.getResults(account);
        assertNotNull(unitOfWorkResults);
        assertEquals(unitOfWorkResults.toString(), 1, unitOfWorkResults.size());

        UnitOfWorkResult unitOfWorkResult = unitOfWorkResults.get(0);
        assertNoErrors(unitOfWorkResult);

        // Id is null for updates
        assertNull(unitOfWorkResult.getId());

        // Method
        assertEquals(Method.DELETE, unitOfWorkResult.getMethod());

        // The Account should have been deleted
        soqlResult = forceApi.query(String.format("SELECT Name FROM Account WHERE Id='%s'", accountId));
        assertNotNull(soqlResult);
        assertNotNull(soqlResult.getRecords());
        assertEquals(0, soqlResult.getTotalSize());
    }

    /**
     * Insert an Account and update its Name using the same UnitOfWork. Assert that the correct HTTP methods were
     * invoked and that the Account exists in the org with the updated name.
     */
    @Test
    public void testInsertAndUpdateAccount() throws Exception {
        String originalName = "Account - " + System.currentTimeMillis();
        String newName = "Updated " + originalName;

        SObject account = new UserDefinedSObjectImpl(SObjectType.Account).name(originalName);
        unitOfWork.registerNew(account);

        // Now register an update to the Account changing its name
        account.setValue("Name", newName);
        unitOfWork.registerModified(account);

        UnitOfWorkResponse unitOfWorkResponse = unitOfWork.commit();
        assertNotNull(unitOfWorkResponse);
        List<UnitOfWorkResult> unitOfWorkResults = unitOfWorkResponse.getResults(account);
        assertNotNull(unitOfWorkResults);
        assertEquals(unitOfWorkResults.toString(), 2, unitOfWorkResults.size());

        UnitOfWorkResult unitOfWorkResult = unitOfWorkResults.get(0);
        assertNoErrors(unitOfWorkResult);

        // Id
        assertNotNull(unitOfWorkResult.getId());
        assertTrue(unitOfWorkResult.getId(), unitOfWorkResult.getId().startsWith(SObjectType.Account.keyPrefix()));

        // Method
        assertEquals(Method.POST, unitOfWorkResult.getMethod());

        // Verify the name is the updated one
        TestUtils.assertAccountName(config, unitOfWorkResult.getId(), newName);

        unitOfWorkResult = unitOfWorkResults.get(1);
        assertNoErrors(unitOfWorkResult);

        // Id - Patches don't sent back an id.
        // See
        // https://developer.salesforce.com/docs/atlas.en-us.api_rest.meta/api_rest/dome_composite_junction_object.htm#topic-title
        assertNull(unitOfWorkResult.getId());

        // Method
        assertEquals(Method.PATCH, unitOfWorkResult.getMethod());
    }

    /**
     * Insert an Account and Contact using the same UnitOfWork. Assert that the correct HTTP methods were invoked and
     * that the relationship has been established between the Contact and Account.
     */
    @Test
    public void testInsertAccountAndContact() throws Exception {
        String accountName = "Account With Contact - " + System.currentTimeMillis();
        String contactLastName = "LastName - " + System.currentTimeMillis();

        SObject account = new UserDefinedSObjectImpl(SObjectType.Account).name(accountName);
        unitOfWork.registerNew(account);

        SObject contact = new UserDefinedSObjectImpl(SObjectType.Contact).relationship(account).value("LastName", contactLastName);
        unitOfWork.registerNew(contact);

        UnitOfWorkResponse unitOfWorkResponse = unitOfWork.commit();
        assertNotNull(unitOfWorkResponse);

        List<UnitOfWorkResult> unitOfWorkResults = unitOfWorkResponse.getResults(account);
        assertNotNull(unitOfWorkResults);
        assertEquals(unitOfWorkResults.toString(), 1, unitOfWorkResults.size());

        UnitOfWorkResult unitOfWorkResult = unitOfWorkResults.get(0);
        assertNoErrors(unitOfWorkResult);

        // Id
        assertNotNull(unitOfWorkResult.getId());
        assertTrue(unitOfWorkResult.getId(), unitOfWorkResult.getId().startsWith(SObjectType.Account.keyPrefix()));
        String accountId = unitOfWorkResult.getId();

        // Method
        assertEquals(Method.POST, unitOfWorkResult.getMethod());

        // Verify the name
        TestUtils.assertAccountName(config, unitOfWorkResult.getId(), accountName);

        unitOfWorkResults = unitOfWorkResponse.getResults(contact);
        assertNotNull(unitOfWorkResults);
        assertEquals(unitOfWorkResults.toString(), 1, unitOfWorkResults.size());

        unitOfWorkResult = unitOfWorkResults.get(0);
        assertNoErrors(unitOfWorkResult);

        // Id
        assertNotNull(unitOfWorkResult.getId());
        assertTrue(unitOfWorkResult.getId(), unitOfWorkResult.getId().startsWith(SObjectType.Contact.keyPrefix()));

        // Method
        assertEquals(Method.POST, unitOfWorkResult.getMethod());

        // Verify the name
        TestUtils.assertContact(config, unitOfWorkResult.getId(), accountId, contactLastName);
    }

    /**
     * Insert an Account and byte stream attachment in the same UnitOfWork. Assert that the correct HTTP methods were
     * invoked and that the relationship has been established between the Attachment and Account.
     */
    @Test
    public void testAddAttachmentToAccountLooseType() throws Exception {
        String accountName = "Account With PDF - " + System.currentTimeMillis();
        String attachnmentName = "AttachmentBody.text";
        String attachmentText = "A string that will be attached to an Account";
        String attachmentBodyAsBase64 = new String(Base64.getEncoder().encode(attachmentText.getBytes()));

        SObject account = new UserDefinedSObjectImpl(SObjectType.Account).name(accountName);
        unitOfWork.registerNew(account);

        SObject attachment = new UserDefinedSObjectImpl(SObjectType.Attachment).name(attachnmentName)
                .value("Body", attachmentBodyAsBase64).relationship(account);
        unitOfWork.registerNew(attachment);

        UnitOfWorkResponse unitOfWorkResponse = unitOfWork.commit();
        assertNotNull(unitOfWorkResponse);

        List<UnitOfWorkResult> unitOfWorkResults = unitOfWorkResponse.getResults(account);
        assertNotNull(unitOfWorkResults);
        assertEquals(unitOfWorkResults.toString(), 1, unitOfWorkResults.size());

        UnitOfWorkResult unitOfWorkResult = unitOfWorkResults.get(0);
        assertNoErrors(unitOfWorkResult);

        // Id
        assertNotNull(unitOfWorkResult.getId());
        assertTrue(unitOfWorkResult.getId(), unitOfWorkResult.getId().startsWith(SObjectType.Account.keyPrefix()));
        String accountId = unitOfWorkResult.getId();

        // Method
        assertEquals(Method.POST, unitOfWorkResult.getMethod());

        // Verify the name
        TestUtils.assertAccountName(config, unitOfWorkResult.getId(), accountName);

        unitOfWorkResults = unitOfWorkResponse.getResults(attachment);
        assertNotNull(unitOfWorkResults);
        assertEquals(unitOfWorkResults.toString(), 1, unitOfWorkResults.size());

        unitOfWorkResult = unitOfWorkResults.get(0);
        assertNoErrors(unitOfWorkResult);

        // Id
        assertNotNull(unitOfWorkResult.getId());
        assertTrue(unitOfWorkResult.getId(), unitOfWorkResult.getId().startsWith(SObjectType.Attachment.keyPrefix()));

        // Method
        assertEquals(Method.POST, unitOfWorkResult.getMethod());

        assertAttachment(unitOfWorkResult.getId(), accountId, attachmentText.length());
    }

    @Test
    public void testAddAttachmentToAccountStrongType() throws Exception {
        String accountName = "Account With PDF - " + System.currentTimeMillis();
        String attachnmentName = "AttachmentBody.text";
        String attachmentText = "A string that will be attached to an Account";
        byte[] attachmentBodyAsBytes = "A string that will be attached to an Account".getBytes();

        Account account = new Account();
        account.setName(accountName);
        unitOfWork.registerNew(account);

        Attachment attachment = new Attachment();
        //attachment.relationship(account);
        attachment.setParentId(account.getFkId());
        attachment.setName(attachnmentName);
        attachment.setBody(attachmentBodyAsBytes);
        unitOfWork.registerNew(attachment);

        UnitOfWorkResponse unitOfWorkResponse = unitOfWork.commit();
        assertNotNull(unitOfWorkResponse);

        List<UnitOfWorkResult> unitOfWorkResults = unitOfWorkResponse.getResults(account);
        assertNotNull(unitOfWorkResults);
        assertEquals(unitOfWorkResults.toString(), 1, unitOfWorkResults.size());

        UnitOfWorkResult unitOfWorkResult = unitOfWorkResults.get(0);
        assertNoErrors(unitOfWorkResult);

        // Id
        assertNotNull(unitOfWorkResult.getId());
        assertTrue(unitOfWorkResult.getId(), unitOfWorkResult.getId().startsWith(SObjectType.Account.keyPrefix()));
        String accountId = unitOfWorkResult.getId();

        // Method
        assertEquals(Method.POST, unitOfWorkResult.getMethod());

        // Verify the name
        TestUtils.assertAccountName(config, unitOfWorkResult.getId(), accountName);

        unitOfWorkResults = unitOfWorkResponse.getResults(attachment);
        assertNotNull(unitOfWorkResults);
        assertEquals(unitOfWorkResults.toString(), 1, unitOfWorkResults.size());

        unitOfWorkResult = unitOfWorkResults.get(0);
        assertNoErrors(unitOfWorkResult);

        // Id
        assertNotNull(unitOfWorkResult.getId());
        assertTrue(unitOfWorkResult.getId(), unitOfWorkResult.getId().startsWith(SObjectType.Attachment.keyPrefix()));

        // Method
        assertEquals(Method.POST, unitOfWorkResult.getMethod());

        assertAttachment(unitOfWorkResult.getId(), accountId, attachmentText.length());
    }

    /**
     * Test that com.sforce.soap.enterprise.sobject fields are return by SObjectImpl.getValues().
     *
     * @throws Exception
     */
    @Test
    public void testSForceValues() throws Exception {
        String accountName = "Account With Contact - " + System.currentTimeMillis();
        String contactLastName = "LastName - " + System.currentTimeMillis();

        Account account = new Account();
        try {
            account.setValue("NOT", "ALLOWED");
            fail("setValues not allowed when using sforce object.  Use setters.");
        } catch (Exception e) {
            // expected
        }
        account.setName(accountName);
        Map<String, Object> values = account.getValues();
        assertTrue(values.toString(), !values.isEmpty());
        assertTrue(accountName.equals(values.get("Name")));

        Contact contact = new Contact();
        contact.setLastName(contactLastName);
        contact.setAccountId(account.getFkId());
        contact.setIsEmailBounced(true);
        values = contact.getValues();
        assertTrue(values.toString(), !values.isEmpty());
        assertTrue(contactLastName.equals(values.get("LastName")));
        assertTrue(account.getFkId().equals(values.get("AccountId")));
        assertTrue(values.get("IsEmailBounced") == Boolean.TRUE);
    }

    /**
     * Same as testInsertAccountAndContact but uses com.sforce.soap.enterprise.sobject objects.
     *
     * @throws Exception
     */
    @Test
    public void testInsertAccountAndContact_SForceObjectsUsingId() throws Exception {
        String accountName = "Account With Contact - " + System.currentTimeMillis();
        String contactLastName = "LastName - " + System.currentTimeMillis();

        Account account = new Account();
        account.setName(accountName);
        unitOfWork.registerNew(account);

        Contact contact = new Contact();
        contact.setLastName(contactLastName);
        contact.setAccountId(account.getFkId());
        unitOfWork.registerNew(contact);

        UnitOfWorkResponse unitOfWorkResponse = unitOfWork.commit();
        assertNotNull(unitOfWorkResponse);

        List<UnitOfWorkResult> unitOfWorkResults = unitOfWorkResponse.getResults(account);
        assertNotNull(unitOfWorkResults);
        assertEquals(unitOfWorkResults.toString(), 1, unitOfWorkResults.size());

        UnitOfWorkResult unitOfWorkResult = unitOfWorkResults.get(0);
        assertNoErrors(unitOfWorkResult);

        // Id
        assertNotNull(unitOfWorkResult.getId());
        assertTrue(unitOfWorkResult.getId(), unitOfWorkResult.getId().startsWith(SObjectType.Account.keyPrefix()));
        String accountId = unitOfWorkResult.getId();

        // Method
        assertEquals(Method.POST, unitOfWorkResult.getMethod());

        // Verify the name
        TestUtils.assertAccountName(config, unitOfWorkResult.getId(), accountName);

        unitOfWorkResults = unitOfWorkResponse.getResults(contact);
        assertNotNull(unitOfWorkResults);
        assertEquals(unitOfWorkResults.toString(), 1, unitOfWorkResults.size());

        unitOfWorkResult = unitOfWorkResults.get(0);
        assertNoErrors(unitOfWorkResult);

        // Id
        assertNotNull(unitOfWorkResult.getId());
        assertTrue(unitOfWorkResult.getId(), unitOfWorkResult.getId().startsWith(SObjectType.Contact.keyPrefix()));

        // Method
        assertEquals(Method.POST, unitOfWorkResult.getMethod());

        // Verify the name
        TestUtils.assertContact(config, unitOfWorkResult.getId(), accountId, contactLastName);
    }

    @Test
    public void testInsertAccountAndContact_SForceObjectsUsingRelationship() throws Exception {
        String accountName = "Account With Contact - " + System.currentTimeMillis();
        String contactLastName = "LastName - " + System.currentTimeMillis();

        Account account = new Account();
        account.setName(accountName);
        unitOfWork.registerNew(account);

        Contact contact = new Contact();
        contact.setLastName(contactLastName);
        contact.setAccount(account);
        unitOfWork.registerNew(contact);

        UnitOfWorkResponse unitOfWorkResponse = unitOfWork.commit();
        assertNotNull(unitOfWorkResponse);

        List<UnitOfWorkResult> unitOfWorkResults = unitOfWorkResponse.getResults(account);
        assertNotNull(unitOfWorkResults);
        assertEquals(unitOfWorkResults.toString(), 1, unitOfWorkResults.size());

        UnitOfWorkResult unitOfWorkResult = unitOfWorkResults.get(0);
        assertNoErrors(unitOfWorkResult);

        // Id
        assertNotNull(unitOfWorkResult.getId());
        assertTrue(unitOfWorkResult.getId(), unitOfWorkResult.getId().startsWith(SObjectType.Account.keyPrefix()));
        String accountId = unitOfWorkResult.getId();

        // Method
        assertEquals(Method.POST, unitOfWorkResult.getMethod());

        // Verify the name
        TestUtils.assertAccountName(config, unitOfWorkResult.getId(), accountName);

        unitOfWorkResults = unitOfWorkResponse.getResults(contact);
        assertNotNull(unitOfWorkResults);
        assertEquals(unitOfWorkResults.toString(), 1, unitOfWorkResults.size());

        unitOfWorkResult = unitOfWorkResults.get(0);
        assertNoErrors(unitOfWorkResult);

        // Id
        assertNotNull(unitOfWorkResult.getId());
        assertTrue(unitOfWorkResult.getId(), unitOfWorkResult.getId().startsWith(SObjectType.Contact.keyPrefix()));

        // Method
        assertEquals(Method.POST, unitOfWorkResult.getMethod());

        // Verify the name
        TestUtils.assertContact(config, unitOfWorkResult.getId(), accountId, contactLastName);
    }

    private void assertAttachment(String attachementId, String expectedParentId, Integer expectedLength) throws Exception {
        ForceAPI forceApi = new ForceAPIImpl(config);

        SOQLResult soqlResult = forceApi
                .query(String.format("SELECT Parent.Id, BodyLength FROM Attachment WHERE Id='%s'", attachementId));
        assertNotNull(soqlResult);
        assertNotNull(soqlResult.getRecords());
        assertEquals(1, soqlResult.getTotalSize());

        // Assert that the size is of an expected size
        Map<String, Object> record = soqlResult.getRecords().get(0);
        Integer bodyLength = (Integer)record.get("BodyLength");
        assertNotNull(bodyLength);
        assertEquals(bodyLength, expectedLength);

        // Assert that the Attachment is associated with the correct Parent
        @SuppressWarnings("unchecked")
        Map<String, Object> parent = (Map<String, Object>)record.get("Parent");
        assertNotNull(parent);
        assertEquals(expectedParentId, parent.get("Id"));
    }

    private void assertNoErrors(UnitOfWorkResult unitOfWorkResult) {
        assertNotNull(unitOfWorkResult);
        assertTrue(unitOfWorkResult.isSuccess());
        assertNotNull(unitOfWorkResult.getErrors());
        assertEquals(unitOfWorkResult.getErrors().toString(), 0, unitOfWorkResult.getErrors().size());
    }
}