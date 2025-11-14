# Product CRUD API Project 🚀

This is a **Spring Boot REST API** project designed to manage product data. The project integrates with **MySQL** as the database and provides **JWT-based authentication** for secure API access.

## Features 🛠️

- **CRUD operations** for product management
- **JWT-based authentication** for secure API access 🔐
- **MySQL** as the database backend 💾
- **Spring Data JPA** for interacting with the database
- **Spring Security** for authentication and authorization 🛡️

## Pre-requisites 📝

Before running this project, ensure you have the following installed:

- **Java 21** (or higher) ☕
- **Maven** for building the project ⚙️
- **MySQL** installed and running 🐬

## GitHub Repository 🔗

You can find the project repository here:  
[Product-CRUD-API](https://github.com/harshkhandelwal459/Product-CRUD-API)

### Clone the Repository

1. **Using SSH:**

    ```bash
    git clone git@github.com:harshkhandelwal459/Product-CRUD-API.git
    ```
2. **Using HTTPS:**

    ```bash
    git clone https://github.com/harshkhandelwal459/Product-CRUD-API.git
    ```

### Setting Up the Database 🗄️

The project uses **MySQL** for data storage. Follow these steps to set up the database:

1. **Create the Database**  
   Run the following SQL command in MySQL to create the `product_db` database:

    ```sql
    CREATE DATABASE product_db;
    ```

2. **Update `application.properties`**  
   In the `src/main/resources/application.properties` file, make sure you configure the **database connection** details correctly:

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/product_db
    spring.datasource.username=root
    spring.datasource.password=your_password
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
    ```

   > Replace `your_password` with your MySQL root password (or another MySQL user and password if applicable).

## Running the Project 🏃‍♂️

Once the MySQL database is set up, you can run the project using Maven.

### Steps to run the project:

1. **Navigate to the project directory** in the terminal.

2. **Run the project** with Maven:

    ```bash
    mvn spring-boot:run
    ```

3. The application should now be running at `http://localhost:8080`.

---

## Note: No Docker Usage 🐳

This project does **not use Docker**. It assumes that you have MySQL installed and running locally on your machine.

---

## Swagger UI 📄

1. Once the application is running, you can view the API documentation and try out the endpoints directly from Swagger UI at:

    ```bash
    http://localhost:8080/swagger-ui/index.html
    ```
---

### Additional Information 📚

- **JWT Authentication**: This project uses JWT to authenticate users. You will need to send a valid token in the `Authorization` header of each API request to access protected endpoints.

- **API Endpoints**: Check the project documentation or Swagger UI for available API endpoints once the application is running.

---

Let me know if you need further adjustments! The icons should help make the `README.md` more visually appealing and easier to follow. 😊
