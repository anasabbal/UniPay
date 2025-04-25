package com.unipay.service.settings;

import com.unipay.command.UserSettingsCommand;
import com.unipay.models.User;
import com.unipay.models.UserSettings;

/**
 * Service interface for managing user-specific application settings.
 *
 * <p>This service provides an abstraction for handling settings preferences tied to a specific {@link User}.
 * Implementations of this interface are responsible for converting the {@link UserSettingsCommand} into a
 * persistent {@link UserSettings} entity and associating it with the correct user.</p>
 *
 * <p>Key Use Cases:
 * <ul>
 *   <li>Creating initial user settings during account setup or onboarding.</li>
 *   <li>Managing default preferences such as language, notification settings, themes, etc.</li>
 *   <li>Ensuring consistent linkage between {@link UserSettings} and its parent {@link User}.</li>
 * </ul>
 * </p>
 *
 * @see com.unipay.models.UserSettings
 * @see com.unipay.models.User
 * @see com.unipay.command.UserSettingsCommand
 */
public interface UserSettingsService {

    /**
     * Creates and persists user settings based on the provided command and user.
     *
     * @param command The {@link UserSettingsCommand} containing user preference details.
     * @param user The {@link User} for whom the settings are being created.
     * @return The created and persisted {@link UserSettings} entity.
     */
    UserSettings create(final UserSettingsCommand command, User user);
}
