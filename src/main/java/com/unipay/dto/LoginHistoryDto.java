package com.unipay.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class LoginHistoryDto extends BaseEntityDto{
    private String id;
    /**
     * The timestamp marking when the login attempt occurred.
     * This value is automatically set when a login record is created.
     */
    private LocalDateTime loginTimestamp;

    /**
     * The IP address from which the login attempt was made.
     * This helps in tracking the location or origin of the login attempt.
     */
    private String ipAddress;

    /**
     * The user agent string of the client's browser or application.
     * This provides information about the client device and software.
     */
    private String userAgent;

    /**
     * A boolean flag indicating whether the login attempt was successful.
     * A value of true means the login attempt succeeded, and false means it failed.
     */
    private boolean successful;
}
