package com.unipay.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * UserDto is a Data Transfer Object (DTO) used for transferring user-related data
 * between different layers of the application (e.g., from controller to service or between services).
 * This class encapsulates the necessary information to represent a user in the system.
 *
 * It includes:
 * - `username`: The unique identifier for the user.
 * - `email`: The email address associated with the user.
 * - `passwordHash`: The hashed password for secure authentication.
 * - `profile`: A reference to the user's profile data (e.g., full name, date of birth).
 * - `settings`: A reference to the user's settings (e.g., language, timezone, email notifications).
 *
 * This class uses Lombok annotations to automatically generate getters, setters, and other boilerplate code.
 *
 * @see UserProfileDto
 * @see UserSettingsDto
 */
@Data
@Getter
@Setter
public class UserDto {

    /**
     * The username of the user. It must be unique and can be used for logging in.
     */
    private String username;

    /**
     * The email address of the user. It must be valid and unique.
     */
    private String email;

    /**
     * The hashed password of the user. This field stores the encrypted password
     * for authentication purposes.
     */
    private String passwordHash;

    /**
     * The user's profile information. This includes details like the user's full name,
     * date of birth, and other personal information.
     */
    private UserProfileDto profile;

    /**
     * The user's settings, such as their preferred language, timezone, and notification preferences.
     */
    private UserSettingsDto settings;
}
