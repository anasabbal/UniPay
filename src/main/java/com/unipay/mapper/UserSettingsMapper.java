package com.unipay.mapper;

import com.unipay.dto.UserSettingsDto;
import com.unipay.models.UserSettings;
import org.mapstruct.Mapper;

/**
 * Mapper interface that defines the mapping between the `UserSettings` model and `UserSettingsDto` data transfer object.
 * It uses MapStruct to automatically generate the implementation at compile-time.
 * This class maps data between the UserSettings entity and the corresponding Data Transfer Object (DTO).
 */
@Mapper(componentModel = "spring")
public abstract class UserSettingsMapper {

    /**
     * Converts a `UserSettings` entity to a `UserSettingsDto` DTO.
     * @param userSettings The `UserSettings` entity to convert.
     * @return The converted `UserSettingsDto` object.
     */
    public abstract UserSettingsDto toDto(UserSettings userSettings);
}
