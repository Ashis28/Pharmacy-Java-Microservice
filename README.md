# Online Pharmacy & Medicine Delivery System

> A secure, microservices-based pharmacy platform built with Java Spring Boot — enabling customers to browse medicines, upload prescriptions, place orders, and track deliveries, while giving admins full control over inventory, order lifecycle, and reporting.

---

##  Project Overview

| Field | Details |
|---|---|
| **Domain** | Healthcare Commerce / Online Pharmacy |
| **Team** | Team 9 — 4 members |
| **Stack** | Java 17 · Spring Boot 3.x · Spring Cloud · MySQL · JWT |
| **Architecture** | Microservices (4 services + Gateway + Eureka) |
| **Focus** | Backend REST APIs only |

---

##  Architecture

```
Client (Postman / Frontend)
        │
        ▼
┌─────────────────────────────┐
│   Spring Cloud Gateway      │  :8080
│   JWT Filter · lb:// routing│
└─────────────┬───────────────┘
              │ (service discovery)
              ▼
┌─────────────────────────────┐
│      Eureka Server          │  :8761
│   Service Registry          │
└─────────────────────────────┘
              │
    ┌─────────┼──────────┬──────────┐
    ▼         ▼          ▼          ▼
┌────────┐ ┌────────┐ ┌────────┐ ┌────────┐
│Identity│ │Catalog │ │ Order  │ │ Admin  │
│  :8081 │ │  :8082 │ │  :8083 │ │  :8084 │
└───┬────┘ └───┬────┘ └───┬────┘ └───┬────┘
    │          │           │          │
  auth_db  catalog_db  orders_db  admin_db
```

---

##  Microservices Breakdown

### 1. Identity / Auth Service — `:8081`
Handles user registration, login, JWT generation, and role-based access control.

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/auth/signup` | Register a new customer |
| `POST` | `/api/auth/login` | Login and receive JWT token |

**Key components:** `User`, `Address`, `JwtUtil`, `JwtAuthFilter`, `SecurityConfig`

---

### 2. Catalog & Prescription Service — `:8082`
Manages the medicine catalog, categories, inventory batches, and prescription file uploads.

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/catalog/medicines` | List medicines (search, filter, sort, paginate) |
| `GET` | `/api/catalog/medicines/{id}` | Get medicine details |
| `GET` | `/api/catalog/categories` | List all categories |
| `POST` | `/api/catalog/prescriptions/upload` | Upload prescription (PDF/JPG/PNG) |

**Key components:** `Medicine`, `Category`, `Inventory`, `Prescription`, `FileStorageService`

---

### 3. Order & Delivery Service — `:8083`
Manages the full cart → checkout → payment → order lifecycle.

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/orders/cart` | View current cart |
| `POST` | `/api/orders/cart/add` | Add item to cart |
| `DELETE` | `/api/orders/cart/{itemId}` | Remove cart item |
| `POST` | `/api/orders/checkout/start` | Start checkout, create order |
| `POST` | `/api/orders/payments/initiate` | Initiate payment (stub) |
| `GET` | `/api/orders` | Order history |
| `GET` | `/api/orders/{id}` | Order details |

**Key components:** `CartItem`, `Order`, `OrderItem`, `Payment`, `OrderStatus` (enum)

---

### 4. Admin / Reporting Service — `:8084`
Admin-protected operations — catalog management, prescription queue, order status updates, and reports.

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/admin/dashboard` | KPIs: orders, stock, prescriptions |
| `POST` | `/api/admin/medicines` | Add new medicine |
| `PUT` | `/api/admin/medicines/{id}` | Update medicine |
| `DELETE` | `/api/admin/medicines/{id}` | Remove medicine |
| `PUT` | `/api/admin/orders/{id}/status` | Update order status |
| `GET` | `/api/admin/prescriptions/pending` | View pending prescriptions |
| `PUT` | `/api/admin/prescriptions/{id}/approve` | Approve prescription |
| `PUT` | `/api/admin/prescriptions/{id}/reject` | Reject prescription |
| `GET` | `/api/admin/reports/sales` | Sales report |
| `GET` | `/api/admin/reports/inventory` | Inventory report |

> All admin endpoints require `@PreAuthorize("hasRole('ADMIN')")`.

---

## 🗄️ Database Design

Nine core entities across four isolated schemas:

```
Users ──< Addresses
Users ──< Orders ──< OrderItems >── Medicines
Users ──< Prescriptions >── Medicines
Orders >── Payments
Orders >── Addresses
Medicines >── Categories
Medicines ──< Inventory (batches)
```

**Entities:** `users`, `addresses`, `categories`, `medicines`, `inventory`, `prescriptions`, `orders`, `order_items`, `payments`

---

## 🔄 Order Status Lifecycle

```
DRAFT_CART
    → CHECKOUT_STARTED
        → PRESCRIPTION_PENDING  (if Rx required)
            → PRESCRIPTION_APPROVED / PRESCRIPTION_REJECTED
        → PAYMENT_PENDING
            → PAID
                → PACKED
                    → OUT_FOR_DELIVERY
                        → DELIVERED

Branch exits:
    → CUSTOMER_CANCELLED
    → ADMIN_CANCELLED
    → PAYMENT_FAILED
    → RETURN_REQUESTED → REFUND_INITIATED → REFUND_COMPLETED
```

---

##  Security

