package com.unipay.dto;

import com.unipay.enums.PermissionName;
import lombok.Data;

/**
 * Represents a specific permission in the system, part of role-based access control (RBAC).
 */
@Data
public class PermissionDto extends BaseEntityDto {
    /**
     * Enum value representing the system permission name.
     */
    private PermissionName name;

    /**
     * A human-readable description of what this permission allows.
     */
    private String description;
}
