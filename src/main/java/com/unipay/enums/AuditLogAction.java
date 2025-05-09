package com.unipay.enums;

/**
 * Enum to define various actions that can be logged in the audit logs.
 */
public enum AuditLogAction {

    USER_CREATED("User Created"),
    USER_UPDATED("User Updated"),
    USER_DELETED("User Deleted"),
    PROFILE_UPDATED("Profile Updated"),
    PASSWORD_CHANGED("Password Changed"),
    LOGIN_SUCCESS("Login Successful"),
    LOGIN_FAILED("Login Failed"),
    SETTINGS_UPDATED("Settings Updated"),
    ACCOUNT_LOCKED("Account Locked"),
    PASSWORD_RESET_REQUEST("Password Reset Requested"),
    ACCOUNT_UNLOCKED("Account Unlocked");

    private final String action;

    AuditLogAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
