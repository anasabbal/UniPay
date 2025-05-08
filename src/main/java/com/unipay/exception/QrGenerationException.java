package com.unipay.exception;

public class QrGenerationException extends Exception {
    public QrGenerationException(String message) {
        super(message);
    }

    public QrGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
