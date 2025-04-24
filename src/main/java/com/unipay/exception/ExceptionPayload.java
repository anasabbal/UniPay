package com.unipay.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * ExceptionPayload is a class used to represent the details of an exception in the application.
 * It is typically used to carry error-related information such as error code, status, message,
 * and additional arguments that can be useful for logging, user feedback, or debugging.
 *
 * The class is designed to be serializable, making it suitable for transferring exception details
 * between different layers of the application or even across systems in case of distributed architectures.
 *
 * It uses the following annotations for better code management:
 * - `@Data`: Automatically generates getter, setter, equals, hashCode, and toString methods.
 * - `@Builder`: Provides a builder pattern for easy creation of instances.
 * - `@NoArgsConstructor`: Generates a no-argument constructor.
 * - `@AllArgsConstructor`: Generates a constructor with all fields as parameters.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionPayload implements Serializable {

    /**
     * Error code representing the specific exception type.
     */
    private Integer code;

    /**
     * HTTP status representing the response status code for the exception.
     */
    private HttpStatus status;

    /**
     * Detailed message describing the exception.
     */
    private String message;

    /**
     * An array of additional arguments that provide context or specific data related to the exception.
     */
    private Object[] args;

    /**
     * A unique reference string that can be used to track the specific instance of the exception.
     */
    private String reference;

    /**
     * Constructor to create an ExceptionPayload with code, status, and message.
     * @param code The error code for the exception.
     * @param status The HTTP status related to the exception.
     * @param message The detailed message describing the exception.
     */
    public ExceptionPayload(Integer code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
