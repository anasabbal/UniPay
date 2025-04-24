package com.unipay.command;

import com.unipay.utils.AssertValidation;
import lombok.Getter;

/**
 * Command class for user settings. This class contains user preferences related to notifications,
 * language, and timezone.
 *
 * The class includes a `validate()` method to ensure that the provided values for preferred language
 * and timezone are valid according to the validation rules defined in the AssertValidation utility.
 *
 * The following fields are included:
 * - emailNotificationsEnabled: A boolean flag indicating whether email notifications are enabled for the user.
 * - preferredLanguage: The preferred language of the user (will be validated for a valid language format).
 * - timezone: The preferred timezone of the user (will be validated for a valid timezone format).
 *
 * Example usage:
 * <pre>
 * UserSettingsCommand userSettingsCommand = new UserSettingsCommand();
 * userSettingsCommand.setPreferredLanguage("en");
 * userSettingsCommand.setTimezone("Europe/Lisbon");
 * userSettingsCommand.setEmailNotificationsEnabled(true);
 * userSettingsCommand.validate(); // Validates the user settings data.
 * </pre>
 *
 * @see AssertValidation
 */
@Getter
public class UserSettingsCommand {

    /**
     * Flag indicating whether email notifications are enabled for the user.
     */
    private boolean emailNotificationsEnabled;

    /**
     * The user's preferred language.
     * This field will be validated to ensure it is a valid language code (e.g., "en" for English).
     */
    private String preferredLanguage;

    /**
     * The user's preferred timezone.
     * This field will be validated to ensure it is a valid timezone (e.g., "Europe/Lisbon").
     */
    private String timezone;

    /**
     * Validates the user settings data using the AssertValidation utility class.
     * This method checks if the preferred language and timezone are valid according to the validation rules.
     *
     * Throws an IllegalArgumentException if any validation fails.
     */
    public void validate() {
        AssertValidation.assertValidLanguage(preferredLanguage);
        AssertValidation.assertValidTimezone(timezone);
    }
}
