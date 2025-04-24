package com.unipay.mapper;


import com.unipay.dto.UserDto;
import com.unipay.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    public abstract UserDto toDto(User user);
}
