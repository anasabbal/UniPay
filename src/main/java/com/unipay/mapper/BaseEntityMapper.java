package com.unipay.mapper;


import com.unipay.dto.BaseEntityDto;
import com.unipay.dto.UserDto;
import com.unipay.models.BaseEntity;
import com.unipay.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class BaseEntityMapper {
    public abstract BaseEntityDto toDto(BaseEntity baseEntity);
}
