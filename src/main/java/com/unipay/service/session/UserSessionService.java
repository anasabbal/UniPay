package com.unipay.service.session;

import com.unipay.models.User;
import com.unipay.models.UserSession;

public interface UserSessionService {
    boolean isSessionValid(String sessionId);
    void invalidateSession(String sessionId);
    UserSession createSession(User user, String userAgent, String ipAddress);
}
