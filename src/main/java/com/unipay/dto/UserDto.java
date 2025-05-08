package com.unipay.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * UserDto is a Data Transfer Object (DTO) used for transferring user-related data
 * between different layers of the application (e.g., from controller to service or between services).
 * This class encapsulates the necessary information to represent a user in the system.
 *
 * It includes:
 * - `username`: The unique identifier for the user.
 * - `email`: The email address associated with the user.
 * - `passwordHash`: The hashed password for secure authentication.
 * - `profile`: A reference to the user's profile data (e.g., full name, date of birth).
 * - `settings`: A reference to the user's settings (e.g., language, timezone, email notifications).
 * - `loginHistories`: Historical records of the user's login attempts.
 * - `auditLogs`: Logs of actions performed by the user within the system for security and auditing.
 *
 * This class uses Lombok annotations to automatically generate getters, setters, and other boilerplate code.
 *
 * @see UserProfileDto
 * @see UserSettingsDto
 */
@Data
@Getter
@Setter
public class UserDto extends BaseEntityDto {
    /**
     * The username of the user. It must be unique and can be used for logging in.
     */
    private String username;

    /**
     * The email address of the user. It must be valid and unique.
     */
    private String email;

    /**
     * The hashed password of the user. This field stores the encrypted password
     * for authentication purposes.
     */
    private String passwordHash;

    /**
     * Records of the user's login attempts.
     * This is useful for auditing and tracking user login patterns for security purposes.
     */
    private Set<LoginHistoryDto> loginHistories;

    /**
     * Logs of actions performed by the user within the system, including administrative actions.
     * This allows for tracking user activities for security and auditing purposes.
     */
    private Set<AuditLogDto> auditLogs;

    /**
     * The user's profile information. This includes details like the user's full name,
     * date of birth, and other personal information.
     */
    private UserProfileDto profile;

    /**
     * The user's settings, such as their preferred language, timezone, and notification preferences.
     */
    private UserSettingsDto settings;

    /**
     * The set of roles assigned to the user.
     * This relationship supports role-based access control (RBAC), defining what actions the user can perform.
     */
    private Set<UserRoleDto> userRoles;

    /**
     * MFA (Multi-factor authentication) settings for the user.
     * This includes whether MFA is enabled, the secret key, and recovery codes.
     */
    private MFASettingsDto mfaSettings;

    /**
     * User sessions associated with the user. This helps in tracking active sessions.
     */
    private Set<UserSessionDto> sessions;

    /**
     * Business details related to the user.
     * This may contain the business registration and verification details.
     */
    private BusinessDto business;

    // Payment Operations
    /**
     * User payout settings, including bank account details and payout schedule.
     */
    private PayoutSettingsDto payoutSettings;

    /**
     * List of transactions associated with the user.
     */
    private Set<TransactionDto> transactions;

    // Developer Integration
    /**
     * API keys assigned to the user for system integration.
     */
    private Set<ApiKeyDto> apiKeys;

    /**
     * Webhooks set up by the user for event-driven communication with external services.
     */
    private Set<WebhookDto> webhooks;
}
