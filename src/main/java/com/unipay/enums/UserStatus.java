package com.unipay.enums;


public enum UserStatus {
    PENDING,        // awaiting email verification
    ACTIVE,         // fully active user
    SUSPENDED,      // temporarily disabled due to policy violations
    DEACTIVATED,    // user-initiated deactivation
    BLOCKED,        // blocked by admin or system
    DELETED         // permanently removed
}
