package com.unipay.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class UserRoleDto extends BaseEntityDto{
    private String userId;
    private String roleId;
    private LocalDateTime assignedAt;
}
