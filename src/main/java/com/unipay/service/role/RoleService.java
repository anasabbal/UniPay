package com.unipay.service.role;

import com.unipay.enums.RoleName;
import com.unipay.models.User;

public interface RoleService {
    void assignRoleToUser(User user, RoleName roleName);
}
