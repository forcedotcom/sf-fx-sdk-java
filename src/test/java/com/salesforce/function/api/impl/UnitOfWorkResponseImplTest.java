package com.salesforce.function.api.impl;

import java.util.*;

import org.junit.*;
import org.junit.rules.ExpectedException;

import com.salesforce.function.api.composite.response.impl.CompositeResponseImpl;
import com.sforce.soap.enterprise.sobject.Account;

/**
 * Unit tests for {@link UnitOfWorkResponseImpl}
 *
 * @since 218
 */
public class UnitOfWorkResponseImplTest extends Assert {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Verify that {@link UnitOfWorkResponseImpl#getResults(com.salesforce.function.api.model.SObject)} throws a
     * NoSuchElementException instead of a NullPointerException.
     */
    @Test
    public void testGetResultsThrowsNoSuchElementExceptionForEmptyReferenceIdList() throws Exception {
        Account account = new Account();
        Map<String, List<String>> uuidToReferenceIds = new HashMap<>();

        UnitOfWorkResponseImpl unitOfWorkResponseImpl = new UnitOfWorkResponseImpl(Collections.emptyMap(),
                uuidToReferenceIds, new CompositeResponseImpl());

        thrown.expect(NoSuchElementException.class);

        unitOfWorkResponseImpl.getResults(account);
    }

    /**
     * Verify that {@link UnitOfWorkResponseImpl#getResults(com.salesforce.function.api.model.SObject)} throws a
     * NoSuchElementException instead of a NullPointerException.
     */
    @Test
    public void testGetResultsThrowsNoSuchElementExceptionForNullReferenceIdList() throws Exception {
        Account account = new Account();
        Map<String, List<String>> uuidToReferenceIds = new HashMap<>();
        uuidToReferenceIds.put(account.getUUID(), Collections.emptyList());

        UnitOfWorkResponseImpl unitOfWorkResponseImpl = new UnitOfWorkResponseImpl(Collections.emptyMap(),
                uuidToReferenceIds, new CompositeResponseImpl());

        thrown.expect(NoSuchElementException.class);

        unitOfWorkResponseImpl.getResults(account);
    }
}
