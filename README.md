# BookAPI — RESTful Book Management System

A RESTful API built using the **Spring Framework**, Java, JDBC, and MySQL to manage book records. The application supports CRUD operations, request validation, centralized exception handling, and controller unit testing.

## Features

- **Create:** Add new books to the database.
- **Read:** Retrieve a book by ID or fetch all books.
- **Update:** Modify existing book details.
- **Delete:** Remove a book by ID.
- **Request validation:** Validate required fields and maximum field lengths.
- **Global exception handling:** Return consistent JSON responses for validation errors and unexpected exceptions.
- **HTTP status codes:** Handle successful requests, missing resources, invalid input, and server errors.
- **Unit testing:** Test controller behavior using JUnit 5 and Mockito.

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 17 | Programming language |
| Spring Framework 6 | REST API and dependency injection |
| Spring MVC | Request mapping and HTTP responses |
| JDBC | Database connectivity |
| MySQL | Relational database |
| Maven | Dependency management and build |
| Apache Tomcat 11 | Servlet container |
| Hibernate Validator | Bean validation |
| JUnit 5 | Unit testing |
| Mockito | Mocking service dependencies |
| Postman | API testing |

## Architecture

The application follows a layered architecture:

```text
Postman / API Client
        |
        v
   BookController
        |
        v
    BookService
        |
        v
   BookRepository
        |
        v
      MySQL
```

Supporting components:

- `DatabaseConfig` manages JDBC connections.
- `GlobalExceptionHandler` handles validation and unexpected exceptions.
- `BookControllerTest` verifies controller behavior using mocked dependencies.
- `AppConfig` and `WebConfig` configure the Spring application.

## Project Structure

```text
BookAPI/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/bookapi/
│   │   │       ├── config/
│   │   │       │   ├── AppConfig.java
│   │   │       │   ├── WebConfig.java
│   │   │       │   └── DatabaseConfig.java
│   │   │       ├── controller/
│   │   │       │   └── BookController.java
│   │   │       ├── exception/
│   │   │       │   └── GlobalExceptionHandler.java
│   │   │       ├── model/
│   │   │       │   └── Book.java
│   │   │       ├── repository/
│   │   │       │   └── BookRepository.java
│   │   │       ├── service/
│   │   │       │   └── BookService.java
│   │   │       └── BookApiInitializer.java
│   │   └── test/
│   │       └── java/
│   │           └── com/bookapi/controller/
│   │               └── BookControllerTest.java
├── pom.xml
└── README.md
```

*Adjust the tree if your actual project contains additional classes or uses a different package structure.*

## Prerequisites

Install the following before running the application:

- JDK 17 or a compatible supported Java version.
- Apache Maven.
- MySQL Server and MySQL Workbench.
- Apache Tomcat 11.
- Eclipse IDE or another Java IDE.
- Postman for API testing.

## Database Setup

Create the database in MySQL:

```sql
CREATE DATABASE bookstore_db;

USE bookstore_db;

CREATE TABLE books (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(150) NOT NULL,
    author VARCHAR(100) NOT NULL
);
```

Optional sample data:

```sql
INSERT INTO books (title, author)
VALUES
    ('Effective Java', 'Joshua Bloch'),
    ('Clean Code', 'Robert C. Martin');
```

Configure your database connection in `DatabaseConfig.java`:

```java
private static final String URL =
    "jdbc:mysql://localhost:3306/bookstore_db";

private static final String USERNAME = "root";
private static final String PASSWORD = "YOUR_MYSQL_PASSWORD";
```

Replace the password placeholder with your local MySQL password. **Never commit real database credentials to GitHub.**

## Running the Application

This project uses the plain Spring Framework and a WAR deployment; it does not use Spring Boot.

1. Clone the repository or import the Maven project into Eclipse.
2. Configure your MySQL database and connection credentials.
3. In Eclipse, right-click the project and select **Maven → Update Project**.
4. Build the project and resolve any dependency errors.
5. Add the project to your Tomcat server.
6. Start Tomcat and confirm that the application is deployed.
7. Open Postman and test the endpoints below.

The base URL used in the examples is:

```text
http://localhost:8080/bookapii
```

The context path may differ if your deployed application uses another name.

## API Endpoints

### 1. Retrieve all books

**GET** `/books`

```text
http://localhost:8080/bookapii/books
```

Expected response: `200 OK`

Returns a JSON array of books.

### 2. Retrieve a book by ID

**GET** `/books/{id}`

```text
http://localhost:8080/bookapii/books/1
```

Expected responses:

- `200 OK` — book found.
- `404 Not Found` — book does not exist.

Example response:

```json
{
  "id": 1,
  "title": "Effective Java",
  "author": "Joshua Bloch"
}
```

### 3. Add a new book

**POST** `/books`

```text
http://localhost:8080/bookapii/books
```

In Postman, select **Body → raw → JSON**.

Request body:

```json
{
  "title": "Clean Code",
  "author": "Robert C. Martin"
}
```

Expected response: `201 Created`

The response includes the newly generated book ID.

Validation rules:

- Title must not be blank.
- Author must not be blank.
- Title must not exceed 150 characters.
- Author must not exceed 100 characters.

Invalid input returns `400 Bad Request`.

Example validation response:

```json
{
  "message": "Validation failed",
  "errors": [
    "Title is required"
  ]
}
```

### 4. Update an existing book

**PUT** `/books/{id}`

```text
http://localhost:8080/bookapii/books/1
```

Request body:

```json
{
  "title": "Effective Java - Updated",
  "author": "Joshua Bloch"
}
```

Expected responses:

- `200 OK` — book updated.
- `404 Not Found` — no book exists with the specified ID.

### 5. Delete a book

**DELETE** `/books/{id}`

```text
http://localhost:8080/bookapii/books/1
```

Expected responses:

- `200 OK` — book deleted.
- `404 Not Found` — no book exists with the specified ID.

Use a test record when verifying deletion.

## Error Handling

The application uses `GlobalExceptionHandler` with Spring's `@RestControllerAdvice` and `@ExceptionHandler`.

| HTTP status | Meaning |
|---|---|
| `200 OK` | Request completed successfully |
| `201 Created` | Book created successfully |
| `400 Bad Request` | Request validation failed |
| `404 Not Found` | Requested book does not exist |
| `500 Internal Server Error` | Unexpected server-side exception |

Validation failures return structured JSON messages. Unexpected exceptions return a generic error message rather than exposing internal exception details to the client.

## Testing

API testing was performed using Postman.

The following scenarios were verified:

- Retrieve all books.
- Retrieve an existing book.
- Request a nonexistent book ID.
- Create a valid book.
- Reject invalid book details.
- Update an existing book.
- Delete a test book.

Controller unit tests use JUnit 5 and Mockito to verify successful retrieval and the not-found response.

Run the Maven test suite from the project root:

```bash
mvn test
```

## Future Improvements

- Add pagination and sorting for book listings.
- Introduce integration tests for repository and database operations.
- Add structured server-side logging.
- Add API documentation with OpenAPI/Swagger.
- Implement authentication and authorization.
- Externalize database configuration using environment variables.
- Add automated build and deployment workflows.

## Learning Outcomes

This project demonstrates practical experience with:

- Spring MVC and REST API development.
- Dependency injection and layered architecture.
- JDBC and MySQL database operations.
- HTTP methods, status codes, and request handling.
- Bean validation and global exception handling.
- Unit testing with JUnit 5 and Mockito.
- Deploying a Maven WAR application to Apache Tomcat.

---

**Project:** BookAPI  
**Application type:** RESTful web application  
**Framework:** Plain Spring Framework (not Spring Boot)
