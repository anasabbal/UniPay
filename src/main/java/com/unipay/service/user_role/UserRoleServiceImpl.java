package com.unipay.service.user_role;


import com.unipay.enums.RoleName;
import com.unipay.exception.BusinessException;
import com.unipay.exception.ExceptionPayloadFactory;
import com.unipay.models.Role;
import com.unipay.models.User;
import com.unipay.models.UserRole;
import com.unipay.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService{

    private final UserRoleRepository userRoleRepository;
}
