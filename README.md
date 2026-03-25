
# Library Management System

A simple, educational Library Management web application built with Spring Boot, Thymeleaf and Spring Data JPA. It provides a small UI to manage books, members and loans (borrow / return) and uses an in-memory H2 database by default for easy local development.

## Highlights

- Clean MVC structure using Spring Boot and Thymeleaf templates
- CRUD-like flows for Books and Members (add/list)
- Borrow and return flows for Loans with basic validation
- In-memory H2 database (configurable) for zero-setup development

## Tech stack

- Java 11+ (project uses Maven)
- Spring Boot (MVC, Data JPA)
- Thymeleaf templates for server-side UI
- H2 in-memory database (default)

## Quick start

Prerequisites: Java 11+ and Maven installed.

1. Build and run the app:

```bash
mvn spring-boot:run
```

2. Open the application in your browser:

- App: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:librarydb)

The default server port is 8080 and can be changed in `src/main/resources/application.properties`.

## What this app provides (UI pages & endpoints)

The web UI is small and uses Thymeleaf templates located in `src/main/resources/templates`.

Controllers and primary routes implemented in the app:

- Home
	- GET `/` — index page (template: `index.html`)

- Books
	- GET `/books` — list all books (template: `books.html`)
	- GET `/books/new` — show form to add a new book (template: `add-book.html`)
	- POST `/books` — submit form to create a book

- Members
	- GET `/members` — list all members (template: `members.html`)
	- GET `/members/new` — show form to add a new member (template: `add-member.html`)
	- POST `/members` — submit form to create a member

- Loans
	- GET `/loans` — list loans and show borrower/available book data (template: `loans.html`)
	- POST `/loans/borrow` — borrow a book (expected form fields: `bookId`, `memberId`)
	- POST `/loans/return` — return a book (expected form fields: `bookId`, `memberId`)

These mappings are implemented in `src/main/java/com/example/library/controller`.

## Configuration

Application properties live in `src/main/resources/application.properties`. By default this project uses H2 in-memory DB configured with:

```properties
spring.datasource.url=jdbc:h2:mem:librarydb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
```

Change the datasource settings to connect a persistent database (Postgres, MySQL, etc.).

## Project structure (important files)

- `src/main/java/com/example/library` — application code
	- `controller/` — HTTP controllers (BookController, MemberController, LoanController, HomeController)
	- `service/` — business logic and service implementation
	- `repository/` — Spring Data JPA repositories
	- `model/` — domain models (Book, Member, Loan, etc.)
	- `exception/` — custom exceptions
- `src/main/resources/templates/` — Thymeleaf HTML views
- `src/main/resources/static/` — static assets (css etc.)

## Tests

If unit or integration tests are present, run them with:

```bash
mvn test
```

## Packaging

Create an executable jar with:

```bash
mvn clean package
```

Then run with:

```bash
java -jar target/library-management-*.jar
```

## Development notes and tips

- The app is intentionally small and designed for learning. Business logic lives in `LibraryService`/`LibraryServiceImpl`.
- Borrow/return operations perform basic checks and throw domain exceptions (see `exception/` package). The controllers surface these as flash messages in the UI.
- To persist data between restarts, replace the H2 memory URL with a file-based H2 URL or configure an external DB.

## Contributing

Contributions are welcome. Open an issue or submit a pull request with a focused change. For larger features (API, authentication, etc.) open a discussion first.

## License

This repository does not include an explicit license file. Add a `LICENSE` file if you intend to open-source the code, or add licensing information here.

---
