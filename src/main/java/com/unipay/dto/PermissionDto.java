package com.unipay.dto;


import com.unipay.enums.PermissionName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PermissionDto extends BaseEntityDto{
    private PermissionName name;
    private String description;
}
