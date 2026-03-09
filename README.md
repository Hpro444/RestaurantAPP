# 🍽️ Restaurant Reservation System

A full-stack **microservices-based** restaurant reservation platform built with Spring Boot and React. The system
supports three user roles — **Customer**, **Manager**, and **Admin** — each with dedicated workflows for managing
restaurants, tables, appointments, and reservations.

---

## 📐 Architecture Overview

The application follows a **microservices architecture** with the following components:

```
┌─────────────────────────────────────────────────┐
│                   React Frontend                │
│              (Vite + React Router)              │
│               http://localhost:5173             │
└─────────────────────┬───────────────────────────┘
                      │ HTTP requests
┌─────────────────────▼───────────────────────────┐
│               API Gateway (Port 8084)           │
│          Spring Cloud Gateway + Load Balancer   │
└──────┬──────────────┬─────────────────┬─────────┘
       │              │                 │
┌──────▼──────┐ ┌─────▼──────┐ ┌───────▼────────┐
│ User Service│ │ Reservation│ │  Notification  │
│  Port 8083  │ │  Service   │ │    Service     │
│             │ │ Port 8082  │ │   Port 8081    │
└─────────────┘ └─────┬──────┘ └───────▲────────┘
                      │                 │
                      └─────────────────┘
                         RabbitMQ Events
┌─────────────────────────────────────────────────┐
│           Eureka Service Registry (Port 8761)   │
└─────────────────────────────────────────────────┘
```

---

## 🧩 Services

### 1. Eureka Service Registry (`port: 8761`)

Service discovery server — all microservices register here, enabling dynamic load balancing and service lookup.

### 2. API Gateway (`port: 8084`)

- Single entry point for all client requests
- Routes traffic to appropriate microservices via Eureka discovery
- Handles **CORS** configuration for the frontend
- Built with **Spring Cloud Gateway**

### 3. User Service (`port: 8083`)

Manages user accounts and authentication.

- **Roles:** `Customer`, `Manager`, `Admin`
- JWT-based authentication
- Account activation flow (via email)
- RabbitMQ integration for sending activation/notification events
- In-memory **H2** database

### 4. Reservation Service (`port: 8082`)

Core domain service for restaurant and reservation management.

- Restaurant CRUD (create, edit, delete)
- Table and appointment slot management
- Reservation creation and management
- Scheduled tasks (auto-cancellation, etc.)
- JWT validation for secured endpoints
- RabbitMQ integration for reservation events
- In-memory **H2** database

### 5. Notification Service (`port: 8081`)

Handles all email notifications.

- Listens to RabbitMQ events from other services
- Sends transactional emails via **Gmail SMTP**
- Reservation confirmations, cancellations, and account activation emails
- JWT validation for secured endpoints

### 6. Frontend (`port: 5173`)

Single Page Application built with **React + Vite**.

**Pages by role:**

| Role     | Pages                                                                                          |
|----------|------------------------------------------------------------------------------------------------|
| Guest    | Login, Register, Activate Account                                                              |
| Customer | Dashboard, Browse Restaurants, View Tables, Make/View Reservations                             |
| Manager  | Dashboard, Manage Restaurants, Add/Edit Tables, Add Appointments, View Restaurant Reservations |
| Admin    | Dashboard, View All Restaurants, View All Reservations, Notifications                          |

---

## 🛠️ Tech Stack

### Backend

| Technology            | Version  | Purpose               |
|-----------------------|----------|-----------------------|
| Java                  | 21       | Core language         |
| Spring Boot           | 3.4.1    | Application framework |
| Spring Cloud          | 2024.0.0 | Microservices toolkit |
| Spring Cloud Gateway  | —        | API Gateway           |
| Netflix Eureka        | —        | Service discovery     |
| Spring Data JPA       | —        | ORM / persistence     |
| H2 Database           | —        | In-memory database    |
| RabbitMQ (AMQP)       | —        | Async messaging       |
| JWT (jjwt)            | 0.11.5   | Authentication tokens |
| Lombok                | 1.18.34  | Boilerplate reduction |
| JavaMail (javax.mail) | 1.6.2    | Email sending         |
| Maven                 | —        | Build tool            |

