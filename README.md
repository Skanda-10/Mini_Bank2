# 🏦 Mini-Bank Microservices Architecture (Mini-Bank v2)

## 📝 Overview

This project implements a fully functional, event-driven banking application using a **Microservices architecture**. It demonstrates robust service discovery, client-side load balancing, and asynchronous messaging to ensure reliable, high-volume transaction processing across distributed services.

## 🚀 Key Technologies & Stack

The application is built using **Java** and the **Spring Boot** framework, leveraging the following core components:

* **Core Framework:** **Spring Boot** (Microservices implementation)
* **Language:** **Java**
* [cite_start]**Service Discovery:** **Spring Cloud Eureka** Server & Client for dynamic service registration and lookup[cite: 2].
* **Load Balancing:** **Spring Cloud Ribbon** (via `@LoadBalanced RestTemplate`) for client-side load balancing across service instances.
* **Asynchronous Messaging:** **RabbitMQ** is used to decouple the Account Service from the downstream Transaction and Ledger Services, ensuring eventual consistency in transactions.
* **Database:** **H2 Database** (In-memory/File-based) for quick setup and data persistence in each service.
* **API Tooling:** **Postman** was used for testing and demonstrating the full transaction flow.

---

## 🏗️ Project Structure (Microservices)

The application is split into four primary services:

| Service Name | Base URL (Default) | Functionality |
| :--- | :--- | :--- |
| **Account-Server** | `http://localhost:8080` | [cite_start]Manages bank accounts and initiates transactions (Deposit/Withdrawal)[cite: 34, 37, 55]. Utilizes `@LoadBalanced` `RestTemplate`. |
| **Transaction-Server** | `http://localhost:8082` | [cite_start]Processes and records transaction requests (Internal API)[cite: 58, 61]. |
| **Ledger-Server** | `http://localhost:8090` | [cite_start]Responsible for ledger entries and provides auditing/history access[cite: 78, 81, 96, 100]. |
| **eureka-server** | `http://localhost:8761` | Centralized registry for all microservices. |
| **User Service** | `http://localhost:8080` | [cite_start]Manages user profiles (CRUD operations)[cite: 7]. |

---

## ▶️ Demo Video

The `MiniBank-Demo.mp4` video demonstrates the full transaction lifecycle, from service startup and Eureka registration to user creation, transaction initiation, and final database verification (User, Transaction, and Ledger tables).

### Video Playback (Embedded in README)

To play the video directly in the README, the native HTML `<video>` tag is used.

<p align="center">
  <video src="MiniBank-Demo.mp4" controls="controls" muted width="80%">
    Your browser does not support the video tag.
  </video>
</p>

---

## ⚙️ Setup and Run Instructions

### Prerequisites

1.  **Java:** JDK 17 or compatible version.
2.  **Build Tool:** Apache Maven.
3.  **Messaging Server:** **RabbitMQ** must be installed and running (default port `5672`).

### Startup Sequence

All services must be started in a specific order:

1.  **Start RabbitMQ** (Externally).
2.  **Start the Eureka Server (`eureka-server`)**: This is the service discovery authority.
3.  **Start all other services**: Launch the `Account-Server`, `Transaction-Server`, and `Ledger-Server` applications.

---

## 📊 API Workflow Example

A typical flow for creating a user, opening an account, and performing a deposit:

1.  [cite_start]**Create a User** [cite: 104]
    * `POST http://localhost:8080/users`
    * [cite_start]Body: `{"name": "Jane Doe", "email": "jane.doe@example.com"}` [cite: 104]
2.  [cite_start]**Create an Account** [cite: 105]
    * `POST http://localhost:8080/accounts/user/{userId}`
    * [cite_start]Body: `{"balance": 1000.00}` [cite: 105]
3.  [cite_start]**Perform a Deposit** [cite: 106]
    * [cite_start]`POST http://localhost:8080/accounts/{accountId}/transact?amount=500.00&type=deposit` [cite: 106]
4.  [cite_start]**Verify Transaction** [cite: 107]
    * [cite_start]`GET http://localhost:8082/transactions/user/{userId}` [cite: 107]

*(Refer to the **Full API Documentation(Mini-Bank v2).docx** file for a complete list of endpoints and request bodies.)*