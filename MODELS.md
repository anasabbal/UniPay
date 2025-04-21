# UniPay – User Management Entities

## Overview

**UniPay** is a payment gateway platform built using **Java Spring Boot**. The user management module includes various entities designed to support user accounts, profiles, roles, permissions, login activity, audit logging, and more. The system follows best practices with JPA/Hibernate and uses a monolithic architecture.

This document provides detailed descriptions of the core entities involved in user management, along with example JSON representations.

---

## 📦 Entities Description

### 1. `User`

Represents the core user account.

#### JSON Example:
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john.doe@example.com",
  "passwordHash": "hashedPasswordHere",
  "status": "ACTIVE",
  "profile": {
    "id": 1,
    "fullName": "John Doe",
    "dateOfBirth": "1990-01-01",
    "phoneNumber": "+123456789",
    "gender": "Male",
    "nationality": "American",
    "addresses": [
      {
        "id": 1,
        "street": "123 Main St",
        "city": "Springfield",
        "state": "IL",
        "zipCode": "62701",
        "country": "USA",
        "addressType": "HOME"
      }
    ]
  },
  "roles": [
    {
      "id": 1,
      "name": "ADMIN",
      "description": "Administrator role with full access"
    }
  ],
  "loginHistories": [
    {
      "id": 1,
      "loginTimestamp": "2025-04-21T14:30:00",
      "ipAddress": "192.168.1.1",
      "userAgent": "Mozilla/5.0",
      "successful": true
    }
  ],
  "settings": {
    "id": 1,
    "emailNotificationsEnabled": true,
    "preferredLanguage": "English",
    "timezone": "UTC"
  },
  "securityQuestions": [
    {
      "id": 1,
      "question": "What is your mother's maiden name?",
      "answerHash": "hashedAnswerHere"
    }
  ],
  "auditLogs": [
    {
      "id": 1,
      "action": "User Login",
      "details": "Login successful from web",
      "timestamp": "2025-04-21T14:30:00"
    }
  ]
}
```

### 2. `UserProfile`

Holds extended user information.

#### JSON Example:
```json
{
  "id": 1,
  "user": {
    "id": 1,
    "username": "john_doe"
  },
  "fullName": "John Doe",
  "dateOfBirth": "1990-01-01",
  "phoneNumber": "+123456789",
  "gender": "Male",
  "nationality": "American",
  "addresses": [
    {
      "id": 1,
      "street": "123 Main St",
      "city": "Springfield",
      "state": "IL",
      "zipCode": "62701",
      "country": "USA",
      "addressType": "HOME"
    }
  ]
}
```

### 3. `Address`

Stores address details for a UserProfile.

#### JSON Example:
```json
{
   "id": 1,
   "userProfile": {
      "id": 1,
      "fullName": "John Doe"
   },
   "street": "123 Main St",
   "city": "Springfield",
   "state": "IL",
   "zipCode": "62701",
   "country": "USA",
   "addressType": "HOME"
}
```

### 4. `UserSettings`

Stores user preferences and configuration.

#### JSON Example:
```json
{
   "id": 1,
   "user": {
      "id": 1,
      "username": "john_doe"
   },
   "emailNotificationsEnabled": true,
   "preferredLanguage": "English",
   "timezone": "UTC"
}
```

### 5. `Role`

Represents a user role in the system (e.g., Admin, Merchant).

#### JSON Example:
```json
{
   "id": 1,
   "name": "ADMIN",
   "description": "Administrator role with full access",
   "userRoles": [
      {
         "id": 1,
         "user": {
            "id": 1,
            "username": "john_doe"
         },
         "assignedAt": "2025-04-21T14:00:00"
      }
   ],
   "permissions": [
      {
         "id": 1,
         "name": "CREATE_USER",
         "description": "Permission to create new users"
      }
   ]
}
```
### 6. `UserRole`

Mapping table between User and Role.

#### JSON Example:
```json
{
   "id": 1,
   "user": {
      "id": 1,
      "username": "john_doe"
   },
   "role": {
      "id": 1,
      "name": "ADMIN"
   },
   "assignedAt": "2025-04-21T14:00:00"
}
```

### 7. `Permission`

Defines granular permissions (e.g., CREATE_USER, VIEW_TRANSACTIONS).

#### JSON Example:
```json
{
   "id": 1,
   "name": "CREATE_USER",
   "description": "Permission to create new users",
   "roles": [
      {
         "id": 1,
         "name": "ADMIN"
      }
   ]
}
```

### 8. `LoginHistory`

Tracks user login activity.

#### JSON Example:
```json
{
   "id": 1,
   "user": {
      "id": 1,
      "username": "john_doe"
   },
   "loginTimestamp": "2025-04-21T14:30:00",
   "ipAddress": "192.168.1.1",
   "userAgent": "Mozilla/5.0",
   "successful": true
}
```

### 9. `SecurityQuestion`

Stores security questions and hashed answers for password recovery.

#### JSON Example:
```json
{
   "id": 1,
   "user": {
      "id": 1,
      "username": "john_doe"
   },
   "question": "What is your mother's maiden name?",
   "answerHash": "hashedAnswerHere"
}
```

### 10. `AuditLog`

Logs user actions for auditing purposes.

#### JSON Example:
```json
{
   "id": 1,
   "user": {
      "id": 1,
      "username": "john_doe"
   },
   "action": "User Login",
   "details": "Login successful from web",
   "timestamp": "2025-04-21T14:30:00"
}
```

### 11. `BaseEntity`

Abstract base class for all entities (common fields like ID, timestamps).

#### JSON Example:
```json
{
   "id": 1,
   "createdAt": "2025-04-20T14:00:00",
   "updatedAt": "2025-04-21T14:00:00",
   "version": 1,
   "isDeleted": false
}
```