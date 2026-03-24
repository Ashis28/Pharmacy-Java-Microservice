sign url :http://localhost:9091/api/auth/signup
{
    "name": "John Doe",
  "email": "john@pharmacy.com",
  "password": "secret123",
  "roles": ["CUSTOMER"],
  "address": {
    "street": "12 Main Street",
    "city": "Chennai",
    "state": "Tamil Nadu",
    "pinCode": 600001
  }
}

login url : http://localhost:9091/api/auth/login
{
  "email": "john@pharmacy.com",
  "password": "secret123"
}
output :
{
    "token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqb2huQHBoYXJtYWN5LmNvbSIsImlhdCI6MTc3NDM0OTE0MiwiZXhwIjoxNzc0NDM1NTQyfQ.vEse3_HuFjawt8yFAQopR4QQdb1MZcIz5CdUhh5Ioe8CrLPsMgJRYrWZmg9j_24l",
    "name": "John Doe",
    "roles": [
        "CUSTOMER"
    ]
}

how to run with other microservice

the token is already there
now step 2 :
🔹 STEP 2: Send Token in Every Request

Now when calling ANY service:

GET /api/catalog/medicines
Authorization: Bearer <token>

👉 This is the connection point between Auth and all services

🔹 STEP 3: Add Spring Security in ALL SERVICES

👉 In:

Catalog Service
Order/Cart Service
Payment Service
Admin Service

Add dependency:

<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>

step4 :

JWT filter should be there in each service

You need:
JWT Utility (same secret key as auth service)
Filter class
Security config

step 5 :

there is a secret key in auth-service my properties.yml file that should be used in each service

step6:
Inside filter:

String email = jwtUtil.extractUsername(token);

👉 Now your service knows:

Who is calling
What role

🔹 STEP 8: Use API Gateway (BEST PRACTICE 🔥)

From your PRD:
👉 You are using Spring Cloud Gateway

Flow becomes:
Client
   ↓
Gateway (validates JWT)
   ↓
Catalog / Order / Payment

Should token be passed?

👉 YES (recommended)

Forward token:

headers.set("Authorization", token);