### Frontend

| Technology       | Version | Purpose                 |
|------------------|---------|-------------------------|
| React            | 18.3.x  | UI framework            |
| Vite             | 6.x     | Build tool & dev server |
| React Router DOM | 7.x     | Client-side routing     |
| Axios            | 1.7.x   | HTTP client             |
| jwt-decode       | 4.x     | JWT parsing on client   |

---

## ⚙️ Prerequisites

Make sure you have the following installed:

- **Java 21+**
- **Maven 3.8+**
- **Node.js 18+** and **npm**
- **RabbitMQ** running on `localhost:5672` (default credentials: `guest/guest`)

---

## 🚀 Getting Started

### 1. Start RabbitMQ

```bash
# Using Docker (recommended)
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:management
```

Or install and start RabbitMQ locally. The management UI will be available at `http://localhost:15672`.

### 2. Start the Eureka Service Registry

```bash
cd EurekaService
./mvnw spring-boot:run
```

> Wait until Eureka is fully started before launching other services.

### 3. Start the Backend Microservices

Start each service in a separate terminal:

```bash
# User Service
cd UserService
./mvnw spring-boot:run
```

```bash
# Reservation Service
cd ReservationService
./mvnw spring-boot:run
```

```bash
# Notification Service
cd NotificationService
./mvnw spring-boot:run
```

```bash
# API Gateway
cd Api_gateway
./mvnw spring-boot:run
```

### 4. Start the Frontend

```bash
cd frontend
npm install
npm run dev
```

The app will be available at **http://localhost:5173**.

---

## 🔌 Service Ports Summary

| Service                 | Port    |
|-------------------------|---------|
| Eureka Service Registry | `8761`  |
| Notification Service    | `8081`  |
| Reservation Service     | `8082`  |
| User Service            | `8083`  |
| API Gateway             | `8084`  |
| Frontend (Vite Dev)     | `5173`  |
| RabbitMQ AMQP           | `5672`  |
| RabbitMQ Management UI  | `15672` |

---

## 🔐 Authentication

The application uses **JWT (JSON Web Tokens)** for stateless authentication.

1. User logs in via the **User Service**
2. A signed JWT is returned to the frontend
3. The token is attached to all subsequent requests as a Bearer token
4. Each service independently validates the JWT using a shared secret

---

## 📧 Email Configuration

The Notification Service sends emails via **Gmail SMTP**. To configure a different email account, update
`NotificationService/src/main/resources/email.properties`:

```properties
mail.smtp.user=your-email@gmail.com
mail.smtp.password=your-app-password
mail.smtp.host=smtp.gmail.com
mail.smtp.port=587
mail.smtp.auth=true
mail.smtp.starttls.enable=true
```

> **Note:** Use a Gmail **App Password** (not your regular password). Enable 2FA on your Google account first, then
> generate an App Password under Google Account → Security.

---

## 🗄️ Database

All services use an **in-memory H2 database** that resets on every restart. H2 consoles are accessible (when services
are running) at:

- User Service: `http://localhost:8083/h2-console` — JDBC URL: `jdbc:h2:mem:testdb`
- Reservation Service: `http://localhost:8082/h2-console` — JDBC URL: `jdbc:h2:mem:testdb`

---

## 📁 Project Structure

```
RestaurantAPP/
├── Api_gateway/          # Spring Cloud Gateway
├── EurekaService/        # Netflix Eureka Server
├── UserService/          # User management & auth
├── ReservationService/   # Restaurants, tables, reservations
├── NotificationService/  # Email notifications
└── frontend/             # React + Vite SPA
    └── src/
        └── pages/        # All page components
```

---

## 👤 User Roles

| Role         | Capabilities                                                                                       |
|--------------|----------------------------------------------------------------------------------------------------|
| **Customer** | Browse restaurants, view available tables, make and cancel reservations                            |
| **Manager**  | Add/edit/delete restaurants, manage tables and appointment slots, view reservations per restaurant |
| **Admin**    | View all restaurants, all reservations system-wide, manage notifications                           |

