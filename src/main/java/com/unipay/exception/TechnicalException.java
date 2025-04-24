package com.unipay.exception;

/**
 * Custom exception to represent technical errors that occur during execution.
 * Typically used to handle unforeseen or system-level issues.
 */
public class TechnicalException extends RuntimeException {

    /**
     * Constructor to create a new `TechnicalException` with the given message.
     * @param s The error message.
     */
    public TechnicalException(String s) {
        super(s);
    }
}