- **JWT Bearer Authentication** via `io.jsonwebtoken` (JJWT 0.12.3)
- `JwtAuthFilter` (`OncePerRequestFilter`) validates every secured request
- Roles: `CUSTOMER`, `ADMIN`
- Admin routes protected with `@PreAuthorize("hasRole('ADMIN')")`
- Gateway-level JWT filter rejects invalid tokens before they reach any service

---

## Project Structure

```
pharmacy-backend/
├── eureka-server/          # Service registry — port 8761
├── api-gateway/            # Spring Cloud Gateway — port 8080
├── identity-service/       # Auth + Users — port 8081
├── catalog-service/        # Medicines + Prescriptions — port 8082
├── order-service/          # Cart + Orders + Payments — port 8083
└── admin-service/          # Admin ops + Reports — port 8084
```

Each service follows this internal package structure:
```
com.pharmacy.{service}/
├── controller/
├── service/
├── repository/
├── entity/
├── dto/
├── security/
├── exception/
└── config/
```

---

##  Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.x |
| API Gateway | Spring Cloud Gateway |
| Service Discovery | Netflix Eureka |
| Security | Spring Security + JJWT 0.12.3 |
| Database | MySQL 8.x + Spring Data JPA |
| Migrations | Hibernate `ddl-auto=update` (dev) / Flyway (prod) |
| API Docs | springdoc-openapi (Swagger UI) |
| Testing | JUnit 5 + Mockito + JaCoCo (≥90% coverage target) |
| Build | Maven |
| Tools | Postman · IntelliJ IDEA · MySQL Workbench |

---

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8.x running locally
- Git

### 1. Clone the repository
```bash
git clone https://github.com/your-org/pharmacy-backend.git
cd pharmacy-backend
```

### 2. Configure each service's `application.yml`
In each service, set your MySQL credentials:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/{service}_db
    username: root
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
```

### 3. Create the four MySQL databases
```sql
CREATE DATABASE auth_db;
CREATE DATABASE catalog_db;
CREATE DATABASE orders_db;
CREATE DATABASE admin_db;
```

### 4. Start services in this order
```bash
# Terminal 1
cd eureka-server && mvn spring-boot:run

# Terminal 2
cd identity-service && mvn spring-boot:run

# Terminal 3
cd catalog-service && mvn spring-boot:run

# Terminal 4
cd order-service && mvn spring-boot:run

# Terminal 5
cd admin-service && mvn spring-boot:run

# Terminal 6 — gateway last
cd api-gateway && mvn spring-boot:run
```

### 5. Verify everything is running
| URL | What you should see |
|---|---|
| `http://localhost:8761` | Eureka dashboard — 5 registered instances |
| `http://localhost:8080/api/catalog/medicines` | Medicine list (empty until seeded) |
| `http://localhost:8081/swagger-ui.html` | Identity service Swagger docs |

---

##  Testing

```bash
# Run all tests with coverage report
mvn clean verify

# Coverage report location
target/site/jacoco/index.html
```

**Test focus areas:**
- Auth: signup, login, invalid credentials, JWT expiry
- Prescription: valid/invalid file upload, status transitions
- Cart: add Rx medicine without prescription (should block)
- Checkout: full stepper flow with approved prescription
- Admin: order status update transitions, prescription approval

---

## 👥 Team & Ownership

| Member | Service Owned | Responsibility |
|---|---|---|
| Person A | Identity Service + Eureka | User auth, JWT, security config, gateway filter |
| Person B | Catalog Service | Medicines, categories, inventory, prescription upload |
| Person C | Order Service | Cart, checkout, payment stub, order lifecycle |
| Person D | Admin Service | Dashboard, reports, Rx queue, order status management |

---

## Key Dependencies (`pom.xml`)

```xml
<!-- Spring Boot -->
<dependency>spring-boot-starter-web</dependency>
<dependency>spring-boot-starter-security</dependency>
<dependency>spring-boot-starter-data-jpa</dependency>

<!-- Spring Cloud -->
<dependency>spring-cloud-starter-gateway</dependency>
<dependency>spring-cloud-starter-netflix-eureka-server</dependency>
<dependency>spring-cloud-starter-netflix-eureka-client</dependency>

<!-- Database -->
<dependency>mysql-connector-j</dependency>

<!-- JWT -->
<dependency>jjwt-api (0.12.3)</dependency>
<dependency>jjwt-impl (0.12.3)</dependency>
<dependency>jjwt-jackson (0.12.3)</dependency>

<!-- Docs & Testing -->
<dependency>springdoc-openapi-starter-webmvc-ui</dependency>
<dependency>spring-boot-starter-test</dependency>
```

---

##  Future Enhancements

- [ ] Kafka event streaming — inventory auto-decrement on order placement
- [ ] Email / SMS notifications via a 5th Notification Service
- [ ] Delivery Agent role with live GPS tracking
- [ ] Refill reminders via `@Scheduled` jobs
- [ ] Refresh token support (`POST /api/auth/refresh`)
- [ ] Redis-backed rate limiting on Gateway
- [ ] Soft deletes with `@Where(clause = "is_deleted = false")`
- [ ] Coupon / discount engine at checkout
- [ ] OpenTelemetry distributed tracing across all services
- [ ] Docker Compose for one-command local setup

---

##License

This project is developed as part of an academic curriculum. All rights reserved by Team 9.
