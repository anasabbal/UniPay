package com.unipay.dto;

import lombok.Data;

import java.util.Set;

/**
 * Defines a role in the system containing a collection of permissions.
 */
@Data
public class RoleDto extends BaseEntityDto {
    /**
     * Name of the role (e.g., ADMIN, USER, MANAGER).
     */
    private String name;

    /**
     * Description of the role’s purpose and access scope.
     */
    private String description;

    /**
     * Set of permissions granted to users assigned to this role.
     */
    private Set<PermissionDto> permissions;
}
