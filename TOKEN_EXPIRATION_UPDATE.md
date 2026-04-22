# JWT Token Expiration & Logout Enhancement

## 📋 Updates Made

### 1. **Token Expiration Tracking**
- ✅ Added `expiresAt` and `issuedAt` fields to `LoginResponse` DTO
- ✅ Token expiration time now displayed in response with format: `yyyy-MM-dd HH:mm:ss`
- ✅ Client receives clear information about when token expires
- ✅ Both **login** and **register** endpoints return expiration time

**Sample Response:**
```json
{
  "status": "success",
  "message": "Login successful",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "username": "john_doe",
    "email": "john@example.com",
    "fullName": "John Doe",
    "tokenType": "Bearer",
    "expiresIn": 86400000,
    "expiresAt": "2026-04-24 14:30:45",
    "issuedAt": "2026-04-23 14:30:45"
  }
}
```

---

### 2. **Token Blacklist System**
- ✅ Created new entity: `TokenBlacklist` to track logged-out tokens
- ✅ Created repository: `TokenBlacklistRepository` for database operations
- ✅ Created service: `TokenBlacklistService` to manage token blacklist
- ✅ Tokens are added to blacklist when user logs out
- ✅ Filter checks blacklist before allowing requests

**Token Blacklist Database Schema:**
```sql
CREATE TABLE token_blacklist (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  token LONGTEXT NOT NULL,
  username VARCHAR(255) NOT NULL,
  expires_at TIMESTAMP NOT NULL,
  blacklisted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---

### 3. **Enhanced Logout Endpoint**
- ✅ Logout now **actually destroys the token** by adding it to blacklist
- ✅ Requires valid Authorization header with token
- ✅ Token becomes invalid immediately after logout
- ✅ Even if client stores token, it won't be accepted after logout

**Logout Request:**
```bash
POST /api/auth/logout
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Logout Response:**
```json
{
  "status": "success",
  "message": "Logout successful. Token has been invalidated."
}
```

---

### 4. **JWT Token Provider Enhancement**
- ✅ `generateToken()` now returns `Map<String, Object>` with:
  - `token` - The JWT token string
  - `issuedAt` - When token was created (LocalDateTime)
  - `expiresAt` - When token expires (LocalDateTime)
  - `expiresIn` - Expiration time in milliseconds

- ✅ New methods:
  - `getExpirationLocalDateTimeFromToken()` - Returns expiration as LocalDateTime
  - `getIssuedAtLocalDateTimeFromToken()` - Returns issued time as LocalDateTime
  - Helper method: `convertToLocalDateTime()` for Date to LocalDateTime conversion

---

### 5. **JWT Authentication Filter Enhancement**
- ✅ Filter now checks if token is **blacklisted**
- ✅ Blacklisted tokens are rejected even if they're valid JWT
- ✅ Prevents use of tokens after logout

---

### 6. **Automatic Cleanup Task**
- ✅ Added `@EnableScheduling` to main application class
- ✅ `TokenBlacklistService` runs cleanup task daily at 2 AM
- ✅ Automatically removes expired tokens from blacklist table
- ✅ Reduces database bloat over time

**Cleanup Schedule:**
```
Runs every day at 2:00 AM
- Deletes tokens where expiresAt < NOW()
```

---

## 🔄 How Logout Works Now

### Before (Old Way)
1. User clicks logout
2. Client-side removes token from storage
3. Server doesn't know about logout ❌
4. If attacker has token, can still use it

### After (New Way)
1. User clicks logout
2. Client sends logout request with token
3. Server adds token to blacklist ✅
4. Token is marked as destroyed at specific time
5. Token cannot be used even if attacker has it ✅
6. Expired blacklist entries cleaned up automatically ✅

---

## 📝 Files Modified/Created

### New Files
- `TokenBlacklist.java` - Entity for storing blacklisted tokens
- `TokenBlacklistRepository.java` - Database access layer
- `TokenBlacklistService.java` - Business logic for token blacklist

### Modified Files
- `LoginResponse.java` - Added expiresAt and issuedAt fields
- `JwtTokenProvider.java` - Enhanced token generation with datetime info
- `AuthService.java` - Uses new token info from JwtTokenProvider
- `JwtAuthenticationFilter.java` - Checks token blacklist
- `AuthController.java` - Logout now blacklists token
- `InventoryManagementApplication.java` - Added @EnableScheduling

---

## ✅ Testing Logout Flow

### 1. Register/Login to get token
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "password123"
  }'
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "expiresAt": "2026-04-24 14:30:45"
}
```

### 2. Use token to access protected endpoint (should work)
```bash
curl -X GET http://localhost:8080/api/products \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

Response: ✅ **200 OK** (works)

### 3. Logout to destroy token
```bash
curl -X POST http://localhost:8080/api/auth/logout \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

Response:
```json
{
  "status": "success",
  "message": "Logout successful. Token has been invalidated."
}
```

### 4. Try to use token again (should fail)
```bash
curl -X GET http://localhost:8080/api/products \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

Response: ❌ **Unauthorized** (token is blacklisted)

---

## 🔒 Security Improvements

✅ **Tokens are actually destroyed on logout**  
✅ **Expiration times clearly shown to client**  
✅ **Both login and register show token validity**  
✅ **Blacklisted tokens cannot be reused**  
✅ **Automatic cleanup prevents database bloat**  
✅ **Logout is now mandatory for security**  

---

## ⚙️ Configuration

No additional configuration needed. System works with existing `application.properties`:

```properties
jwt.secret=...
jwt.expiration=86400000  # 24 hours
```

---

## 📊 Database Changes

New table created automatically:

```sql
CREATE TABLE token_blacklist (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  token LONGTEXT NOT NULL UNIQUE,
  username VARCHAR(255) NOT NULL,
  expires_at TIMESTAMP NOT NULL,
  blacklisted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_expires_at (expires_at)
);
```

---

## 🚀 Next Enhancements (Optional)

1. **Redis Token Cache** - Use Redis for faster blacklist lookups
2. **Token Refresh** - Implement refresh tokens for longer sessions
3. **Rate Limiting** - Prevent brute force logout attempts
4. **Audit Logging** - Log all login/logout/token activities
5. **Multiple Device Logout** - Logout from all devices
6. **Session Management** - Track active sessions per user

---

**Updated:** April 23, 2026  
**Version:** 2.0.0
