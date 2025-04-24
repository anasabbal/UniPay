package com.unipay.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * UserSettingsDto is a Data Transfer Object (DTO) used for transferring user settings-related data
 * between different layers of the application (e.g., from controller to service or between services).
 * This class encapsulates the necessary information related to a user's settings, such as email preferences,
 * language, and timezone.
 *
 * It includes:
 * - `emailNotificationsEnabled`: A flag to indicate whether email notifications are enabled for the user.
 * - `preferredLanguage`: The language preferred by the user (e.g., "en" for English).
 * - `timezone`: The timezone setting for the user (e.g., "Europe/Lisbon").
 *
 * This class uses Lombok annotations to automatically generate getters, setters, and other boilerplate code.
 */
@Data
@Getter
@Setter
public class UserSettingsDto {

    /**
     * A boolean flag indicating whether the user has enabled email notifications.
     * If true, email notifications are enabled for the user.
     * If false, email notifications are disabled.
     */
    private boolean emailNotificationsEnabled;

    /**
     * The preferred language for the user. This can be a language code such as "en" for English,
     * "pt" for Portuguese, etc.
     */
    private String preferredLanguage;

    /**
     * The timezone of the user, represented as a string (e.g., "Europe/Lisbon").
     * This is used to display times correctly according to the user's local time.
     */
    private String timezone;
}
