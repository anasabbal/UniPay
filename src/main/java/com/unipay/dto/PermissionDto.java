package com.unipay.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PermissionDto extends BaseEntityDto{
    private String name;
    private String description;
}
