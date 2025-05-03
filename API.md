# UniPay API Documentation

**Base URL:**  
`https://api.unipay.com/v1`
`http://localhost:8080/api/swagger-ui/index.html#/`

## 📌 Overview

The UniPay API provides a comprehensive suite of endpoints for authentication, payment operations, multi-factor authentication, user management, developer integrations, and more. All requests and responses follow RESTful principles and are formatted as JSON.

---

## 🔐 1. Authentication API

| Endpoint            | Method | Description            | Auth Required       |
|---------------------|--------|------------------------|---------------------|
| `/auth/register`    | POST   | Register new user      | ❌ No               |
| `/auth/login`       | POST   | Login with credentials | ❌ No               |
| `/auth/mfa/verify`  | POST   | Verify MFA code        | ❌ No               |
| `/auth/refresh`     | POST   | Refresh access token   | ✅ Yes (Refresh)    |

---

## 👤 2. User Management API

| Endpoint                          | Method | Description                  |
|-----------------------------------|--------|------------------------------|
| `/users/{userId}`                 | GET    | Get user details             |
| `/users/{userId}`                 | PUT    | Update user profile          |
| `/users/{userId}/password`        | PUT    | Change password              |
| `/users/{userId}/status`          | PATCH  | Update user status           |

---

## 🔒 3. MFA (Multi-Factor Authentication) API

| Endpoint                                         | Method | Description                        |
|--------------------------------------------------|--------|------------------------------------|
| `/users/{userId}/mfa/enable`                    | POST   | Enable MFA                         |
| `/users/{userId}/mfa/disable`                   | POST   | Disable MFA                        |
| `/users/{userId}/mfa/qrcode`                    | GET    | Get MFA QR code image              |
| `/users/{userId}/mfa/recovery-codes`           | GET    | Get recovery codes                 |
| `/users/{userId}/mfa/recovery-codes`           | POST   | Generate new recovery codes        |

---

## 💼 4. Session Management API

| Endpoint                                          | Method | Description                     |
|---------------------------------------------------|--------|---------------------------------|
| `/users/{userId}/sessions`                       | GET    | List active sessions            |
| `/users/{userId}/sessions/{sessionId}`          | DELETE | Revoke specific session         |
| `/users/{userId}/sessions`                       | DELETE | Revoke all sessions             |

---

## 💸 5. Payment Operations API

| Endpoint                                             | Method | Description                       |
|------------------------------------------------------|--------|-----------------------------------|
| `/transactions`                                     | POST   | Create new payment                |
| `/transactions/{transactionId}`                     | GET    | Get transaction details           |
| `/transactions`                                     | GET    | List transactions (filterable)    |
| `/transactions/{transactionId}/refund`              | POST   | Process refund                    |
| `/payouts`                                          | POST   | Initiate payout                   |
| `/payouts/{payoutId}`                              | GET    | Get payout status                 |

---

## 🔐 6. Security & Compliance API

| Endpoint                                             | Method | Description                         |
|------------------------------------------------------|--------|-------------------------------------|
| `/users/{userId}/security-questions`                | GET    | List security questions             |
| `/users/{userId}/security-questions`                | POST   | Set security questions              |
| `/users/{userId}/consents`                          | GET    | Get consent history                 |
| `/audit-logs`                                       | GET    | Get audit logs (filterable)         |

---

## 🏢 7. Business Management API (KYB)

| Endpoint                                               | Method | Description                    |
|--------------------------------------------------------|--------|--------------------------------|
| `/businesses`                                         | POST   | Register business              |
| `/businesses/{businessId}`                            | GET    | Get business details           |
| `/businesses/{businessId}/verification`               | POST   | Submit verification documents  |

---

## 🧑‍💻 8. Developer API

| Endpoint                      | Method | Description                |
|-------------------------------|--------|----------------------------|
| `/api-keys`                  | POST   | Generate new API key       |
| `/api-keys/{keyId}`          | DELETE | Revoke API key             |
| `/webhooks`                  | POST   | Register webhook           |
| `/webhooks/{webhookId}`      | DELETE | Remove webhook             |

---

## ⚙️ 9. System API

| Endpoint                    | Method | Description                  |
|-----------------------------|--------|------------------------------|
| `/currencies`              | GET    | Supported currencies         |
| `/payment-methods`         | GET    | Available payment methods    |
| `/system/health`           | GET    | Service status               |

---

## 🔎 Special Parameters

### Pagination Example:
```http
GET /transactions?page=0&size=20&sort=createdAt,desc
