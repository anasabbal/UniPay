package com.unipay.service.role;

import com.unipay.enums.PermissionName;
import com.unipay.enums.RoleName;
import com.unipay.models.User;

import java.util.Set;

/**
 * Service interface for managing user roles and their associated permissions.
 * Provides methods to assign roles to users and update role definitions.
 */
public interface RoleService {

    /**
     * Assigns a specified role to a user.
     *
     * @param user     The user to whom the role should be assigned.
     * @param roleName The name of the role to assign.
     */
    void assignRoleToUser(User user, RoleName roleName);

    /**
     * Updates the description and permissions of a given role.
     *
     * @param roleName       The name of the role to update.
     * @param newDescription The new description for the role.
     * @param newPermissions The new set of permissions to associate with the role.
     */
    void updateRole(RoleName roleName, String newDescription, Set<PermissionName> newPermissions);
}
