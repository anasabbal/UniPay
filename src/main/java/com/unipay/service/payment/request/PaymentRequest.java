package com.unipay.service.payment.request;

import java.math.BigDecimal;
import java.util.Map;

public record PaymentRequest(
        BigDecimal amount,
        String currency,
        String paymentMethod, // "CREDIT_CARD", "BANK_TRANSFER"
        String gateway,       // "STRIPE", "PAYPAL"

        // Payment method details (e.g., card number, bank account)
        Map<String, String> paymentDetails
) {}