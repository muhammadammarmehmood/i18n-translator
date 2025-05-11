# i18n-translator

# Translation Management Service

---

## 🚀 Setup Instructions

### Prerequisites

- **Java 17+**
- **Maven 3.8+**
- **PostgreSQL**

---

### 🛠️ Getting Started

#### 1. Clone the Repository

#### 2. Set Up PostgreSQL Database
- Create a database in PostgreSQL
- Update the `application.properties` file with your database connection details

#### 3. Run the application
`mvn spring-boot:run`
- The application will be running at `http://localhost:8080`.
- Open Swagger UI at: `http://localhost:8080/swagger-ui.html`


### ⚡ Performance Testing
To test the scalability and performance, use the following endpoint to populate the database with 100k+ records for testing:
- Populate Test Data
<br />
  **POST**  `http://localhost:8080/data-loader`

### 🔒 Authentication
- All endpoints require an API key for access.
- In `application.properties` api key is defined by `api.key` property.

### 🛠️ Tools Used

    Spring Boot – Backend framework

    PostgreSQL – Database

    Spring Security – API key-based authentication

    Swagger – API documentation and testing

    ModelMapper – Object mapping

    JPA – ORM for database interaction


