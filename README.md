# 📦 sqs-message-processor-api

Kotlin + Spring Boot API with real AWS SQS integration, designed with Clean Code principles, SOLID architecture, comprehensive testing, and production-grade scalability.

---

## 🎯 Objective

This project showcases how to send and receive messages from AWS SQS queues using modern backend practices with Kotlin and Spring Boot.

Ideal for:

- Senior backend developer portfolio
- Real-world message queue simulations
- Asynchronous processing demonstrations

---

## 🛠️ Tech Stack

- **Language:** Kotlin 1.9.25
- **Framework:** Spring Boot 3.5.3
- **Messaging:** AWS SQS (SDK v2)
- **Build Tool:** Maven
- **Testing:** JUnit 5, Mockito Kotlin, Spring Test
- **Validation:** Jakarta Bean Validation (Hibernate Validator)
- **Observability:** SLF4J structured logging

---

## 🧱 Architecture

- **Controller Layer:** Handles HTTP requests and delegates to use cases
- **Application Layer (Use Cases):** Implements business logic
- **Infrastructure Layer (Gateways):** Integrates with AWS SQS (send/receive)
- **DTOs:** Decouples payloads between layers using `MessageRequestDTO`

> Follows Clean Architecture principles with clear separation of concerns.

---

## 🔁 Message Flow

1. **Publishing:**

   - HTTP POST to `/api/v1/messages`
   - Payload is serialized and sent to SQS via `SqsPublisherGateway`

2. **Consuming:**

   - Annotated with `@SqsListener`
   - Received messages are deserialized and processed by `ProcessMessageUseCase`
   - If processing fails, fallback to DLQ can be configured

3. **Error Handling:**

   - Robust `try/catch` blocks with structured logging
   - Handles `null` values, timeouts, and network failures gracefully

---

## 📂 Project Structure

```
sqs-message-processor-api
│
├── src/main/kotlin
│   └── br.com.rpdesenvolve.sqs_message_processor_api
│       ├── config              # AWS SQS configuration
│       ├── entrypoint          # Controllers and DTOs
│       ├── usecase             # Business use cases
│       └── infrastructure
│           └── sqs             # SQS Gateway and Listeners
│
├── src/test/kotlin
│   ├── unit                   # Unit tests
│   └── integration            # Integration tests with Spring Boot
│
├── docker-compose.yml         # (Optional) LocalStack for local SQS testing
├── application.yml            # Application configuration
├── README.md
└── pom.xml                    # Build file with dependencies
```

---

## ✅ How to Run

1. Export your AWS credentials:

```bash
export AWS_ACCESS_KEY_ID=xxxxxx
export AWS_SECRET_ACCESS_KEY=xxxxxx
```

2. (Optional) Start LocalStack:

```bash
docker-compose up -d
```

3. Run the application:

```bash
mvn spring-boot:run
```

4. Test message publishing:

```bash
curl -X POST http://localhost:8080/api/v1/messages \
  -H "Content-Type: application/json" \
  -d '{"payload": "This is a test message sent to the SQS queue through the MessageController."}'
```

---

## 📊 Monitoring & Tools

- Postman
![](/src/main/resources/img/consume_api.png)

- Logging
![](/src/main/resources/img/ide_logging.png)

- SQS Dashboard
![](/src/main/resources/img/sqs-dashboard.png)

---

## 🧪 Testing Strategy

### Unit Tests

- `PublishMessageUseCaseTest`
- `ProcessMessageUseCaseTest`
- `SqsPublisherGatewayTest`
- `SqsListenerTest`
- `MessageControllerTest`

### Integration Test

- `MessageControllerIntegrationTest` with `TestRestTemplate`

---

## 📝 Author

**Ricardo Proença**\
Computer Engineer | Backend Kotlin + AWS | Clean Code & Hexagonal Architecture Advocate\
[LinkedIn](https://www.linkedin.com/in/ricardoproenca-dev) | [GitHub](https://github.com/rpdesenvolve)

