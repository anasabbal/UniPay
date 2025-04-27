package com.unipay.service.user;

import com.unipay.command.UserRegisterCommand;
import com.unipay.enums.AuditLogAction;
import com.unipay.enums.RoleName;
import com.unipay.exception.BusinessException;
import com.unipay.exception.ExceptionPayloadFactory;
import com.unipay.models.User;
import com.unipay.models.UserProfile;
import com.unipay.models.UserSettings;
import com.unipay.repository.UserRepository;
import com.unipay.service.audit_log.AuditLogService;
import com.unipay.service.login_histroy.LoginHistoryService;
import com.unipay.service.profile.UserProfileService;
import com.unipay.service.role.RoleService;
import com.unipay.service.settings.UserSettingsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    private final LoginHistoryService loginHistoryService;
    private final AuditLogService auditLogService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;


    /**
     * Creates a new user along with their profile and settings based on the provided command.
     * This method ensures that all related data is persisted atomically using a transactional context.
     *
     * @param command The {@link UserRegisterCommand} containing the user, profile, and settings information.
     * @param request The HTTP request containing the client’s IP address and user agent.
     * @return The fully initialized and saved {@link User} entity.
     */
    @Override
    @Transactional
    public User create(UserRegisterCommand command, HttpServletRequest request) {
        log.debug("Start creating User with username {}", command.getUsername());
        checkIfUserExists(command);
        User user = createUser(command);
        roleService.assignRoleToUser(user, RoleName.USER);
        associateUserProfileAndSettings(user, command);
        logLoginHistory(user, request);
        auditLogCreate(
                user,
                AuditLogAction.USER_CREATED.getAction(),
                "User with username " + command.getUsername() + " has been created."
        );
        user.setPasswordHash(passwordEncoder.encode(command.getPassword()));
        userRepository.save(user);
        return user;
    }

    /**
     * Check if a user already exists by email or username.
     *
     * @param command The registration command containing the user's email and username.
     * @throws IllegalArgumentException if the user exists.
     */
    private void checkIfUserExists(UserRegisterCommand command) {
        if (userRepository.existsByEmailOrUsername(command.getEmail(), command.getUsername())) {
            log.warn("User with email {} or username {} already exists", command.getEmail(), command.getUsername());
            throw new BusinessException(ExceptionPayloadFactory.USER_ALREADY_EXIST.get());
        }
    }
    /**
     * Creates a new User entity from the provided registration command.
     *
     * @param command The registration command containing user data.
     * @return The newly created User entity.
     */
    private User createUser(UserRegisterCommand command) {
        User user = User.create(command);
        log.debug("User created with username {}", command.getUsername());
        return userRepository.save(user);
    }

    /**
     * Associates a UserProfile and UserSettings to the newly created user.
     *
     * @param user The user to associate the profile and settings with.
     * @param command The registration command containing user profile and settings data.
     */
    private void associateUserProfileAndSettings(User user, UserRegisterCommand command) {
        try {
            UserProfile userProfile = userProfileService.create(command.getProfile(), user);
            UserSettings userSettings = userSettingsService.create(command.getSettings(), user);

            user.setProfile(userProfile);
            user.setSettings(userSettings);
        } catch (Exception e) {
            log.error("Error creating and associating user profile/settings", e);
            throw new BusinessException(ExceptionPayloadFactory.TECHNICAL_ERROR.get(), e);
        }
    }
    private void auditLogCreate(User user, String action, String details){
        try {
            auditLogService.createAuditLog(user, action, details);
        } catch (Exception e) {
            log.error("Error creating audit log for user", e);
            throw new BusinessException(ExceptionPayloadFactory.TECHNICAL_ERROR.get(), e);
        }
    }

    /**
     * Logs the login history for the user and associates it with their account.
     *
     * @param user The user to log the login history for.
     * @param request The HTTP request containing the client’s IP address and user agent.
     */
    private void logLoginHistory(User user, HttpServletRequest request) {
        try {
            loginHistoryService.createLoginHistory(user, request, true);
        } catch (Exception e) {
            log.error("Error creating login history for user", e);
            throw new BusinessException(ExceptionPayloadFactory.TECHNICAL_ERROR.get(), e);
        }
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId The unique identifier of the user.
     * @return An {@link Optional} containing the user if found, or an empty {@link Optional} if no user is found.
     */
    @Override
    public Optional<User> getUserById(String userId) {
        log.debug("Begin fetching User with ID {}", userId);
        final User user = userRepository.findById(userId).orElseThrow(
                () -> new BusinessException(ExceptionPayloadFactory.USER_NOT_FOUND.get())
        );
        log.debug("User fetched successfully with ID {}", userId);
        return userRepository.findById(userId);
    }
}
