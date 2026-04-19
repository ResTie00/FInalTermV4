
# FinalTermV4

This is my final project for the Programming Languages course. It's a console app where you can manage job applications (add, delete, update, view them). Everything is stored in a PostgreSQL database.

---

## What it does

You run the program and it shows you a menu with options:

1. Add a new job application
2. Delete one by id
3. See all applications
4. Find one by id
5. Update an existing one
6. Delete all of them (asks for confirmation)
7. Exit

Each application has: an id, a name, job title, job description, status (PENDING / APPROVED / REJECTED / PAUSED), and a date.

---

## How to run it

You'll need:
- Java 17 or newer
- Maven
- PostgreSQL

First, create a database in PostgreSQL:

```sql
CREATE DATABASE job_application_db;
```

Then create the table:

```sql
CREATE TABLE job_applications (
    id               BIGINT PRIMARY KEY,
    application_name VARCHAR NOT NULL,
    job_name         VARCHAR NOT NULL,
    job_description  TEXT,
    status           VARCHAR NOT NULL,
    date             DATE
);
```

Then open the `.env` file and put in your database credentials:

```
DB_URL=jdbc:postgresql://localhost:5432/job_application_db?options=-c%20lc_messages=en_US.UTF-8
DB_USERNAME=your_username
DB_PASSWORD=your_password
ADMIN_USERNAME=admin
ADMIN_PASSWORD=admin123
```

Then build and run:

```bash
mvn clean package
java -jar target/FInalTermV4-1.0-SNAPSHOT.jar
```

---

## Project structure

```
src/main/java/com/
├── Main.java               # the main menu and all the user input logic
├── model/
│   ├── JobApplication.java # the main class for an application
│   ├── ApplicationStatus.java
│   ├── BaseEntity.java
│   └── Printable.java
├── service/
│   └── ApplicationService.java  # handles validation before going to db
├── database/
│   └── DB.java             # all the SQL queries are here
└── exceptions/             # custom exceptions for different error cases
```

---

## Technologies used

- Java
- Maven
- PostgreSQL
- JDBC (no Hibernate or anything like that, just plain SQL)
- dotenv-java (to read the .env file)

---

## Documentation

### Modules and their responsibilities

The project is split into 3 main layers. `DB.java` handles all direct communication with the database using JDBC every SQL query (INSERT, SELECT, UPDATE, DELETE) lives here. `ApplicationService.java` sits on top of it and handles validation logic, it checks for duplicates, missing fields, and throws the right exception before anything reaches the database. `Main.java` runs the loop, shows the menu, reads user input, and calls the service.

The model layer has `JobApplication` as the main entity, `ApplicationStatus` as an enum with 4 values (PENDING, APPROVED, REJECTED, PAUSED), `BaseEntity` as a parent class, and `Printable` as an interface that `JobApplication` implements.

### Data structure

Each job application is stored as a row in the `job_applications` table with 6 fields: id (BIGINT), application_name, job_name, job_description, status, and date. On the Java side it maps directly to the `JobApplication` class.

### Challenges faced

**Error handling without crashing**: the hardest part was making the program recover from errors instead of just crashing. The solution was catching exceptions at the right level: database errors are caught in `DB.java` and converted to custom exceptions, then caught again in `Main.java` inside the menu loop so the program just prints the error and continues.

**Finding the right SQL error codes**: when working with PostgreSQL and JDBC, a generic `SQLException` is thrown for everything. To tell apart a duplicate key error from a connection failure, I had to dig into `SQLException` and check the SQLState codes (for example, `23505` means unique constraint violation). This led to creating `DBExceptionHandler` which maps these codes to specific custom exceptions.

**Reading the .env file**:  storing credentials directly in code is bad practice but reading from a config file wasn't obvious at first. Found the `dotenv-java` library which loads a `.env` file and makes values available via `Dotenv.get()`.

**Learning JDBC from scratch**: had no prior experience with JDBC. Had to learn how `DriverManager`, `Connection`, `PreparedStatement`, and `ResultSet` work together. Used `PreparedStatement` for all queries to avoid SQL injection.

---

## Presentation

[Click here to view the presentation](https://docs.google.com/presentation/d/13wLGoQRQSEnSqGpqGMQmRpV1oNo6KJ8v/edit?usp=drive_link&ouid=112504527904248519370&rtpof=true&sd=true)

---

## Author

ResTie00 — Final Term Project, 2024/2025

--- 

<img width="1803" height="889" alt="image" src="https://github.com/user-attachments/assets/c21104e7-2137-46e5-94e4-fdf7890ad0c0" />

--- 

<img width="1800" height="680" alt="image" src="https://github.com/user-attachments/assets/b653a2ff-262e-4081-a82a-b50ebdf3be3c" />

--- 

<img width="1807" height="772" alt="image" src="https://github.com/user-attachments/assets/6e817bcb-8844-44d3-b6e8-e0f5b8d57313" />

--- 

<img width="1804" height="783" alt="image" src="https://github.com/user-attachments/assets/ebc5951c-8194-4bbb-bf90-eab8d52bf000" />

--- 

<img width="1806" height="818" alt="image" src="https://github.com/user-attachments/assets/968e1cb0-5a4f-4f8a-8d68-c6ce5761c3df" />

--- 

<img width="1805" height="685" alt="image" src="https://github.com/user-attachments/assets/b2d8127f-76a8-4aab-a976-9cfdf6e11ea8" />

--- 

<img width="1804" height="473" alt="image" src="https://github.com/user-attachments/assets/8082251e-2d9c-4119-87f7-1b900f56bd55" />
