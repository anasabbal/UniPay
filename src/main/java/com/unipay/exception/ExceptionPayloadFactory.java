package com.unipay.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Enum to define different types of exception payloads that are used to generate
 * specific exception responses based on error types. Each enum constant represents
 * an exception case, providing a code, HTTP status, and a message key.
 *
 * It provides a method to retrieve an `ExceptionPayload` for each error type.
 */
@Getter
@AllArgsConstructor
public enum ExceptionPayloadFactory {

    TECHNICAL_ERROR(0, HttpStatus.INTERNAL_SERVER_ERROR, "technical.error"),

    INVALID_PAYLOAD(1, HttpStatus.BAD_REQUEST, "invalid.request.payload"),
    MISSING_REQUEST_BODY_ERROR_CODE(2, HttpStatus.BAD_REQUEST, "request.missing.body");

    private final Integer code;
    private final HttpStatus status;
    private final String message;

    /**
     * Creates an `ExceptionPayload` instance from the enum constants.
     * @return An `ExceptionPayload` instance containing code, status, and message.
     */
    public ExceptionPayload get() {
        return new ExceptionPayload(code, status, message);
    }
}
