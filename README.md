# 🎭 Playwright Java CI/CD Automation Testing

Proyek contoh automation testing berbasis **Java + Playwright + JUnit 5 + Maven** dengan integrasi **GitHub Actions CI/CD Pipeline**.

---

## 🛠️ Tech Stack
- **Language**: Java 17
- **Testing Framework**: JUnit 5
- **Automation Tool**: Microsoft Playwright for Java
- **Build Tool**: Apache Maven
- **CI/CD Platform**: GitHub Actions

---

## 🚀 Cara Menjalankan Secara Lokal

### 1. Prasyarat
- Java JDK 17 atau yang lebih baru
- Maven 3.8+

### 2. Jalankan Test
```bash
# Menjalankan semua test secara headless (seperti di CI)
mvn test

# Menjalankan test dengan browser terlihat (headed mode untuk debugging)
mvn test -Dheadless=false
```

### 3. Menginspeksi Playwright Trace
Playwright otomatis merekam rekaman langkah, screenshot, dan network activity ke folder `target/playwright-traces/`.

Buka file trace:
1. Akses web [https://trace.playwright.dev](https://trace.playwright.dev)
2. Drag & drop file `.zip` dari `target/playwright-traces/`.

---

## 🔄 Bagaimana CI/CD Bekerja?

Pipeline didefinisikan pada file [`.github/workflows/playwright-tests.yml`](.github/workflows/playwright-tests.yml):

```text
[ Developer Push / Buat PR ]
             │
             ▼
[ GitHub Actions Runner (Ubuntu) ]
             │
             ├─ 1. Checkout Kode
             ├─ 2. Setup JDK 17 & Cache Maven dependencies
             ├─ 3. Install Browser (Chromium) & OS library (`playwright install --with-deps`)
             ├─ 4. Eksekusi `mvn test -Dheadless=true`
             │
             ▼
    [ Apakah Ada Test Gagal? ]
       ├── YA  ──> ❌ Status PR "RED" (Gagal). Upload Trace & Screenshot untuk investigasi.
       └── TDK ──> ✅ Status PR "GREEN" (Lolos). Kode aman di-merge ke branch main.
```
