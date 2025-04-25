package com.unipay.service.settings;

import com.unipay.command.UserSettingsCommand;
import com.unipay.models.User;
import com.unipay.models.UserSettings;
import com.unipay.repository.UserSettingsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link UserSettingsService} interface responsible for managing
 * the creation of user-specific application settings.
 *
 * <p>This class handles the lifecycle of {@link UserSettings} entities by taking a validated
 * {@link UserSettingsCommand} and persisting the resulting settings linked to a specific {@link User}.</p>
 *
 * <p>Responsibilities:
 * <ul>
 *   <li>Validating user settings input data.</li>
 *   <li>Transforming the command object into a {@link UserSettings} entity.</li>
 *   <li>Persisting the entity and logging the operation flow.</li>
 *   <li>Handling and logging errors during the creation process.</li>
 * </ul>
 * </p>
 *
 * <p>This service is typically invoked during user onboarding or when updating application preferences.</p>
 *
 * @see com.unipay.service.settings.UserSettingsService
 * @see com.unipay.models.UserSettings
 * @see com.unipay.command.UserSettingsCommand
 * @see com.unipay.repository.UserSettingsRepository
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserSettingsServiceImpl implements UserSettingsService {

    /**
     * Repository for persisting and retrieving {@link UserSettings} entities.
     */
    private final UserSettingsRepository userSettingsRepository;

    /**
     * Creates and saves user settings based on the provided command and associated user.
     *
     * @param command The {@link UserSettingsCommand} containing user preference configurations.
     * @param user The {@link User} to whom these settings will be linked.
     * @return The newly created and saved {@link UserSettings} entity.
     */
    @Override
    public UserSettings create(UserSettingsCommand command, User user) {
        command.validate();
        log.debug("Start creating User settings for user {}", user.getId());

        UserSettings userSettings = UserSettings.create(command);
        userSettings.setUser(user);

        try {
            userSettings = userSettingsRepository.save(userSettings);
            log.info("User settings created successfully with ID {}", userSettings.getId());
        } catch (Exception e) {
            log.error("Error during user settings creation", e);
            throw e;
        }

        return userSettings;
    }
}
