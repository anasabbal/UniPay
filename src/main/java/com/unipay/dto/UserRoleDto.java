package com.unipay.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Represents a role assigned to a user.
 * This class stores the role information and the timestamp when it was assigned to the user.
 */
@Data
@Getter
@Setter
public class UserRoleDto extends BaseEntityDto {
    /**
     * The role assigned to the user. This can be a predefined role such as ADMIN, USER, etc.
     */
    private RoleDto role;

    /**
     * Timestamp indicating when the role was assigned to the user.
     */
    private LocalDateTime assignedAt;
}
