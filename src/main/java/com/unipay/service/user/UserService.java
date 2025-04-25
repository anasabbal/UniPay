package com.unipay.service.user;

import com.unipay.command.UserRegisterCommand;
import com.unipay.models.User;

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
     * Creates a new user in the system based on the provided registration command.
     *
     * @param command The {@link UserRegisterCommand} containing validated registration details.
     * @return The newly created {@link User} entity.
     */
    User create(final UserRegisterCommand command);
}
