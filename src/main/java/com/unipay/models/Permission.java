package com.unipay.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToMany;
import java.util.Set;


/**
 * Represents a specific permission within the system, defining an action or access right.
 * Permissions are associated with roles to implement role-based access control (RBAC).
 * Each permission includes a unique name and an optional description.
 */
@Entity
@Table(name = "permissions")
public class Permission extends BaseEntity {
    private String name;
    private String description;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;
}
