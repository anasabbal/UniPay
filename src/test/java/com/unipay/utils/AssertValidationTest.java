package com.unipay.utils;


import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class AssertValidationTest {

    @Test
    void shouldThrowIllegalArgumentExceptionIfPhoneNumberIsInvalid() {
        String invalidPhoneNumber = "123abc";
        String fieldName = "Phone Number";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            AssertValidation.assertValidPhoneNumber(invalidPhoneNumber);
        });

        assertEquals(fieldName + " is invalid.", exception.getMessage());
    }

    @Test
    void shouldNotThrowExceptionIfPhoneNumberIsValid() {
        String validPhoneNumber = "+1234567890";

        assertDoesNotThrow(() -> AssertValidation.assertValidPhoneNumber(validPhoneNumber));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionIfPasswordIsInvalid() {
        String invalidPassword = "123";
        String fieldName = "Password";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            AssertValidation.assertValidPassword(invalidPassword);
        });

        assertEquals(fieldName + " is invalid.", exception.getMessage());
    }

    @Test
    void shouldNotThrowExceptionIfPasswordIsValid() {
        String validPassword = "ValidPassword123!";

        assertDoesNotThrow(() -> AssertValidation.assertValidPassword(validPassword));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionIfFullNameIsInvalid() {
        String invalidFullName = "A";
        String fieldName = "Full Name";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            AssertValidation.assertValidFullName(invalidFullName);
        });

        assertEquals(fieldName + " is invalid.", exception.getMessage());
    }

    @Test
    void shouldNotThrowExceptionIfFullNameIsValid() {
        String validFullName = "Uni pay";

        assertDoesNotThrow(() -> AssertValidation.assertValidFullName(validFullName));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionIfEmailIsInvalid() {
        String invalidEmail = "invalidemail@com";
        String fieldName = "Email";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            AssertValidation.assertValidEmail(invalidEmail);
        });

        assertEquals(fieldName + " is invalid.", exception.getMessage());
    }

    @Test
    void shouldNotThrowExceptionIfEmailIsValid() {
        String validEmail = "valid@example.com";

        assertDoesNotThrow(() -> AssertValidation.assertValidEmail(validEmail));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionIfDateOfBirthIsInTheFuture() {
        LocalDate futureDateOfBirth = LocalDate.now().plusDays(1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            AssertValidation.assertValidDateOfBirth(futureDateOfBirth);
        });

        assertEquals("Date of Birth is invalid or in the future.", exception.getMessage());
    }

    @Test
    void shouldNotThrowExceptionIfDateOfBirthIsValid() {
        LocalDate validDateOfBirth = LocalDate.now().minusYears(25);

        assertDoesNotThrow(() -> AssertValidation.assertValidDateOfBirth(validDateOfBirth));
    }
}
