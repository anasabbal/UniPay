package com.unipay.service.profile;

import com.unipay.command.CreateAddressCommand;
import com.unipay.command.ProfileCommand;
import com.unipay.models.User;
import com.unipay.models.UserProfile;

/**
 * Service interface for managing user profile operations.
 *
 * <p>This interface defines the contract for creating and managing extended user profile information,
 * including details such as full name, date of birth, phone number, gender, nationality, and related
 * entities like addresses and payment methods.</p>
 *
 * <p>Responsibilities:
 * <ul>
 *   <li>Creating a new {@link UserProfile} instance associated with a specific {@link User}.</li>
 *   <li>Handling the transformation of input data from {@link ProfileCommand} into a {@link UserProfile} object.</li>
 *   <li>Enforcing business rules related to user profiles.</li>
 * </ul>
 * </p>
 *
 * @see com.unipay.models.UserProfile
 * @see com.unipay.command.ProfileCommand
 */
public interface UserProfileService {

    /**
     * Creates a new {@link UserProfile} using the provided {@link ProfileCommand} and associates it with the given {@link User}.
     *
     * @param profileCommand The profile data submitted by the user.
     * @param user The user entity to whom the profile belongs.
     * @return A newly created {@link UserProfile} instance associated with the user.
     */
    UserProfile create(final ProfileCommand profileCommand, User user);
    void addAddress(final CreateAddressCommand addressCommand);

}
