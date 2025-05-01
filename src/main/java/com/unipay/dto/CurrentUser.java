package com.unipay.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Data
@Getter
@Setter
public class CurrentUser extends BaseEntityDto{
    /**
     * The username of the user. It must be unique and can be used for logging in.
     */
    private String username;

    /**
     * The email address of the user. It must be valid and unique.
     */
    private String email;
    /**
     * The user's profile information. This includes details like the user's full name,
     * date of birth, and other personal information.
     */
    private UserProfileDto profile;

    /**
     * The user's settings, such as their preferred language, timezone, and notification preferences.
     */
    private UserSettingsDto settings;
    /**
     * The set of roles assigned to the user.
     * This relationship supports role-based access control (RBAC), defining what actions the user can perform.
     */
    private Set<UserRoleDto> userRoles;
}
