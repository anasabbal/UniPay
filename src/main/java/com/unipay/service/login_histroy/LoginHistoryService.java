package com.unipay.service.login_histroy;

import com.unipay.models.User;
import jakarta.servlet.http.HttpServletRequest;

public interface LoginHistoryService {
    void createLoginHistory(User user, HttpServletRequest request, boolean successful);
}
