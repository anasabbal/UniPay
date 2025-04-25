package com.unipay.service.user;

import com.unipay.command.UserRegisterCommand;
import com.unipay.models.User;
import com.unipay.models.UserProfile;
import com.unipay.models.UserSettings;
import com.unipay.repository.UserRepository;
import com.unipay.service.profile.UserProfileService;
import com.unipay.service.settings.UserSettingsService;
import com.unipay.utils.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@link UserService} interface that handles user registration logic.
 * This class coordinates the creation of a user and its related entities such as {@link UserProfile} and {@link UserSettings}.
 *
 * <p>Key Responsibilities:
 * <ul>
 *   <li>Validating the user registration command.</li>
 *   <li>Creating and saving the {@link User} entity.</li>
 *   <li>Creating associated {@link UserProfile} and {@link UserSettings} for the new user.</li>
 *   <li>Persisting the complete user structure within a transactional boundary.</li>
 * </ul>
 * </p>
 *
 * <p>Logging is used to trace the registration process and handle errors effectively.</p>
 *
 * @see com.unipay.command.UserRegisterCommand
 * @see com.unipay.models.User
 * @see com.unipay.models.UserProfile
 * @see com.unipay.models.UserSettings
 * @see com.unipay.service.user.UserService
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserProfileService userProfileService;
    private final UserSettingsService userSettingsService;

    /**
     * Creates a new user along with their profile and settings based on the provided command.
     * This method ensures that all related data is persisted atomically using a transactional context.
     *
     * @param command The {@link UserRegisterCommand} containing the user, profile, and settings information.
     * @return The fully initialized and saved {@link User} entity.
     */
    @Override
    @Transactional
    public User create(UserRegisterCommand command) {
        command.validate();
        log.debug("Start creating User with username {}", command.getUsername());

        final User user = User.create(command);
        User savedUser = userRepository.save(user);

        try {
            UserProfile userProfile = userProfileService.create(command.getProfile(), savedUser);
            UserSettings userSettings = userSettingsService.create(command.getSettings(), savedUser);

            savedUser.setProfile(userProfile);
            savedUser.setSettings(userSettings);

            savedUser = userRepository.save(savedUser);
            log.info("User created successfully with ID {}", savedUser.getId());
        } catch (Exception e) {
            log.error("Error during user creation", e);
            throw e;
        }

        return savedUser;
    }
}
