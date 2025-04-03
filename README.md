# Bootcamp Spring Boot & Angular Repository
 
 ## Overview

This repository contains multiple projects and exercises developed as part of a Bootcamp focused on backend development with Spring Boot and frontend development with Angular. The projects cover a range of topics, from CRUD operations to batch processing and SOAP web services.

### Built With

* <img src="https://github.com/devicons/devicon/blob/master/icons/spring/spring-original.svg"  title="Spring Boot" alt="Spring Boot" width="40" height="40"/>&nbsp;
* <img src="https://github.com/devicons/devicon/blob/master/icons/angular/angular-original.svg" title="Angular" alt="Angular" width="40" height="40"/>&nbsp;
* <img src="https://github.com/devicons/devicon/blob/master/icons/java/java-original.svg" title="Java" alt="Java" width="40" height="40"/>&nbsp;
* <img src="https://github.com/devicons/devicon/blob/master/icons/typescript/typescript-original.svg" title="Typescript" alt="Typescript" width="40" height="40"/>&nbsp;
* <img src="https://github.com/devicons/devicon/blob/master/icons/mysql/mysql-original.svg" title="MySQL" alt="MySQL" width="40" height="40"/>&nbsp;
* <img src="https://github.com/devicons/devicon/blob/master/icons/postman/postman-original-wordmark.svg" title="postman" alt="postman" width="40" height="40"/>&nbsp;
* <img src="https://github.com/devicons/devicon/blob/master/icons/github/github-original-wordmark.svg" title="github" alt="github" width="40" height="40"/>&nbsp;

## Projects

Below is an overview of the main projects in this repository:

- catalogo: A backend service built with Spring Boot that manages CRUD operations for movies, actors, categories, and languages.

- catalogo-front: An Angular frontend application for the catalogo project.

- demo-angular: A sample Angular project for testing and practice.

- demo-maven: A simple Maven-based Java project.

- demo: A general-purpose project for experimenting with Spring Boot an Angular.

- ejercicio-lotes-batch: A project focused on batch processing in Spring Batch.

- gilded-rose-kata-java: A Java implementation of the Gilded Rose Kata, a common coding challenge.

- laboratorio-soap-web-consumer: A SOAP web service client implementation.

- laboratorio-soap-web: A SOAP web service provider project.

## Requirements

To run the projects in this repository, ensure you have the following installed:

-Java 17+

-Spring Boot 3+

-Maven

-Node.js & Angular CLI (for frontend projects)

-Docker or Podman

## Setup & Usage

Each project has its own specific setup instructions. Please refer to the individual README files (if available) within each project directory for detailed steps.

Clone the repository:
```
git clone https://github.com/SergioLM7/bootcamp-cap2025
cd any-folder-name
```
Use Sakila DB (here you have one container, but proceed as you prefer)
```
podman run -d --name mysql-sakila -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 jamarton/mysql-sakila
```

Run a Spring Boot application
```
cd catalogo
mvn spring-boot:run
```

Run an Angular project
```
cd catalogo-front
npm install
ng serve
```

## Contact

**Sergio Lillo, Full Stack Developer**
<a href="https://www.linkedin.com/in/lillosergio/" target="_blank"> <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/8/81/LinkedIn_icon.svg/1200px-LinkedIn_icon.svg.png" width=30px, height=30px/></a> - sergiolillom@gmail.com

<p align="right">(<a href="#readme-top">back to top</a>)</p>
