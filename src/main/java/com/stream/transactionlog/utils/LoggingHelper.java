package com.stream.transactionlog.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingHelper {

    /**
     * Create a logger for a specific class.
     *
     * @param clazz The class for which the logger is created.
     * @return Logger instance for the class.
     */
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    /**
     * Log an info message.
     *
     * @param logger The logger instance.
     * @param message The message to log.
     */
    public static void info(Logger logger, String message) {
        logger.info(message);
    }

    /**
     * Log a debug message.
     *
     * @param logger The logger instance.
     * @param message The message to log.
     */
    public static void debug(Logger logger, String message) {
        logger.debug(message);
    }

    /**
     * Log an error message with exception.
     *
     * @param logger The logger instance.
     * @param message The message to log.
     * @param exception The exception to include in the log.
     */
    public static void error(Logger logger, String message, Throwable exception) {
        logger.error(message, exception);
    }
}
