package com.unipay.models;

import com.unipay.command.UserSettingsCommand;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents user-specific preferences and configurations.
 * These settings allow for customization of user experience, such as language and timezone.
 *
 * <p>Key Features:
 * <ul>
 *   <li><strong>user</strong>: The user to whom these settings apply. This is a mandatory relationship with the {@link User} entity.</li>
 *   <li><strong>emailNotificationsEnabled</strong>: Indicates whether the user wants to receive email notifications.</li>
 *   <li><strong>preferredLanguage</strong>: The user's chosen language for UI or communication.</li>
 *   <li><strong>timezone</strong>: Timezone used for scheduling and displaying time-based data.</li>
 * </ul>
 *
 * <p>User settings allow users to personalize their account experience, including managing notifications and adjusting language or timezone preferences.
 */
@Entity
@Table(name = "user_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSettings extends BaseEntity {

    /**
     * The user to whom these settings apply.
     * This relationship ensures that the settings are tied to a specific user in the system.
     */
    @OneToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Indicates whether the user wants to receive email notifications.
     * This setting allows users to control their notification preferences.
     */
    private boolean emailNotificationsEnabled;

    /**
     * The user's preferred language for the user interface and communication.
     * This setting helps personalize the experience based on language preference.
     */
    private String preferredLanguage;

    /**
     * The timezone used for scheduling and displaying time-based data.
     * This helps ensure that time-sensitive data is correctly displayed according to the user's time zone.
     */
    private String timezone;

    /**
     * Creates a new instance of {@link UserSettings} from the provided {@link UserSettingsCommand}.
     *
     * @param command the user settings command containing the preferences to set
     * @return a new {@link UserSettings} object with values set from the command
     */
    public static UserSettings create(final UserSettingsCommand command){
        final UserSettings userSettings = new UserSettings();

        userSettings.preferredLanguage = command.getPreferredLanguage();
        userSettings.emailNotificationsEnabled = command.isEmailNotificationsEnabled();
        userSettings.timezone = command.getTimezone();

        return userSettings;
    }
}
