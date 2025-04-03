# Catalogo Front - Angular Application

## Overview

`catalogo-front` is the frontend application for the Catalogo project. Built with Angular, it provides a user interface to interact with the backend API for managing a films catalog, including movies, actors, categories, and languages.

## Features

- Developed with Angular and TypeScript
- Communicates with the [Catalogo Backend](../catalogo/README.md) via REST API
- Bootstrap for UI components
- Routing with Angular Router
- Form validation and template driven forms
- Docker support for containerized deployment

## Requirements

Ensure you have the following installed:

- Node.js (LTS recommended)
- Angular CLI (`npm install -g @angular/cli`)
- Docker (optional, for containerized deployment)

## Setup & Installation

Clone the repository:
```bash
git clone https://github.com/SergioLM7/bootcamp-cap2025/
cd catalogo-front
```
Install dependencies:
```bash
npm install
```
Configure API Endpoint:
```Typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8001/'
};
```
Start back-end server:
```bash
mvn spring-boot:run
OR
push play in Boot Dashboard
```

### Development server

To start a local development server, run:

```bash
ng serve
```
Once the server is running, open your browser and navigate to `http://localhost:4200/`. The application will automatically reload whenever you modify any of the source files.

## Building
To build the project run:

```bash
ng build
```
This will compile your project and store the build artifacts in the `dist/` directory. By default, the production build optimizes your application for performance and speed.

## Related Projects

This frontend communicates with the backend service: [Catalogo Backend](../catalogo/README.md).

For an overview of all Bootcamp projects, see the [Main Repository](/README.md) README.

This project was generated using [Angular CLI](https://github.com/angular/angular-cli) version 19.2.5.

## Contact

**Sergio Lillo, Full Stack Developer**
<a href="https://www.linkedin.com/in/lillosergio/" target="_blank"> <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/8/81/LinkedIn_icon.svg/1200px-LinkedIn_icon.svg.png" width=30px, height=30px/></a> - sergiolillom@gmail.com

<p align="right">(<a href="#readme-top">back to top</a>)</p>
