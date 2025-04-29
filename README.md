# Payment Gateway - Monolithic Java Spring Boot
<img src="img/unipay.png" alt="UniPay Image" width="100" height="100"/>

A robust and scalable monolithic payment gateway API built with **Java Spring Boot**.

## Features

- **User Authentication & Authorization**: Secure login and registration using JWT.
- **Merchant Onboarding & KYC**: Seamless merchant registration with Know-Your-Customer (KYC) checks.
- **Payment Request & Processing**: Handling payment requests, processing, and status tracking.
- **Transaction Lifecycle Management**: Full transaction tracking from initiation to completion.
- **Merchant Dashboards**: Provides merchants with access to real-time transaction data and insights.
- **Scheduled Payouts**: Supports manual and scheduled payouts to merchants.
- **Notifications & Webhooks**: Real-time updates via notifications and webhooks.

## Database Models

For detailed information about the database entities, please refer to the [MODELS.md](MODELS.md) file, which contains a comprehensive description of the core entities involved in user management, payment processing, and related modules.

## Database Models

For an in-depth understanding of the system architecture and design considerations, please refer to the [system design](resources/system-design.md) file located in the resources folder.

## Setup

```bash
# Clone the repository
git clone https://github.com/anasabbal/UniPay.git
cd UniPay

# Build the project
./mvnw clean install

# Run the app
./mvnw spring-boot:run
```

## 🚀 Steps to Contribute

1. **Fork the repository**

2. **Create a branch** using the following naming convention:  
   `UP-01-description-of-feature`  
   (Use `UP-02`, `UP-03`, etc. for subsequent features)

3. **Branch from `develop`**:
```bash
git checkout develop
git pull origin develop
git checkout -b UP-01
```

## 🛠️ System Architecture

### C4 Model Overview
**Context Level**:
```plaintext
+----------------+       +----------------+       +-----------------+
|   Merchant     |       |   Payment      |       |  Banking        |
|   Applications |<----->|   Providers    |<----->|  Systems        |
+----------------+       +----------------+       +-----------------+
          ▲                        ▲                        ▲
          |                        |                        |
          ▼                        ▼                        ▼
+---------------------------------------------------------------+
|                        UniPay Core System                     |
+---------------------------------------------------------------+
```

### Key Flow: Payment Processing
![Payment Processing Flow](img/pay-proc.png)

## Contributing

Open an issue or submit a pull request.
