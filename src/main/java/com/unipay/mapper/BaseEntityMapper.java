package com.unipay.mapper;


import com.unipay.dto.BaseEntityDto;
import com.unipay.models.BaseEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class BaseEntityMapper {
    public abstract BaseEntityDto toDto(BaseEntity baseEntity);
}
