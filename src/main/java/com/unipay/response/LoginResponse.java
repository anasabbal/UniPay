package com.unipay.response;

import com.unipay.utils.JwtService;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private JwtService.TokenPair token;
    private List<String> roles;
}