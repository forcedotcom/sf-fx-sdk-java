package com.salesforce.function.api.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.salesforce.function.api.composite.request.CompositeRequest;
import com.salesforce.function.api.composite.request.CompositeSubrequest;
import com.salesforce.function.api.composite.request.impl.CompositeRequestImpl;
import com.salesforce.function.api.composite.request.impl.CompositeSubrequestImpl;
import com.salesforce.function.api.model.SObjectType;
import com.salesforce.function.util.Constants;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.enterprise.sobject.Contact;
import com.sforce.soap.enterprise.sobject.GolfCourseC;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test various aspects of Jackson serialization/deserialization.
 *
 * @since 218
 */
public class SerializationTest extends Assert {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCompositeRequestNoSubRequests() throws Exception {
        CompositeRequest compositeRequest = new CompositeRequestImpl();

        ObjectMapper mapper = new ObjectMapper();
        String jsonValue = mapper.writeValueAsString(compositeRequest);
        assertNotNull(jsonValue);

        CompositeRequest deserializedRequest = mapper.readValue(jsonValue, CompositeRequest.class);
        assertNotNull(deserializedRequest);
        assertFalse(deserializedRequest.isAllOrNone());
        assertEquals(0, deserializedRequest.getSubRequests().size());
    }

    @Test
    public void testCompositeSubequestArray() throws Exception {
        CompositeSubrequest originalCompositeSubrequest = CompositeSubrequestImpl.insertBuilder()
                .sobjecType(SObjectType.Account).name("My Account").build();
        assertEquals("/services/data/v" + Constants.CURRENT_API_VERSION + "/sobjects/Account",
                originalCompositeSubrequest.getUrl());

        List<CompositeSubrequest> originalCompositeSubrequests = Collections.singletonList(originalCompositeSubrequest);

        ObjectMapper mapper = new ObjectMapper();
        String jsonValue = mapper.writeValueAsString(originalCompositeSubrequests);
        assertNotNull(jsonValue);

        List<CompositeSubrequest> deserializedCompositeSubrequests = mapper.readValue(jsonValue,
                TypeFactory.defaultInstance().constructCollectionType(List.class, CompositeSubrequest.class));
        assertNotNull(deserializedCompositeSubrequests);
        assertEquals(1, deserializedCompositeSubrequests.size());
        CompositeSubrequest deserializedCompositeSubrequest = deserializedCompositeSubrequests.get(0);
        assertEquals(originalCompositeSubrequest.getReferenceId(), deserializedCompositeSubrequest.getReferenceId());
        assertEquals(originalCompositeSubrequest.getBody(), deserializedCompositeSubrequest.getBody());
        assertEquals(originalCompositeSubrequest.getHttpHeaders(), deserializedCompositeSubrequest.getHttpHeaders());
        assertEquals(originalCompositeSubrequest.getMethod(), deserializedCompositeSubrequest.getMethod());
    }

    /**
     * Verify that {@link Contact#setAccount(Account)} is serialized as the AccountId string instead of the object graph
     */
    @Test
    public void testAccountObjectIsConvertedToAccountId() throws Exception {
        String accountName = "Account With Contact - " + System.currentTimeMillis();
        String contactLastName = "LastName - " + System.currentTimeMillis();

        Account account = new Account();
        account.setName(accountName);
        String accountFkId = account.getFkId();

        Contact contact = new Contact();
        contact.setLastName(contactLastName);
        contact.setAccount(account);

        ObjectMapper mapper = new ObjectMapper();
        String jsonValue = mapper.writeValueAsString(contact);
        assertNotNull(jsonValue);

        Contact deserializedContact = mapper.readValue(jsonValue, Contact.class);
        assertEquals(accountFkId, deserializedContact.getValues().get("AccountId"));
    }

    /**
     * It's not legal to set a relationship using both an Id and Complex object. The user should use one or the other.
     */
    @Test
    public void testUsingBothObjectAndIdSetterThrows() {
        String accountName = "Account With Contact - " + System.currentTimeMillis();
        String contactLastName = "LastName - " + System.currentTimeMillis();

        Account account = new Account();
        account.setName(accountName);

        Contact contact = new Contact();
        contact.setLastName(contactLastName);
        contact.setAccount(account);
        contact.setAccountId(account.getFkId());

        thrown.expect(RuntimeException.class);
        thrown.expectMessage("You can't set a relationship using the both 'Contact#setAccount' and 'Contact#setAccountId'. Call one of these methods to set the value back to null.");

        contact.getValues();
    }

    /**
     * Validate Custom Object and Custom Field support.
     */
    @Test
    public void testCustomObjectField() {
        String golfCourseId = "999B0000004ifUOIAY";
        String golfCourseName = "GolfCourse - " + System.currentTimeMillis();
        GolfCourseC golfCourse = new GolfCourseC();
        golfCourse.setId(golfCourseId);
        golfCourse.setName(golfCourseName);
        Map<String, Object> values = golfCourse.getValues();
        assertTrue(values != null && !values.isEmpty());
        assertTrue(golfCourseId.equals(values.get("Id")));
        assertTrue(golfCourseName.equals(values.get("Name")));

        String accountName = "Account With Custom Field - " + System.currentTimeMillis();
        Account account = new Account();
        account.setName(accountName);
        account.setGolfCourseR(golfCourse);
        values = account.getValues();
        assertTrue(values != null && !values.isEmpty());
        assertTrue(golfCourseId.equals(values.get("GolfCourse__c")));
    }
}