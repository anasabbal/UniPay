package com.unipay.service.user;

import com.unipay.command.UserRegisterCommand;
import com.unipay.criteria.UserCriteria;
import com.unipay.enums.AuditLogAction;
import com.unipay.enums.RoleName;
import com.unipay.enums.UserStatus;
import com.unipay.exception.BusinessException;
import com.unipay.exception.ExceptionPayloadFactory;
import com.unipay.helper.UserRegistrationHelper;
import com.unipay.models.MFASettings;
import com.unipay.models.User;
import com.unipay.models.UserProfile;
import com.unipay.models.UserSettings;
import com.unipay.repository.UserRepository;
import com.unipay.service.mail.EmailService;
import com.unipay.service.role.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserRegistrationHelper registrationHelper;
    private final EmailService emailService;


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
        log.debug("Creating user: {}", command.getUsername());
        checkIfUserExists(command);

        User user = createUserEntity(command);
        initializeMfaSettings(user);
        roleService.assignRoleToUser(user, RoleName.USER);

        registrationHelper.associateUserProfileAndSettings(user, command);
        registrationHelper.logLoginHistory(user, request);
        registrationHelper.auditLogCreate(
                user,
                AuditLogAction.USER_CREATED.getAction(),
                "User created: " + command.getUsername()
        );
        emailService.sendConfirmationEmail(user);
        user.setStatus(UserStatus.PENDING);
        return user;
    }
    private void initializeMfaSettings(User user) {
        MFASettings mfaSettings = new MFASettings();
        mfaSettings.setEnabled(false);
        mfaSettings.setUser(user);
        user.setMfaSettings(mfaSettings);
    }
    private User createUserEntity(UserRegisterCommand command) {
        User user = User.create(command);
        user.setPasswordHash(passwordEncoder.encode(command.getPassword()));
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User findByEmailWithRolesAndPermissions(String email) {
        log.info("Begin fetching user with email {}", email);
        final User user =  userRepository.findByEmailWithRolesAndPermissions(email)
                .orElseThrow(() -> new BusinessException(ExceptionPayloadFactory.USER_NOT_FOUND.get()));
        log.info("User with email {} fetched successfully", user.getEmail());
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
     * Retrieves a user by their ID.
     *
     * @param userId The unique identifier of the user.
     * @return An {@link Optional} containing the user if found, or an empty {@link Optional} if no user is found.
     */
    @Override
    @Transactional(readOnly = true)
    public User getUserById(String userId) {
        log.debug("Begin fetching User with ID {}", userId);
        final User user = userRepository.findById(userId).orElseThrow(
                () -> new BusinessException(ExceptionPayloadFactory.USER_NOT_FOUND.get())
        );
        log.debug("User fetched successfully with ID {}", userId);
        return user;
    }
    /**
     * Retrieves a paginated list of {@link User} entities based on the specified filtering criteria.
     *
     * <p>This method delegates to the {@link UserRepository} to fetch users matching the provided
     * {@link UserCriteria} filters and paginates the result according to the given {@link Pageable} object.
     * In case of any exceptions during the fetching process, a {@link BusinessException} is thrown
     * with a technical error payload.</p>
     *
     * @param pageable the pagination information, including page number, size, and sorting
     * @param criteria the filtering criteria to apply when fetching users
     * @return a page of users matching the specified criteria
     * @throws BusinessException if an error occurs while fetching users from the repository
     */
    @Override
    @Transactional(readOnly = true)
    public Page<User> getAllByCriteria(Pageable pageable, UserCriteria criteria) {
        try {
            Page<User> users = userRepository.getUsersByCriteria(pageable, criteria);
            log.debug("User by criteria fetched successfully !!");
            return users;
        } catch (Exception e) {
            log.error("Error fetching users by criteria", e);
            throw new BusinessException(ExceptionPayloadFactory.TECHNICAL_ERROR.get(), e);
        }
    }
    @Override
    @Transactional(readOnly = true)
    public User getUserByIdWithRoles(String userId) {
        return userRepository.findByIdWithRoles(userId)
                .orElseThrow(() -> new BusinessException(ExceptionPayloadFactory.USER_NOT_FOUND.get()));
    }

}
