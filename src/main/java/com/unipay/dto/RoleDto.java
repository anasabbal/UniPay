package com.unipay.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Data
@Getter
@Setter
public class RoleDto extends BaseEntityDto{
    private String name;
    private String description;
    private Set<PermissionDto> permissions;
}
