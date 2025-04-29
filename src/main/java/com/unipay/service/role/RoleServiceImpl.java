package com.unipay.service.role;


import com.unipay.enums.PermissionName;
import com.unipay.enums.RoleName;
import com.unipay.exception.BusinessException;
import com.unipay.exception.ExceptionPayloadFactory;
import com.unipay.models.Permission;
import com.unipay.models.Role;
import com.unipay.models.User;
import com.unipay.models.UserRole;
import com.unipay.repository.PermissionRepository;
import com.unipay.repository.RoleRepository;
import com.unipay.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PermissionRepository permissionRepository;

    /**
     * Assigns a role to the user.
     *
     * @param user The user to assign the role to.
     * @param roleName The role to assign.
     */
    @Override
    @Transactional
    public void assignRoleToUser(User user, RoleName roleName) {
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(
                        () -> new BusinessException(ExceptionPayloadFactory.ROLE_NOT_FOUND.get()
                        )
                );
        UserRole userRole = UserRole.create(user, role);
        userRoleRepository.save(userRole);
        user.getUserRoles().add(userRole);
    }
    /**
     * Updates an existing role's description and/or permissions.
     *
     * @param roleName      The role to update.
     * @param newDescription Optional new description for the role.
     * @param newPermissions Optional set of new permissions to assign to the role.
     */
    @Override
    @Transactional
    public void updateRole(RoleName roleName, String newDescription, Set<PermissionName> newPermissions) {
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new BusinessException(ExceptionPayloadFactory.ROLE_NOT_FOUND.get()));

        if (newDescription != null && !newDescription.isBlank()) {
            role.setDescription(newDescription);
        }

        if (newPermissions != null) {
            Set<Permission> permissions = new HashSet<>();
            for (PermissionName permissionName : newPermissions) {
                Permission permission = permissionRepository.findByName(permissionName)
                        .orElseThrow(() -> new BusinessException(ExceptionPayloadFactory.PERMISSION_NOT_FOUND.get()));
                permissions.add(permission);
            }
            role.setPermissions(permissions);
        }
        roleRepository.save(role);
    }

}
