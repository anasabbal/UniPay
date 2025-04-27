package com.unipay.models;

import com.unipay.enums.PermissionName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a specific permission within the system, defining an action or access right.
 * Permissions are associated with roles to implement role-based access control (RBAC).
 * Each permission includes a unique name and an optional description.
 *
 * <p>Key Features:
 * <ul>
 *   <li><strong>name</strong>: The unique name of the permission (e.g., "VIEW_DASHBOARD").</li>
 *   <li><strong>description</strong>: A brief description of the permission's purpose.</li>
 *   <li><strong>roles</strong>: The roles that have been granted this permission.</li>
 * </ul>
 *
 * <p>Permissions control access to specific actions or resources within the system,
 * ensuring that users with the appropriate roles can perform actions based on their granted permissions.
 */
@Entity
@Table(name = "permissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Permission extends BaseEntity {
    /**
     * The unique name of the permission (e.g., "VIEW_DASHBOARD").
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private PermissionName name;

    /**
     * A brief description of the permission's purpose.
     */
    private String description;

    /**
     * The roles that have been granted this permission.
     * This relationship supports many-to-many associations between permissions and roles.
     */
    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;

    public static Permission create(PermissionName name, String description) {
        Permission permission = new Permission();
        permission.setName(name);
        permission.setDescription(description);
        permission.setRoles(new HashSet<>());
        return permission;
    }
}
