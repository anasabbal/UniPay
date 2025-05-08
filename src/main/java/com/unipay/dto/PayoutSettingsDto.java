package com.unipay.dto;

import lombok.Data;

/**
 * Stores payout configuration for users or businesses.
 */
@Data
public class PayoutSettingsDto {
    /**
     * Bank account identifier (encrypted) where payouts are sent.
     */
    private String bankAccount;

    /**
     * Preferred payout currency (e.g., USD, EUR).
     */
    private String currency;

    /**
     * Scheduled frequency of payouts (e.g., DAILY, WEEKLY).
     */
    private String schedule;
}
