package com.unipay.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The `ExceptionResponse` class represents the structure of the error response
 * that will be returned to the client when an exception is thrown.
 * It contains the error code, message, and a reference for tracking the exception.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {

    private Integer code;
    private String message;
    private String reference;

    /**
     * Private constructor to create an `ExceptionResponse` from an `ExceptionPayload`.
     * @param exceptionPayload The payload containing error details.
     */
    private ExceptionResponse(ExceptionPayload exceptionPayload) {
        this.code = exceptionPayload.getCode();
        this.message = exceptionPayload.getMessage();
        this.reference = exceptionPayload.getReference();
    }

    /**
     * Static method to create an `ExceptionResponse` from an `ExceptionPayload`.
     * @param exceptionPayload The payload containing error details.
     * @return A new `ExceptionResponse` instance.
     */
    public static ExceptionResponse of(ExceptionPayload exceptionPayload) {
        return new ExceptionResponse(exceptionPayload);
    }
}
