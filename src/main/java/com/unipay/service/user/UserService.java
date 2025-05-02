package com.unipay.service.user;

import com.unipay.command.UserRegisterCommand;
import com.unipay.criteria.UserCriteria;
import com.unipay.models.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing core user operations within the system.
 *
 * <p>This interface defines methods related to the lifecycle of {@link User} entities,
 * primarily focused on user registration and creation workflows.</p>
 *
 * <p>Responsibilities:
 * <ul>
 *   <li>Creating new users based on validated registration commands.</li>
 *   <li>Delegating the actual persistence and additional user-related logic to implementation classes.</li>
 * </ul>
 * </p>
 *
 * <p>Typical use cases include:
 * <ul>
 *   <li>Handling new user registration.</li>
 *   <li>Initiating user-related entities (e.g., profile, settings, roles) during signup.</li>
 * </ul>
 * </p>
 *
 * @see com.unipay.command.UserRegisterCommand
 * @see com.unipay.models.User
 */
public interface UserService {

    /**
     * Registers a new user in the system based on the provided registration command and request details.
     * <p>
     * This method creates a new user, along with their associated profile and settings, and logs the user's
     * login attempt. The process ensures that all related data is persisted within a transactional context.
     * </p>
     *
     * @param command The {@link UserRegisterCommand} containing validated registration information, such as
     *                the user's credentials, profile, and settings.
     * @param request The {@link HttpServletRequest} used to capture the user's IP address and user agent
     *                during the registration process for tracking purposes.
     * @return The newly created and fully initialized {@link User} entity, including the user's profile and settings.
     */
    User create(final UserRegisterCommand command, HttpServletRequest request);
    User getUserById(String userId);
    Page<User> getAllByCriteria(Pageable pageable, UserCriteria criteria);
    User findByEmailWithRolesAndPermissions(String email);
}
