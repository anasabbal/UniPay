package com.unipay.command;


import com.unipay.utils.AssertValidation;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ProfileCommand {
    private String fullName;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String gender;
    private String nationality;


    public void validate() {
        AssertValidation.assertValidFullName(fullName);
        AssertValidation.assertValidGender(gender);
        AssertValidation.assertValidDateOfBirth(dateOfBirth);
        AssertValidation.assertValidPhoneNumber(phoneNumber);
    }
}
