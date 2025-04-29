package com.unipay.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Data
@Getter
@Setter
public class AuditLogDto extends BaseEntityDto{

    private String id;
    /**
     * A short description or title of the action performed.
     * This could include things like "User Registered", "Password Changed", etc.
     */
    private String action;

    /**
     * Detailed information regarding the action performed.
     * This can include data such as the specific changes made or additional context
     * for the action.
     */
    private String details;

    /**
     * The timestamp of when the action occurred.
     * This field captures the exact date and time the action was performed.
     */
    private LocalDateTime timestamp;
}
