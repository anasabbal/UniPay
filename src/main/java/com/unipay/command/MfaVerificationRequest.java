package com.unipay.command;

public record MfaVerificationRequest(
        String challengeToken,
        String code
) {
}
