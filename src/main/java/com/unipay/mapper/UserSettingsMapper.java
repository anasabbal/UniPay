package com.unipay.mapper;


import com.unipay.dto.UserSettingsDto;
import com.unipay.models.UserSettings;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserSettingsMapper {
    public abstract UserSettingsDto toDto(UserSettings userSettings);
}
