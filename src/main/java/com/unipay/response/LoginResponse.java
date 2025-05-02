package com.unipay.response;

import com.unipay.utils.JwtService;
import lombok.Getter;

import java.util.List;

@Getter
public class LoginResponse {
    private final JwtService.JwtTokenPair tokenPair;
    private final List<String> roles;
    private final boolean mfaRequired;
    private final String mfaChallenge;

    private LoginResponse(JwtService.JwtTokenPair tokenPair,
                          List<String> roles,
                          boolean mfaRequired,
                          String mfaChallenge) {
        this.tokenPair = tokenPair;
        this.roles = roles;
        this.mfaRequired = mfaRequired;
        this.mfaChallenge = mfaChallenge;
    }

    public static LoginResponse success(JwtService.JwtTokenPair tokenPair, List<String> roles) {
        return new LoginResponse(tokenPair, roles, false, null);
    }

    public static LoginResponse mfaRequired(String mfaChallenge) {
        return new LoginResponse(null, null, true, mfaChallenge);
    }

    public static LoginResponse error() {
        return new LoginResponse(null, null, false, null);
    }
}