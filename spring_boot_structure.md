src/main/java/com/yourapp/

├── YourAppApplication.java

├── config/
│   ├── security/
│   │   ├── SecurityConfig.java
│   │   ├── JwtFilter.java
│   │   └── JwtProvider.java
│   │
│   └── app_config/
│       ├── WebConfig.java
│       └── OpenApiConfig.java

├── core/
│   ├── exception/
│   │   ├── GlobalExceptionHandler.java
│   │   ├── ApiException.java
│   │   └── ErrorResponse.java
│   │
│   ├── utils/
│   │   ├── DateUtils.java
│   │   └── SecurityUtils.java
│   │
│   └── constants/
│       └── AppConstants.java

├── modules/
│   ├── auth/
│   │   ├── controller/
│   │   │   └── AuthController.java
│   │   ├── service/
│   │   │   ├── AuthService.java
│   │   │   └── impl/AuthServiceImpl.java
│   │   ├── repository/
│   │   │   └── UserRepository.java
│   │   ├── entity/
│   │   │   └── User.java
│   │   ├── dto/
│   │   │   ├── LoginRequest.java
│   │   │   └── AuthResponse.java
│   │   └── mapper/
│   │       └── UserMapper.java
│
│   ├── transaction/
│   │   ├── controller/
│   │   │   └── TransactionController.java
│   │   ├── service/
│   │   │   ├── TransactionService.java
│   │   │   └── impl/TransactionServiceImpl.java
│   │   ├── repository/
│   │   │   └── TransactionRepository.java
│   │   ├── entity/
│   │   │   └── Transaction.java
│   │   ├── dto/
│   │   │   └── TransactionResponse.java
│   │   └── mapper/
│   │       └── TransactionMapper.java
│
│   └── user/
│       ├── controller/
│       ├── service/
│       ├── repository/
│       ├── entity/
│       └── dto/

├── shared/
│   ├── response/
│   │   ├── ApiResponse.java
│   │   └── PaginationResponse.java
│   │
│   └── enums/
│       └── Role.java

└── infrastructure/
    ├── database/
    │   ├── config/
    │   └── migration/
    │
    └── external/
        └── payment/