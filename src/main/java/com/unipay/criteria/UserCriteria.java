package com.unipay.criteria;

import com.unipay.enums.RoleName;
import com.unipay.enums.UserStatus;
import com.unipay.utils.AssertValidation;
import lombok.Data;

import java.time.LocalDate;



@Data
public class UserCriteria {
    private String username;
    private String email;
    private UserStatus status;
    private RoleName roleName;
    private LocalDate dateOfBirthFrom;
    private LocalDate dateOfBirthTo;

    public void validate(){
        AssertValidation.assertValidUsername(username);
        AssertValidation.assertValidDateOfBirth(dateOfBirthFrom);
        AssertValidation.assertValidDateOfBirth(dateOfBirthTo);
    }
}
