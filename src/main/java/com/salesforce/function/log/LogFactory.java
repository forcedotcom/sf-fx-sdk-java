package com.salesforce.function.log;

import com.salesforce.function.log.impl.SystemLogger;

/**
 * TODO
 *
 * FIXME: use java.util.logging.Logger
 */
public class LogFactory {

    private static LogFactory instance = null;
    private static final Logger DEFAULT = new SystemLogger();

    private LogFactory() {}

    public static LogFactory getInstance() {
        if (instance == null) {
            instance = new LogFactory();
        }

        return instance;
    }

    public Logger getDefault() {
        return DEFAULT;
    }

    public Logger getLogger(Class<?> clazz) {
        return DEFAULT;
    }
}