package com.unipay.service.role;

import com.unipay.enums.PermissionName;
import com.unipay.enums.RoleName;
import com.unipay.models.User;

import java.util.Set;

public interface RoleService {
    void assignRoleToUser(User user, RoleName roleName);
    void updateRole(RoleName roleName, String newDescription, Set<PermissionName> newPermissions);
}
