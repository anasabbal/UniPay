package com.unipay.service.session;


import com.unipay.models.User;
import com.unipay.models.UserSession;
import com.unipay.repository.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserSessionServiceImpl implements UserSessionService{

    private final UserSessionRepository sessionRepository;

    @Transactional
    public UserSession createSession(User user, String userAgent, String ipAddress) {
        UserSession session = new UserSession();
        session.setUser(user);
        session.setUserAgent(userAgent);
        session.setIpAddress(ipAddress);
        session.setExpiresAt(Instant.now().plus(7, ChronoUnit.DAYS));
        return sessionRepository.save(session);
    }

    @Transactional
    public void revokeSession(String sessionId) {
        sessionRepository.deleteById(sessionId);
    }

    @Transactional
    public void revokeAllSessions(User user) {
        sessionRepository.deleteByUser(user);
    }
    @Override
    @Transactional(readOnly = true)
    public boolean isSessionValid(String sessionId) {
        return sessionRepository.findById(sessionId)
                .map(session ->
                        !session.isRevoked() &&
                                session.getExpiresAt().isAfter(Instant.now())
                )
                .orElse(false);
    }

    @Override
    @Transactional
    public void invalidateSession(String sessionId) {
        sessionRepository.findById(sessionId)
                .ifPresent(session -> {
                    session.setRevoked(true);
                    sessionRepository.save(session);
                });
    }
}
