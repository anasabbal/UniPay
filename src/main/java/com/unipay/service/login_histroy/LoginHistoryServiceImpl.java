package com.unipay.service.login_histroy;

import com.unipay.models.LoginHistory;
import com.unipay.models.User;
import com.unipay.repository.LoginHistoryRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        String ipAddress = request.getRemoteAddr(); // Get IP address of the client
        String userAgent = request.getHeader("User-Agent"); // Get the user agent (browser/device information)

        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setUser(user);
        loginHistory.setLoginTimestamp(java.time.LocalDateTime.now());
        loginHistory.setIpAddress(ipAddress);
        loginHistory.setUserAgent(userAgent);
        loginHistory.setSuccessful(successful);

        // Save the login history and also add it to the user
        user.addLoginHistory(loginHistory);
        loginHistoryRepository.save(loginHistory);
    }
}
