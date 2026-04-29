# Mini Wallet API

Spring Boot REST API for the Mini-Wallet mobile app.

Companion mobile repo: https://github.com/DrRaspec/Mini-Wallet

## Overview

This service manages wallet transactions and balance summaries for the Mini-Wallet app. It uses Spring Boot, Spring Data JPA, PostgreSQL, MapStruct, Bean Validation, and JWT utilities for authentication support.

## Features

- Create, update, fetch, and delete transactions
- Get all transactions or a single transaction by ID
- Calculate balance, income, and expense totals
- PostgreSQL persistence with JPA entities
- Centralized error handling with custom exceptions
- JWT helper utilities and user model for auth workflows

## Tech Stack

- Java 17
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- Spring Security
- PostgreSQL
- MapStruct
- Lombok

## Project Structure

- `src/main/java/com/bunleng/mini_wallet_api/core` - shared utilities and exception handling
- `src/main/java/com/bunleng/mini_wallet_api/modules/auth` - user model, repository, and auth support
- `src/main/java/com/bunleng/mini_wallet_api/modules/transaction` - transaction API, service, repository, mapper, and DTOs
- `src/main/resources/application.yaml` - application configuration

## Requirements

- JDK 17
- Maven 3.9+ or the included Maven wrapper
- PostgreSQL 14+ running locally or remotely

## Configuration

The application reads its database configuration from environment variables with local defaults:

- `DB_URL` - defaults to `jdbc:postgresql://localhost:5432/mini_wallet_db`
- `DB_USERNAME` - defaults to `mini_wallet_user`
- `DB_PASSWORD` - defaults to `change-me`

JWT settings are also configured in `src/main/resources/application.yaml`.

## Running the API

1. Create the PostgreSQL database if it does not already exist.
2. Set the database environment variables if you do not want to use the defaults.
3. Start the application with the Maven wrapper:

```bash
./mvnw spring-boot:run
```

On Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

The API starts on the default Spring Boot port `8080`.

## Build and Test

```bash
./mvnw clean test
./mvnw clean package
```

On Windows PowerShell:

```powershell
.\mvnw.cmd clean test
.\mvnw.cmd clean package
```

## API Endpoints

Base path: `/api/v1/transactions`

| Method | Path | Description |
| --- | --- | --- |
| POST | `/` | Create a transaction |
| GET | `/` | List all transactions |
| GET | `/{transactionId}` | Get a transaction by ID |
| GET | `/balance` | Get current balance summary |
| PUT | `/{transactionId}` | Update a transaction |
| DELETE | `/{transactionId}` | Delete a transaction |

### Transaction Payload

```json
{
  "title": "Salary",
  "amount": 2500,
  "isIncome": true
}
```

## Notes

- Transactions are stored with UUID identifiers.
- Balance is computed from the stored transaction data as income minus expense.
- Timestamp fields are formatted as ISO local date-time strings in responses.
