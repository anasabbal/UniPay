package com.unipay.command;


import com.unipay.utils.AssertValidation;
import lombok.Getter;

@Getter
public class UserSettingsCommand {
    private boolean emailNotificationsEnabled;
    private String preferredLanguage;
    private String timezone;

    public void validate() {
        AssertValidation.assertValidLanguage(preferredLanguage);
        AssertValidation.assertValidTimezone(timezone);
    }
}
