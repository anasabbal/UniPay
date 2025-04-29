package com.unipay.service.login_histroy;

import com.unipay.models.LoginHistory;
import com.unipay.models.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LoginHistoryService {
    void createLoginHistory(User user, HttpServletRequest request, boolean successful);
    Page<LoginHistory> getLoginHistoryByUserId(Pageable pageable, String userId);
}
