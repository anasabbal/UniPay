package com.unipay.utils;

import java.util.List;

/**
 * Utility class containing common regex expressions used for validation.
 */
public class RegexExpressions {

    // Username: allows alphanumeric and underscore, 3–20 characters
    public static final String USERNAME = "^[a-zA-Z0-9_]{3,20}$";

    // Gender: case-insensitive (if needed), or validate via enum
    public static final List<String> GENDERS = List.of("MALE", "FEMALE", "OTHER");

    // Allows alphabetic characters, underscores, and spaces with minimum 2 characters
    public static final String ALPHABETIC_MIN_2_CHARS = "^([A-Za-z_ ]){2,}$";

    // Basic email validation
    public static final String EMAIL = "^([\\w\\.\\-]+)@([\\w\\-]+)((\\.(\\w){2,3})+)$";

    // International phone number (starts with +, followed by digits, between 7 to 15 total digits)
    public static final String PHONE = "^\\+?[1-9]\\d{6,14}$";

    // Password with minimum 8 characters, at least one upper case, one lower case, one digit, one special character
    public static final String PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    // Date of Birth in ISO format (yyyy-MM-dd)
    public static final String DATE_OF_BIRTH = "^\\d{4}-\\d{2}-\\d{2}$";
}

