package com.unipay.mapper;

import com.unipay.dto.CurrentUser;
import com.unipay.dto.UserDto;
import com.unipay.models.User;
import org.mapstruct.Mapper;

/**
 * Mapper interface that defines the mapping between the `User` model and `UserDto` data transfer object.
 * It uses MapStruct to automatically generate the implementation at compile-time.
 * This class maps data between the User entity and the corresponding Data Transfer Object (DTO).
 */
@Mapper(componentModel = "spring")
public abstract class UserMapper {

    /**
     * Converts a `User` entity to a `UserDto` DTO.
     * @param user The `User` entity to convert.
     * @return The converted `UserDto` object.
     */
    public abstract UserDto toDto(User user);
    public abstract CurrentUser toUser(User user);
}
