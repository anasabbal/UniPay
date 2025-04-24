package com.unipay.mapper;


import com.unipay.dto.UserProfileDto;
import com.unipay.models.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserProfileMapper {
    public abstract UserProfileDto toDto(UserProfile userProfile);
}
