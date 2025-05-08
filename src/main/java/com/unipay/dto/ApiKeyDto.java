package com.unipay.dto;

import lombok.Data;

import java.time.Instant;
import java.util.List;

/**
 * Represents an API key entity used by developers for authentication and integration.
 */
@Data
public class ApiKeyDto {
    /**
     * Unique identifier for the API key.
     */
    private String keyId;

    /**
     * Human-readable name for the API key, useful for display and management.
     */
    private String name;

    /**
     * List of permissions granted to this API key (e.g., READ_TRANSACTIONS, MANAGE_WEBHOOKS).
     */
    private List<String> permissions;

    /**
     * Expiration timestamp for the API key. After this, the key becomes invalid.
     */
    private Instant expiresAt;
}
