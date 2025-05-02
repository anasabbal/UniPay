package com.unipay.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Data
@Getter
@Setter
public class ApiKeyDto {
    private String keyId;
    private String name;
    private List<String> permissions;
    private Instant expiresAt;
}
