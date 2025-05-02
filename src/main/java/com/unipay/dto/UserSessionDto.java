package com.unipay.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


@Data
@Getter
@Setter
public class UserSessionDto {
    private String sessionId;
    private String deviceInfo;
    private String ipAddress;
    private Instant expiresAt;
}