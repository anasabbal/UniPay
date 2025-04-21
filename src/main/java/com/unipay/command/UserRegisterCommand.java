package com.unipay.command;


import com.unipay.utils.AssertValidation;
import lombok.Getter;


@Getter
public class UserRegisterCommand {
    private String userName;
    private String email;
    private String password;
    private ProfileCommand profileCommand;
    private UserSettingsCommand userSettingsCommand;

    public void validate() {
        AssertValidation.assertValidFullName(userName);
        AssertValidation.assertValidPassword(password);
        AssertValidation.assertValidEmail(email);
        profileCommand.validate();
        userSettingsCommand.validate();
    }
}
