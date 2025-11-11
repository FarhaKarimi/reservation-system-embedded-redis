

---

````markdown
# Project: Reservation System

## 1. توضیحات کلی

این پروژه یک سیستم رزرو منابع است که کاربران می‌توانند منابعی مانند اتاق‌ها، سالن‌ها یا تجهیزات را رزرو کنند.  
پروژه از معماری 3 لایه‌ای (Layered Architecture) استفاده می‌کند:

- **Controller Layer**: مدیریت درخواست‌های HTTP و پاسخ‌دهی به کلاینت  
- **Service Layer**: منطق کسب‌وکار، اعتبارسنجی، الگوریتم پیشنهاد منابع  
- **Repository Layer**: دسترسی به دیتابیس با Spring Data JPA  

**امکانات اصلی:**

- ثبت‌نام و ورود کاربران با JWT Authentication  
- Role-Based Access Control: `ADMIN`, `MANAGER`, `USER`  
- مدیریت منابع و رزروها  
- مشاهده رزروها و منابع  
- الگوریتم پیشنهادی ساده بر اساس رزروهای مشابه کاربران  
- کش با Embedded Redis  
- لاگ و مانیتورینگ با Spring Boot Actuator  
- مستندسازی API با Swagger  

---

## 2. نیازمندی‌ها

- Java 17+  
- Maven 3.8+  
- PostgreSQL 14+  
- Spring Boot 3.2+  
- Embedded Redis (بدون نیاز به نصب جداگانه)  

---

## 3. نصب و اجرا

### 3.1 دیتابیس

1. PostgreSQL را نصب و اجرا کنید.  
2. دیتابیس ایجاد کنید:

```sql
CREATE DATABASE reservationdb;
````

3. در `application.properties` تنظیمات دیتابیس را وارد کنید:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/reservationdb
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
```

### 3.2 اجرای پروژه

1. پروژه را با Maven Build کنید:

```bash
mvn clean install
```

2. پروژه را اجرا کنید:

```bash
mvn spring-boot:run
```

3. Embedded Redis به صورت خودکار روی یک پورت آزاد اجرا می‌شود.

---

## 4. معماری پروژه

```
src/main/java/com/example/reservation/
│
├─ controller/      # REST Controller ها
├─ service/         # منطق کسب‌وکار
├─ repository/      # Repository برای دیتابیس
├─ model/           # Entity ها
├─ dto/             # کلاس‌های انتقال داده
├─ security/        # JWT, SecurityConfig
└─ config/          # Redis و سایر Config ها
```

---

## 5. امنیت

* JWT Authentication: بدون Session، توکن در Header `Authorization` ارسال می‌شود
* Roles:

  * `ADMIN`: مدیریت منابع و رزروها
  * `MANAGER`: مدیریت رزروهای منابع خاص
  * `USER`: رزرو منابع، مشاهده رزروها
* Password Encoding: BCrypt

---

## 6. Embedded Redis

* برای کش کردن داده‌های پرکاربرد استفاده می‌شود (مثلاً لیست منابع محبوب).
* روی ویندوز بدون نیاز به نصب جداگانه اجرا می‌شود.

---

## 7. API Endpoints

### 7.1 Authentication

| Method | URL              | Body                                                                                  | Description       |
| ------ | ---------------- | ------------------------------------------------------------------------------------- | ----------------- |
| POST   | /api/auth/signup | `{ "username":"Ali", "email":"ali@example.com", "password":"123456", "role":"USER" }` | ثبت‌نام کاربر     |
| POST   | /api/auth/login  | `{ "email":"ali@example.com", "password":"123456" }`                                  | ورود و دریافت JWT |

### 7.2 Resources

| Method | URL            | Header                              | Body                                                            | Description               |
| ------ | -------------- | ----------------------------------- | --------------------------------------------------------------- | ------------------------- |
| GET    | /api/resources | `Authorization: Bearer <JWT>`       | –                                                               | مشاهده منابع (USER/Admin) |
| POST   | /api/resources | `Authorization: Bearer <Admin JWT>` | `{ "name":"اتاق A", "description":"...", "location":"طبقه ۲" }` | ایجاد منبع (Admin فقط)    |

### 7.3 Reservations

| Method | URL               | Header                        | Body                                                                                     | Description          |
| ------ | ----------------- | ----------------------------- | ---------------------------------------------------------------------------------------- | -------------------- |
| GET    | /api/reservations | `Authorization: Bearer <JWT>` | –                                                                                        | مشاهده رزروهای کاربر |
| POST   | /api/reservations | `Authorization: Bearer <JWT>` | `{ "resourceId":1, "startTime":"2025-11-12T09:00:00", "endTime":"2025-11-12T10:00:00" }` | رزرو منبع            |

### 7.4 Ratings (اختیاری)

| Method | URL          | Header                        | Body                            | Description        |
| ------ | ------------ | ----------------------------- | ------------------------------- | ------------------ |
| POST   | /api/ratings | `Authorization: Bearer <JWT>` | `{ "resourceId":1, "score":5 }` | امتیازدهی به منابع |

---

## 8. Postman Usage

1. ابتدا ثبت‌نام و ورود کنید.
2. JWT دریافت شده را در Header همه درخواست‌های محافظت‌شده قرار دهید:

```
Authorization: Bearer <JWT Token>
```

3. Admin برای ایجاد منابع استفاده شود.
4. User برای رزرو منابع و مشاهده رزروها استفاده شود.

---

## 9. Monitoring & Logging

* Spring Boot Actuator: `/actuator/health`, `/actuator/metrics`
* Logging: Logback (قابل تغییر به ELK stack در آینده)

---

## 10. تست و مستندسازی

* Unit Testing: JUnit + Mockito
* Integration Testing: MockMVC
* Swagger/OpenAPI برای مستندات:

```
http://localhost:8080/swagger-ui.html
```

---

## 11. نکات مهم

* همیشه JWT را در Header `Authorization` قرار دهید.
* USER فقط می‌تواند منابع را مشاهده و رزرو کند.
* POST و DELETE منابع فقط توسط Admin مجاز است.
* Embedded Redis خودکار روی پورت آزاد اجرا می‌شود.

````

---

نکات کلیدی برای GitHub:

1. همیشه **کدها را در سه backtick ```** بگذارید.  
2. جدول‌ها باید فاصله‌ها درست باشند و با Pipe `|` ساخته شوند.  
3. خطوط افقی با `---` ساخته می‌شوند.  
4. از ایموجی‌ها خودداری کنید تا در GitHub درست نمایش داده شود.  

---


````
