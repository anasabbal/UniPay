package com.unipay.exception;

import lombok.Getter;

/**
 * BusinessException is a custom exception class used to handle business-related errors
 * in the application. It extends `RuntimeException` and carries an `ExceptionPayload` object
 * that contains detailed information about the exception, such as error codes, messages,
 * and additional arguments to provide context for the error.
 *
 * This class allows for enhanced exception handling, where the `ExceptionPayload` can
 * be used to store error-specific data that can later be accessed for logging or response
 * generation.
 *
 * It includes:
 * - `payload`: An instance of `ExceptionPayload` that holds the details of the error.
 *
 * Constructor Overloading:
 * - One constructor accepts an `ExceptionPayload` to initialize the payload.
 * - Another constructor accepts an `ExceptionPayload` and a variable number of arguments (`args`),
 *   allowing dynamic error message creation or parameter injection.
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * An instance of `ExceptionPayload` that holds the details of the exception,
     * such as error codes and arguments.
     */
    private final ExceptionPayload payload;

    /**
     * Constructor for creating a BusinessException with an ExceptionPayload.
     * @param payload The payload containing details about the exception.
     */
    public BusinessException(ExceptionPayload payload) {
        this.payload = payload;
    }

    /**
     * Constructor for creating a BusinessException with an ExceptionPayload and additional arguments.
     * This can be used to dynamically populate the payload's details (e.g., error message, arguments).
     * @param payload The payload containing details about the exception.
     * @param args Additional arguments that can be used in error messages or logging.
     */
    public BusinessException(ExceptionPayload payload, Object... args) {
        payload.setArgs(args);  // Sets the arguments in the payload.
        this.payload = payload;
    }
}
