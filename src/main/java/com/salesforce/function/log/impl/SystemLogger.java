package com.salesforce.function.log.impl;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.salesforce.function.log.Logger;

/**
 * System logger for Salesforce Functions.
 *
 */
public class SystemLogger implements Logger {

    public SystemLogger() {
    }

    @Override
    public void trace(String msg) {
        System.out.println("TRACE: " + msg);
    }

    @Override
    public void debug(String msg) {
        System.out.println("DEBUG: " + msg);
    }

    @Override
    public void debug(String msg, Exception ex) {
        System.out.println("DEBUG: " + msg + "\n" + getStacktrace(ex));
    }

    @Override
    public void info(String msg) {
        System.out.println("INFO: " + msg);
    }

    @Override
    public void error(String msg) {
        System.err.println("ERROR: " + msg);
    }

    @Override
    public void warn(String msg, Exception ex) {
        System.err.println("WARN: " + msg + "\n" + getStacktrace(ex));
    }

    @Override
    public void error(String msg, Exception ex) {
        System.err.println("ERROR: " + msg + "\n" + getStacktrace(ex));
    }

    private String getStacktrace(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }
}