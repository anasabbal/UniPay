package com.unipay.service.role;


import com.unipay.enums.RoleName;
import com.unipay.exception.BusinessException;
import com.unipay.exception.ExceptionPayloadFactory;
import com.unipay.models.Role;
import com.unipay.models.User;
import com.unipay.models.UserRole;
import com.unipay.repository.RoleRepository;
import com.unipay.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

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
}
