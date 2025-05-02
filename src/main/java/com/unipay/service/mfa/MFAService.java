package com.unipay.service.mfa;

import com.unipay.models.User;

public interface MFAService {
    boolean validateCode(User user, String code);
}
