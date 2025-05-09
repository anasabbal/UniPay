package com.unipay.service.payment.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.unipay.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(
        String transactionId,
        TransactionStatus status,
        String gatewayTransactionId,
        BigDecimal amount,
        String currency,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        LocalDateTime timestamp,

        String message
) {
    public static PaymentResponse success(String transactionId, TransactionStatus status,
                                          String gatewayId, BigDecimal amount, String currency,
                                          LocalDateTime timestamp) {
        return new PaymentResponse(
                transactionId,
                status,
                gatewayId,
                amount,
                currency,
                timestamp,
                "Payment processed successfully"
        );
    }

    // Failure factory method
    public static PaymentResponse failure(String transactionId, TransactionStatus status,
                                          String errorMessage, LocalDateTime timestamp) {
        return new PaymentResponse(
                transactionId,
                status,
                null,
                null,
                null,
                timestamp,
                "Payment failed: " + errorMessage
        );
    }
}
