package com.unipay.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserSettingsDto {
    private boolean emailNotificationsEnabled;
    private String preferredLanguage;
    private String timezone;
}
