# Java Rest Assured Automation Suite

API test automation framework built in Java using Rest Assured, Maven, and JUnit 5.  
The goal of this project is to practice, structure, and evolve API testing following corporate-style modularization, organization, and best practices.

## Technologies
- Java 21 (LTS)
- Maven
- Rest Assured
- JUnit 5
- VS Code (or any compatible IDE)

## Project Goals
- Criar testes GET, POST, PUT, DELETE
- Validar payloads JSON
- Implement global Rest Assured configuration
- Structure the project in layers (base, endpoints, payloads, tests)
- Evolve toward authentication, parameterized tests, and reporting

## Prerequisites
- JDK 21 instalado e configurado no `JAVA_HOME`
- Maven 3.9+

## How to Run
```bash
mvn clean test
```

## Readable Output (PowerShell)
If the raw VS Code log is hard to read, run:

```powershell
./scripts/run-training.ps1
```

For a specific class:

```powershell
./scripts/run-training.ps1 -TestClass PostsCrudTest
```

## Configuration
File: `src/test/resources/config/config.properties`

```properties
api.content.type=application/json
api.practice.base.url=https://jsonplaceholder.typicode.com
```

- No API key is required for JSONPlaceholder.

## Implemented Scenarios
- `PostsCrudTest`: full flow using `JSONPlaceholder`
    - `GET /posts`
    - `GET /posts/1`
    - `GET /posts/99999` (not found)
    - `POST /posts`
    - `PUT /posts/1`
    - `PATCH /posts/1`
    - `DELETE /posts/1`
    - `GET /posts?userId=1` (filter)
    - `GET /posts/1/comments` (nested route)

- `PostPayloadTest`: validates payload assembly for create and update
- `TestDataTest`: validates reading test data from JSON
- `TestConfigTest`: validates reading project configuration

> Note: JSONPlaceholder simulates persistence for POST/PUT/PATCH/DELETE.

## Testing Strategy (Didactic)
- `config/data/payload` tests run offline and ensure framework fundamentals
- `PostsCrudTest` tests validate real API endpoints
- This approach helps you learn structure first, then HTTP behavior

## Initial Structure
```text
src
 └── test
      ├── java/com/gabriel
      │   ├── base
      │   ├── config
      │   ├── endpoints
      │   ├── payloads
      │   └── tests
      └── resources
          ├── config
          └── data
```

## Playwright Equivalence
- `pages` (UI) -> `endpoints` (API)
- `BaseTest` still exists for global setup
- `config` and `resources/data` keep the same role
