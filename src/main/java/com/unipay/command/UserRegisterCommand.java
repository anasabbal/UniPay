package com.unipay.command;


import com.unipay.utils.AssertValidation;
import lombok.Getter;


@Getter
public class UserRegisterCommand {
    private String username;
    private String email;
    private String password;
    private ProfileCommand profile;
    private UserSettingsCommand settings;

    public void validate() {
        AssertValidation.assertValidUsername(username);
        AssertValidation.assertValidPassword(password);
        AssertValidation.assertValidEmail(email);
        profile.validate();
        settings.validate();
    }
}
