package com.unipay.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
public class UserDto {
    private String username;
    private String email;
    private String passwordHash;
    private UserProfileDto profile;
    private UserSettingsDto settings;
}
