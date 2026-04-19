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
6. Delete all of them
7. Exit

Each application has: an id, a name, job title, job description, status (PENDING / APPROVED / REJECTED / PAUSED), and a date.

---

## How to run it

You'll need:
- Java (17 or newer)
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
