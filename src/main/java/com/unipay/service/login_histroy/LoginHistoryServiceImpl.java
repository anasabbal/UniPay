package com.unipay.service.login_histroy;


import com.unipay.models.LoginHistory;
import com.unipay.models.User;
import com.unipay.repository.LoginHistoryRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@link LoginHistoryService} interface that handles login history creation.
 * This service is responsible for creating login history entries associated with a user.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginHistoryServiceImpl implements LoginHistoryService {

    private final LoginHistoryRepository loginHistoryRepository;

    /**
     * Creates a login history entry for the user and associates it with the provided user.
     * This method also ensures that the user's login history is updated correctly.
     *
     * @param user The user who logged in.
     * @param request The HTTP request to extract the IP address and user agent.
     * @param successful Indicates if the login was successful.
     */
    @Override
    public void createLoginHistory(User user, HttpServletRequest request, boolean successful) {
        String ipAddress = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setUser(user);
        loginHistory.setLoginTimestamp(java.time.LocalDateTime.now());
        loginHistory.setIpAddress(ipAddress);
        loginHistory.setUserAgent(userAgent);
        loginHistory.setSuccessful(successful);

        user.addLoginHistory(loginHistory);
        loginHistoryRepository.save(loginHistory);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<LoginHistory> getLoginHistoryByUserId(Pageable pageable, String userId) {
        log.info("Begin fetching LoginHistory with userId {}", userId);
        final Page<LoginHistory> loginHistories = loginHistoryRepository.getLoginHistoriesByUser_Id(pageable, userId);
        log.info("LoginHistory fetched successfully");
        return loginHistories;
    }
}
