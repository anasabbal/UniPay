package com.unipay.dto;

import lombok.Data;

/**
 * Represents a financial transaction made by or on behalf of the user.
 */
@Data
public class TransactionDto {
    /**
     * Unique identifier of the transaction.
     */
    private String transactionId;

    /**
     * Amount involved in the transaction.
     */
    private double amount;

    /**
     * Currency of the transaction (e.g., USD, EUR).
     */
    private String currency;

    /**
     * Current status of the transaction (e.g., SUCCESS, FAILED, PENDING).
     */
    private String status;

    /**
     * Method used for the transaction (e.g., CARD, BANK_TRANSFER).
     */
    private String paymentMethod;
}
