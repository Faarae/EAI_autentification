# Project Structure - JWT Authentication Implementation

```
Enterprise/
├── pom.xml                                    ✅ Updated with JWT & Security dependencies
├── README.md                                  (Original)
├── Procfile                                   (Original)
├── system.properties                          (Original)
│
├── API_AUTH_DOCUMENTATION.md                 ✨ NEW - Complete API documentation
├── IMPLEMENTATION_SUMMARY.md                 ✨ NEW - Implementation guide
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/inventory/
│   │   │       ├── InventoryManagementApplication.java (Original)
│   │   │       │
│   │   │       ├── controller/
│   │   │       │   ├── ProductController.java          (Original)
│   │   │       │   ├── StockController.java            (Original)
│   │   │       │   ├── WarehouseController.java        (Original)
│   │   │       │   └── AuthController.java             ✨ NEW - Auth endpoints
│   │   │       │
│   │   │       ├── entity/
│   │   │       │   ├── Product.java                    (Original)
│   │   │       │   ├── Stock.java                      (Original)
│   │   │       │   ├── StockMovement.java              (Original)
│   │   │       │   ├── Warehouse.java                  (Original)
│   │   │       │   ├── InventoryAlert.java             (Original)
│   │   │       │   └── User.java                       ✨ NEW - User entity
│   │   │       │
│   │   │       ├── dto/
│   │   │       │   ├── ProductRequest.java             (Original)
│   │   │       │   ├── ProductResponse.java            (Original)
│   │   │       │   ├── StockRequest.java               (Original)
│   │   │       │   ├── StockResponse.java              (Original)
│   │   │       │   ├── StockMovementRequest.java       (Original)
│   │   │       │   ├── StockMovementResponse.java      (Original)
│   │   │       │   ├── WarehouseRequest.java           (Original)
│   │   │       │   ├── WarehouseResponse.java          (Original)
│   │   │       │   ├── InventoryAlertResponse.java     (Original)
│   │   │       │   ├── LoginRequest.java               ✨ NEW - Login DTO
│   │   │       │   ├── LoginResponse.java              ✨ NEW - Login response DTO
│   │   │       │   ├── RegisterRequest.java            ✨ NEW - Register DTO
│   │   │       │   └── AuthResponse.java               ✨ NEW - Generic auth response
│   │   │       │
│   │   │       ├── repository/
│   │   │       │   ├── ProductRepository.java          (Original)
│   │   │       │   ├── StockRepository.java            (Original)
│   │   │       │   ├── StockMovementRepository.java    (Original)
│   │   │       │   ├── WarehouseRepository.java        (Original)
│   │   │       │   ├── InventoryAlertRepository.java   (Original)
│   │   │       │   └── UserRepository.java             ✨ NEW - User repository
│   │   │       │
│   │   │       ├── service/
│   │   │       │   ├── ProductService.java             (Original)
│   │   │       │   ├── StockService.java               (Original)
│   │   │       │   ├── WarehouseService.java           (Original)
│   │   │       │   └── AuthService.java                ✨ NEW - Auth service
│   │   │       │
│   │   │       ├── exception/
│   │   │       │   └── GlobalExceptionHandler.java     (Original)
│   │   │       │
│   │   │       ├── security/                           ✨ NEW PACKAGE
│   │   │       │   ├── JwtTokenProvider.java           ✨ NEW - JWT utility
│   │   │       │   └── JwtAuthenticationFilter.java    ✨ NEW - JWT filter
│   │   │       │
│   │   │       └── config/                             ✨ NEW PACKAGE
│   │   │           └── SecurityConfig.java             ✨ NEW - Security config
│   │   │
│   │   └── resources/
│   │       └── application.properties                  ✅ Updated with JWT config
│   │
│   └── test/
│       └── java/
│           └── com/example/inventory/
│               └── InventoryManagementApplicationTests.java (Original)
│
└── target/                                    (Build artifacts)
    ├── classes/
    ├── maven-status/
    └── test-classes/
```

## Legend
- ✅ Updated files
- ✨ NEW - Newly created files/packages
- (Original) - Existing files unchanged

## New Packages Added
1. `security/` - JWT token handling and authentication filter
2. `config/` - Spring Security and application configuration

## Key Files for Authentication
| File | Purpose |
|------|---------|
| `User.java` | User entity stored in database |
| `UserRepository.java` | Database queries for users |
| `AuthService.java` | Business logic for login/register |
| `AuthController.java` | REST API endpoints |
| `JwtTokenProvider.java` | Token generation & validation |
| `JwtAuthenticationFilter.java` | Request interceptor for token validation |
| `SecurityConfig.java` | Spring Security configuration |

---

## Running the Application

### 1. Build
```bash
mvn clean install
```

### 2. Run
```bash
mvn spring-boot:run
```

### 3. Test Authentication
- Register: `POST http://localhost:8080/api/auth/register`
- Login: `POST http://localhost:8080/api/auth/login`
- Access protected endpoints with token in header

---

## 📚 Related Documentation
- See `API_AUTH_DOCUMENTATION.md` for complete API details
- See `IMPLEMENTATION_SUMMARY.md` for implementation details
- See `README.md` for project overview
