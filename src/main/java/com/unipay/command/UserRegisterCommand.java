package com.unipay.command;


import lombok.Getter;


@Getter
public class UserRegisterCommand {
    private String userName;
    private String email;
    private String password;
    private ProfileCommand profileCommand;
    private UserSettingsCommand userSettingsCommand;
}
