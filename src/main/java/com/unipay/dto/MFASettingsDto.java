package com.unipay.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Data
@Getter
@Setter
public class MFASettingsDto {
    private boolean enabled;
    private String secret;
    private List<String> recoveryCodes;
}
