package com.salesforce.function;


/**
 * General exception class for Salesforce Functions.
 */
public class FunctionException extends Exception {

    public FunctionException(String msg) {
        super(msg);
    }

    public FunctionException(String msg, Throwable th) {
        super(msg, th);
    }
}