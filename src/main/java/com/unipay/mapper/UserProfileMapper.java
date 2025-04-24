package com.unipay.mapper;

import com.unipay.dto.UserProfileDto;
import com.unipay.models.UserProfile;
import org.mapstruct.Mapper;

/**
 * Mapper interface that defines the mapping between the `UserProfile` model and `UserProfileDto` data transfer object.
 * It uses MapStruct to automatically generate the implementation at compile-time.
 * This class maps data between the UserProfile entity and the corresponding Data Transfer Object (DTO).
 */
@Mapper(componentModel = "spring")
public abstract class UserProfileMapper {

    /**
     * Converts a `UserProfile` entity to a `UserProfileDto` DTO.
     * @param userProfile The `UserProfile` entity to convert.
     * @return The converted `UserProfileDto` object.
     */
    public abstract UserProfileDto toDto(UserProfile userProfile);
}
