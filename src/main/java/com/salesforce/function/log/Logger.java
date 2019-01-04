package com.salesforce.function.log;

/**
 * TODO
 *
 * FIXME: use java.util.logging.Logger
 */
public interface Logger {

    void trace(String msg);

    void debug(String msg);

    void debug(String msg, Exception ex);

    void info(String msg);

    void warn(String msg, Exception ex);

    void error(String msg);

    void error(String msg, Exception ex);
}