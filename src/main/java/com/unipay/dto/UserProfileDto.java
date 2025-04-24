package com.unipay.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * UserProfileDto is a Data Transfer Object (DTO) used for transferring user profile-related data
 * between different layers of the application (e.g., from controller to service or between services).
 * This class encapsulates the necessary information that represents a user's profile.
 *
 * It includes:
 * - `fullName`: The full name of the user.
 * - `dateOfBirth`: The date of birth of the user.
 * - `phoneNumber`: The phone number associated with the user.
 * - `gender`: The gender of the user.
 * - `nationality`: The nationality of the user.
 *
 * This class uses Lombok annotations to automatically generate getters, setters, and other boilerplate code.
 */
@Data
@Getter
@Setter
public class UserProfileDto {

    /**
     * The full name of the user. This includes both first and last names.
     */
    private String fullName;

    /**
     * The date of birth of the user. Stored as a LocalDate object.
     */
    private LocalDate dateOfBirth;

    /**
     * The phone number of the user. Stored as a string to accommodate various formats,
     * including international numbers.
     */
    private String phoneNumber;

    /**
     * The gender of the user. Typically, it can be values like "MALE", "FEMALE", or "OTHER".
     */
    private String gender;

    /**
     * The nationality of the user. This can be a country name or any other form of identification
     * of the user's national origin.
     */
    private String nationality;
}
