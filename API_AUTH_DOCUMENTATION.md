# API Authentication Documentation

## Overview
Sistem autentikasi menggunakan JWT (JSON Web Token) untuk keamanan API. Semua request ke endpoint yang dilindungi memerlukan token yang valid di header Authorization.

## Endpoints

### 1. Register (Registrasi Pengguna Baru)
**Endpoint:** `POST /api/auth/register`  
**Authorization:** Tidak diperlukan  
**Content-Type:** `application/json`

**Request Body:**
```json
{
  "username": "user123",
  "email": "user@example.com",
  "password": "password123",
  "fullName": "John Doe"
}
```

**Validasi:**
- Username: minimal 3 karakter
- Email: harus format email yang valid
- Password: minimal 6 karakter
- Full Name: tidak boleh kosong

**Response Success (201):**
```json
{
  "status": "success",
  "message": "Registration successful",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "username": "user123",
    "email": "user@example.com",
    "fullName": "John Doe",
    "tokenType": "Bearer",
    "expiresIn": 86400000,
    "expiresAt": "2026-04-24 14:30:45",
    "issuedAt": "2026-04-23 14:30:45"
  }
}
```

**Response Error (400):**
```json
{
  "status": "error",
  "message": "Username already exists"
}
```

---

### 2. Login (Masuk)
**Endpoint:** `POST /api/auth/login`  
**Authorization:** Tidak diperlukan  
**Content-Type:** `application/json`

**Request Body:**
```json
{
  "username": "user123",
  "password": "password123"
}
```

**Response Success (200):**
```json
{
  "status": "success",
  "message": "Login successful",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "username": "user123",
    "email": "user@example.com",
    "fullName": "John Doe",
    "tokenType": "Bearer",
    "expiresIn": 86400000,
    "expiresAt": "2026-04-24 14:30:45",
    "issuedAt": "2026-04-23 14:30:45"
  }
}
```

**Response Error (401):**
```json
{
  "status": "error",
  "message": "Invalid password"
}
```

---

### 3. Logout (Keluar)
**Endpoint:** `POST /api/auth/logout`  
**Authorization:** Bearer Token (diperlukan)  

**Header:**
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Response (200):**
```json
{
  "status": "success",
  "message": "Logout successful. Token has been invalidated."
}
```

**Note:** 
- Token is added to blacklist after logout
- Token cannot be used anymore, even if stored by client
- Blacklist entries are automatically cleaned up when tokens expire

---

### 4. Validate Token (Validasi Token)
**Endpoint:** `GET /api/auth/validate-token`  
**Authorization:** Bearer Token (diperlukan)  

**Header:**
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Response Success (200):**
```json
{
  "status": "success",
  "message": "Token is valid"
}
```

**Response Error - Token Blacklisted (401):**
```json
{
  "status": "error",
  "message": "Token is blacklisted (logged out)"
}
```

**Response Error - Expired/Invalid (401):**
```json
{
  "status": "error",
  "message": "Token validation failed: ..."
}
```

**Note:** This endpoint checks:
- Token signature and format validity
- Token expiration
- Whether token has been blacklisted (logged out)

---

## Menggunakan Token

### Header Format
Semua request ke endpoint yang dilindungi harus menyertakan token di header Authorization:

```
Authorization: Bearer <token>
```

### Token Expiration Information
Saat login/register, response menyertakan:
- **expiresIn**: Waktu expiration dalam milliseconds (86400000 = 24 jam)
- **expiresAt**: Waktu expire dalam format readable (yyyy-MM-dd HH:mm:ss)
- **issuedAt**: Waktu token dibuat dalam format readable (yyyy-MM-dd HH:mm:ss)

Client harus refresh token sebelum `expiresAt` tercapai atau perlu login ulang.

### Contoh Request dengan Token
```bash
curl -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  http://localhost:8080/api/products
```

---

## Token Expiration & Logout
- **Default Expiration:** 24 jam (86400000 ms)
- Token akan menjadi invalid setelah waktu expiration berakhir
- Saat logout, token ditambahkan ke blacklist dan tidak bisa digunakan lagi
- User perlu login kembali untuk mendapatkan token baru

---

## Error Codes

| Status | Pesan Error | Keterangan |
|--------|-------------|-----------|
| 400 | Bad Request | Request body tidak valid atau field diperlukan kosong |
| 401 | Unauthorized | Username/password salah atau token expired |
| 403 | Forbidden | Token tidak ada di header Authorization |
| 404 | Not Found | User tidak ditemukan |

---

## Security Best Practices

1. **Jangan bagikan token** ke orang lain
2. **Simpan token dengan aman** (localStorage, sessionStorage, atau cookie HttpOnly)
3. **Gunakan HTTPS** saat production
4. **Refresh token** secara berkala
5. **Logout saat selesai** menggunakan aplikasi
6. **Update JWT_SECRET** di environment variable untuk production

---

## Konfigurasi Environment Variables

Tambahkan ke `.env` atau environment:

```
JWT_SECRET=your-very-long-secret-key-min-256-bits
JWT_EXPIRATION=86400000
```

---

## Testing dengan Postman/cURL

### Register
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username":"testuser",
    "email":"test@example.com",
    "password":"test123",
    "fullName":"Test User"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username":"testuser",
    "password":"test123"
  }'
```

### Logout
```bash
curl -X POST http://localhost:8080/api/auth/logout \
  -H "Authorization: Bearer <TOKEN_DARI_LOGIN>"
```

Response:
```json
{
  "status": "success",
  "message": "Logout successful. Token has been invalidated."
}
```

### Akses Protected Endpoint
```bash
curl -X GET http://localhost:8080/api/products \
  -H "Authorization: Bearer <TOKEN_DARI_LOGIN>"
```

### Coba Akses Setelah Logout (akan gagal)
```bash
curl -X GET http://localhost:8080/api/products \
  -H "Authorization: Bearer <TOKEN_YANG_SUDAH_DI_LOGOUT>"
```

Response:
```json
{
  "status": "error",
  "message": "Token is blacklisted (logged out)"
}
```

---

## Database Schema

### Users Table
Table `users` akan dibuat otomatis dengan field:
- `id` (Primary Key, Auto Increment)
- `username` (Unique, Not Null)
- `email` (Unique, Not Null)
- `password` (Not Null, Encrypted dengan BCrypt)
- `full_name` (Not Null)
- `is_active` (Boolean, Default: true)
- `created_at` (Timestamp)
- `updated_at` (Timestamp)

### Token Blacklist Table
Table `token_blacklist` untuk track token yang sudah di-logout:
- `id` (Primary Key, Auto Increment)
- `token` (Unique, LONGTEXT, Not Null)
- `username` (VARCHAR 255, Not Null)
- `expires_at` (Timestamp, Not Null) - Kapan token expire
- `blacklisted_at` (Timestamp, Not Null) - Kapan token di-blacklist (logout)

**Cleanup:** Token blacklist otomatis dihapus setiap hari pukul 2 AM setelah token expire
