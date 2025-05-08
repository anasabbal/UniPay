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

/**
 * Implementation of {@link UserSessionService} that provides
 * the logic for managing user sessions including creation, validation,
 * and revocation of sessions.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserSessionServiceImpl implements UserSessionService {

    private final UserSessionRepository sessionRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public UserSession createSession(User user, String userAgent, String ipAddress) {
        UserSession session = new UserSession();
        session.setUser(user);
        session.setUserAgent(userAgent);
        session.setIpAddress(ipAddress);
        session.setExpiresAt(Instant.now().plus(7, ChronoUnit.DAYS));
        return sessionRepository.save(session);
    }

    /**
     * Revokes the session identified by the provided session ID by deleting it from the repository.
     *
     * @param sessionId The ID of the session to revoke.
     */
    @Transactional
    public void revokeSession(String sessionId) {
        sessionRepository.deleteById(sessionId);
    }

    /**
     * Revokes all active sessions for the given user by removing them from the repository.
     *
     * @param user The user whose sessions are to be revoked.
     */
    @Transactional
    public void revokeAllSessions(User user) {
        sessionRepository.deleteByUser(user);
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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
