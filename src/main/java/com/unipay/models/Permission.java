package com.unipay.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToMany;

import java.util.Set;

@Entity
@Table(name = "permissions")
public class Permission extends BaseEntity {
    private String name;
    private String description;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;
}
