package com.unipay.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Represents a webhook configuration for the user to receive notifications about specific events.
 * This DTO holds the webhook URL, a secret key for authentication, and a list of events that will trigger the webhook.
 */
@Data
@Getter
@Setter
public class WebhookDto {
    /**
     * URL to which the webhook notifications will be sent.
     */
    private String url;

    /**
     * Secret key used for validating webhook requests and ensuring security.
     */
    private String secret;

    /**
     * List of events that will trigger the webhook. Each event corresponds to a system action (e.g., transaction success).
     */
    private List<String> events;
}
