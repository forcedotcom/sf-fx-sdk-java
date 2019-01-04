package com.salesforce.function;

import com.salesforce.function.request.*;

/**
 * Interface for Salesforce Function implementations.
 */
public interface Function {

    /**
     * TODO
     *
     * @return
     */
    default String getName() {
        return getClass().getSimpleName();
    }

    /**
     * TODO
     *
     * @param request
     * @return
     * @throws FunctionException
     */
    Object invoke(Request request) throws FunctionException;
}