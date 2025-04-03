# Catalogo - Spring Boot Backend

## Overview

The `catalogo` project is a backend service built with Spring Boot. It provides a REST API for managing a films catalog, including CRUD operations for films, actors, categories, and languages. It also has implemented some search and pagination methods.

## Features

- RESTful API with CRUD operations
- Spring Boot and Spring Data JPA for data persistence
- MySQL/PostgreSQL database support
- Docker support for containerized deployment
- Lombok for reducing boilerplate code
- OpenAPI (Swagger) documentation

## Requirements

Ensure you have the following installed:

- Java 17+
- Maven
- MySQL (configured in `application.properties`)
- Docker / Podman

## Setup & Installation

### Clone the repository

```bash
git clone https://github.com/SergioLM7/bootcamp-cap2025/
cd catalogo
```

### Configure the database

Update `src/main/resources/application.properties` with your database credentials.

Example with Sakila DB:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sakila
spring.datasource.username=root
spring.datasource.password=root
```

### Run the application

```bash
mvn spring-boot:run
```

## API Documentation

Once the application is running, API documentation is available at:
```
http://localhost:8001/swagger-ui/index.html
```

## Related Projects

This backend is used by the frontend project: catalogo-front

## License

This project is for educational purposes as part of a Bootcamp program.

## Contact

**Sergio Lillo, Full Stack Developer**
<a href="https://www.linkedin.com/in/lillosergio/" target="_blank"> <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/8/81/LinkedIn_icon.svg/1200px-LinkedIn_icon.svg.png" width=30px, height=30px/></a> - sergiolillom@gmail.com

<p align="right">(<a href="#readme-top">back to top</a>)</p>
