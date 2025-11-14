# Product API Project

This is a **Spring Boot REST API** project designed to manage product data. The project integrates with **MySQL** as the database and provides **JWT-based authentication** for secure API access.

## Features

- **CRUD operations** for product management
- **JWT-based authentication** for secure API access
- **MySQL** as the database backend
- **Spring Data JPA** for interacting with the database
- **Spring Security** for authentication and authorization

## Pre-requisites

Before running this project, ensure you have the following installed:

- **Java 21**
- **Maven** for building the project
- **MySQL** installed and running

## Setting Up the Database

The project uses **MySQL** for data storage. Follow these steps to set up the database:

### 1. Create the Database

Run the following SQL command in MySQL to create the `product_db` database:

```sql
CREATE DATABASE product_db;
