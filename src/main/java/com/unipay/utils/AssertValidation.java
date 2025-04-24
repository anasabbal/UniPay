package com.unipay.utils;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for performing various validation assertions.
 */
public class AssertValidation {

    /**
     * Validates if the given string matches the provided regex pattern.
     * @param value The value to be validated.
     * @param pattern The regex pattern.
     * @param fieldName The name of the field to include in error messages.
     */
    public static void assertMatchesPattern(String value, String pattern, String fieldName) {
        if (value == null || !value.matches(pattern)) {
            throw new IllegalArgumentException(fieldName + " is invalid.");
        }
    }

    /**
     * Validates if the given date of birth is a valid date (and not in the future).
     * @param dateOfBirth The date of birth to be validated.
     */
    public static void assertValidDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth == null || dateOfBirth.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of Birth is invalid or in the future.");
        }
    }

    /**
     * Validates if the phone number is valid.
     * @param phoneNumber The phone number to be validated.
     */
    public static void assertValidPhoneNumber(String phoneNumber) {
        assertMatchesPattern(phoneNumber, RegexExpressions.PHONE, "Phone Number");
    }

    /**
     * Validates if the password is secure.
     * @param password The password to be validated.
     */
    public static void assertValidPassword(String password) {
        assertMatchesPattern(password, RegexExpressions.PASSWORD, "Password");
    }

    /**
     * Validates if the full name is valid.
     * @param fullName The full name to be validated.
     */
    public static void assertValidFullName(String fullName) {
        assertMatchesPattern(fullName, RegexExpressions.ALPHABETIC_MIN_2_CHARS, "Full Name");
    }

    /**
     * Validates if the email is valid.
     * @param email The email to be validated.
     */
    public static void assertValidEmail(String email) {
        assertMatchesPattern(email, RegexExpressions.EMAIL, "Email");
    }
    /**
     * Validates if the given string is non-null and has a minimum length.
     * @param value The value to be validated.
     * @param minLength The minimum length for the value.
     * @param fieldName The name of the field to include in error messages.
     */
    public static void assertNonEmptyString(String value, int minLength, String fieldName) {
        if (value == null || value.length() < minLength) {
            throw new IllegalArgumentException(fieldName + " should have at least " + minLength + " characters.");
        }
    }

    /**
     * Validates if the given timezone is valid.
     * @param timezone The timezone to be validated.
     */
    public static void assertValidTimezone(String timezone) {
        assertNonEmptyString(timezone, 1, "Timezone"); // You can also use specific regex for timezone if needed.
    }

    /**
     * Validates if the given language is valid.
     * @param language The language to be validated.
     */
    public static void assertValidLanguage(String language) {
        assertNonEmptyString(language, 2, "Preferred Language"); // Ensure it's not empty and at least 2 characters long.
    }
}

