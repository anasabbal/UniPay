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
 * <p>Key Fields:
 * <ul>
 *   <li><strong>user</strong>: The user to whom these settings apply (one-to-one relationship).</li>
 *   <li><strong>emailNotificationsEnabled</strong>: Indicates whether the user wants to receive email notifications.</li>
 *   <li><strong>preferredLanguage</strong>: The user's chosen language for UI or communication.</li>
 *   <li><strong>timezone</strong>: Timezone used for scheduling and displaying time-based data.</li>
 * </ul>
 */

@Entity
@Table(name = "user_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSettings extends BaseEntity {


    @OneToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    private boolean emailNotificationsEnabled;
    private String preferredLanguage;
    private String timezone;

    public static UserSettings create(final UserSettingsCommand command){
        final UserSettings userSettings = new UserSettings();

        userSettings.preferredLanguage = command.getPreferredLanguage();
        userSettings.emailNotificationsEnabled = command.isEmailNotificationsEnabled();
        userSettings.timezone = command.getTimezone();

        return userSettings;
    }
}
