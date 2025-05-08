package com.unipay.dto;

import lombok.Data;

import java.util.List;

/**
 * Contains settings and metadata related to the user's Multi-Factor Authentication (MFA).
 */
@Data
public class MFASettingsDto {
    /**
     * Whether MFA is enabled for the user.
     */
    private boolean enabled;

    /**
     * The user's MFA secret key used to generate time-based codes.
     */
    private String secret;

    /**
     * A list of recovery codes the user can use if they lose access to their MFA device.
     */
    private List<String> recoveryCodes;
}
