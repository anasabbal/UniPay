package com.unipay.command;

import com.unipay.utils.AssertValidation;
import lombok.Getter;

import java.time.LocalDate;

/**
 * Command class representing a user profile for registration or update.
 * This class contains fields related to the user's personal information and provides
 * validation methods to ensure the data is correct before being processed.
 *
 * The following fields are included:
 * - fullName: The user's full name.
 * - dateOfBirth: The user's date of birth.
 * - phoneNumber: The user's phone number.
 * - gender: The user's gender.
 * - nationality: The user's nationality.
 *
 * This class provides a `validate()` method to ensure that the data provided in these fields
 * adheres to the validation rules defined in the AssertValidation utility.
 *
 * Example usage:
 * <pre>
 * ProfileCommand profileCommand = new ProfileCommand();
 * profileCommand.setFullName("John Doe");
 * profileCommand.setDateOfBirth(LocalDate.of(1990, 5, 12));
 * profileCommand.setPhoneNumber("+351123456789");
 * profileCommand.setGender("MALE");
 * profileCommand.setNationality("Portuguese");
 * profileCommand.validate(); // Validates the profile data.
 * </pre>
 *
 * @see AssertValidation
 */
@Getter
public class ProfileCommand {

    /**
     * The full name of the user.
     * This field will be validated to ensure it contains at least 2 alphabetic characters and no invalid characters.
     */
    private String fullName;

    /**
     * The date of birth of the user.
     * This field will be validated to ensure that it is not in the future.
     */
    private LocalDate dateOfBirth;

    /**
     * The phone number of the user.
     * This field will be validated to ensure that it matches the required international format.
     */
    private String phoneNumber;

    /**
     * The gender of the user.
     * This field will be validated to ensure that it matches a predefined set of acceptable values.
     */
    private String gender;

    /**
     * The nationality of the user.
     * This field will be validated to ensure that it is not empty or null.
     */
    private String nationality;

    /**
     * Validates the user's profile data using the AssertValidation utility class.
     * This method checks if the provided full name, gender, date of birth, and phone number
     * adhere to the appropriate format and constraints.
     *
     * Throws an IllegalArgumentException if any validation fails.
     */
    public void validate() {
        AssertValidation.assertValidFullName(fullName);
        AssertValidation.assertValidGender(gender);
        AssertValidation.assertValidDateOfBirth(dateOfBirth);
        AssertValidation.assertValidPhoneNumber(phoneNumber);
    }
}
