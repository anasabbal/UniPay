package com.unipay.command;


import lombok.Getter;

@Getter
public class UserSettingsCommand {
    private boolean emailNotificationsEnabled;
    private String preferredLanguage;
    private String timezone;
}
