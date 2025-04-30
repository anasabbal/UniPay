package com.unipay.helper;


import com.unipay.command.UserRegisterCommand;
import com.unipay.models.User;
import com.unipay.models.UserProfile;
import com.unipay.models.UserSettings;
import com.unipay.service.audit_log.AuditLogService;
import com.unipay.service.login_histroy.LoginHistoryService;
import com.unipay.service.profile.UserProfileService;
import com.unipay.service.settings.UserSettingsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserRegistrationHelper {

    private final UserProfileService userProfileService;
    private final UserSettingsService userSettingsService;
    private final LoginHistoryService loginHistoryService;
    private final AuditLogService auditLogService;

    @Transactional
    public void associateUserProfileAndSettings(User user, UserRegisterCommand command) {
        UserProfile profile = userProfileService.create(command.getProfile(), user);
        UserSettings settings = userSettingsService.create(command.getSettings(), user);
        user.setProfile(profile);
        user.setSettings(settings);
    }

    @Transactional
    public void logLoginHistory(User user, HttpServletRequest request) {
        loginHistoryService.createLoginHistory(user, request, true);
    }

    @Transactional
    public void auditLogCreate(User user, String action, String details) {
        auditLogService.createAuditLog(user, action, details);
    }
}
