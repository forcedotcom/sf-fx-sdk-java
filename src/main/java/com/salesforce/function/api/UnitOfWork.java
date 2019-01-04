package com.salesforce.function.api;

import com.salesforce.function.FunctionException;
import com.salesforce.function.api.model.SObject;

/**
 * A Salesforce specific implementation of <a href="https://martinfowler.com/eaaCatalog/unitOfWork.html">Martin
 * Fowler's</a> Unit of Work Pattern. Multiple DML operations can be added to the unit of work and committed in a
 * single transaction. If any fail, then the whole unit of work is rolled back.
 */
public interface UnitOfWork {

    /**
     * Register create operation.
     */
    void registerNew(SObject sobject);

    /**
     * Register update operation.
     * 
     * @throws FunctionException
     */
    void registerModified(SObject sobject) throws FunctionException;

    /**
     * Register delete operation.
     * 
     * @throws FunctionException
     */
    void registerDeleted(SObject sobject) throws FunctionException;

    /***
     * All operations batched together executed in single transaction.
     * 
     * @return UnitOfWorkResponse that represents the results for the entire unit of work.
     * @throws FunctionException
     */
    UnitOfWorkResponse commit() throws FunctionException;

}