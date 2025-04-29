package com.unipay.repository;

import com.unipay.enums.PermissionName;
import com.unipay.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
    Optional<Permission> findByName(PermissionName name);
}
