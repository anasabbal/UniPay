package com.unipay.enums;

/**
 * Enum to define various permissions within the system.
 */
public enum PermissionName {

    // User Permissions
    VIEW_PROFILE("View Profile"), // USER
    UPDATE_PROFILE("Update Profile"), // USER
    VIEW_ORDERS("View Orders"), // USER  // CLIENT
    CREATE_ORDER("Create Order"), // USER  // CLIENT
    VIEW_PAYMENTS("View Payments"), // USER // CLIENT
    MAKE_PAYMENT("Make Payment"), // USER
    VIEW_INVOICES("View Invoices"), // USER // CLIENT
    VIEW_SUBSCRIPTIONS("View Subscriptions"), // USER // CLIENT
    MANAGE_SUBSCRIPTIONS("Manage Subscriptions"), // USER // CLIENT
    VIEW_BALANCES("View Balances"), // USER  // CLIENT
    VIEW_TERMINALS("View Terminals"),// USER  // CLIENT

    // Admin Permissions
    MANAGE_USERS("Manage Users"), // ADMIN
    CREATE_USER("Create User"), // ADMIN
    UPDATE_USER("Update User"), // ADMIN
    DELETE_USER("Delete User"), // ADMIN
    VIEW_USER("View User"), // ADMIN
    MANAGE_ROLES("Manage Roles"), // ADMIN
    CREATE_ROLE("Create Role"), // ADMIN
    UPDATE_ROLE("Update Role"), // ADMIN
    DELETE_ROLE("Delete Role"), // ADMIN
    VIEW_ROLE("View Role"), // ADMIN
    MANAGE_PERMISSIONS("Manage Permissions"), // ADMIN
    CREATE_PERMISSION("Create Permission"), // ADMIN
    UPDATE_PERMISSION("Update Permission"), // ADMIN
    DELETE_PERMISSION("Delete Permission"), // ADMIN
    VIEW_PERMISSION("View Permission"), // ADMIN
    MANAGE_SETTINGS("Manage Settings"), // ADMIN
    ACCESS_API("Access API"), // ADMIN
    VIEW_AUDIT_LOGS("View Audit Logs"), // ADMIN

    // Client Permissions
    VIEW_DASHBOARD("View Dashboard"), // CLIENT
    EXPORT_DATA("Export Data"), // CLIENT
    IMPORT_DATA("Import Data"), // CLIENT
    CREATE_PAYMENT("Create Payment"), // CLIENT
    CANCEL_PAYMENT("Cancel Payment"), // CLIENT
    REFUND_PAYMENT("Refund Payment"), // CLIENT
    VIEW_REFUNDS("View Refunds"), // CLIENT
    CREATE_REFUND("Create Refund"), // CLIENT
    CANCEL_REFUND("Cancel Refund"), // CLIENT
    VIEW_CUSTOMERS("View Customers"), // CLIENT
    MANAGE_CUSTOMERS("Manage Customers"), // CLIENT
    VIEW_MANDATES("View Mandates"), // CLIENT
    MANAGE_MANDATES("Manage Mandates"), // CLIENT
    VIEW_PROFILES("View Profiles"), // CLIENT
    MANAGE_PROFILES("Manage Profiles"), // CLIENT
    CREATE_INVOICE("Create Invoice"), // CLIENT
    CANCEL_INVOICE("Cancel Invoice"), // CLIENT
    VIEW_SETTLEMENTS("View Settlements"), // CLIENT
    CANCEL_ORDER("Cancel Order"), // CLIENT
    VIEW_SHIPMENTS("View Shipments"), // CLIENT
    CREATE_SHIPMENT("Create Shipment"), // CLIENT
    CANCEL_SHIPMENT("Cancel Shipment"), // CLIENT
    VIEW_ORGANIZATIONS("View Organizations"), // CLIENT
    MANAGE_ORGANIZATIONS("Manage Organizations"), // CLIENT
    VIEW_ONBOARDING_STATUS("View Onboarding Status"), // CLIENT
    MANAGE_ONBOARDING("Manage Onboarding"), // CLIENT
    VIEW_PAYMENT_LINKS("View Payment Links"), // CLIENT
    CREATE_PAYMENT_LINK("Create Payment Link"), // CLIENT
    MANAGE_TERMINALS("Manage Terminals"); // CLIENT

    private final String description;

    PermissionName(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
