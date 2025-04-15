package com.unipay.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    private String name;
    private String description;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<UserRole> userRoles;

    @ManyToMany
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;
}
