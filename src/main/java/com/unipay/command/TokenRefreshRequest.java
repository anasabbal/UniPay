package com.unipay.command;

public record TokenRefreshRequest(
        String refreshToken
) {
}
