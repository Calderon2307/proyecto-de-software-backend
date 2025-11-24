# Pokedex V2 – Backend

  

API REST desarrollada con Spring Boot 3 y PostgreSQL para gestionar entrenadores, Pokémon y equipos dentro del proyecto académico Pokedex V2.

Este repositorio corresponde **solo al backend**, utilizado por la aplicación frontend.
  
---

## 1. Descripción del proyecto

  

La API de Pokedex V2 permite:

  

- Registrar y autenticar entrenadores mediante **JWT** y **Google OAuth2**.

- Administrar información de **Pokémon** (tipos, sprites, características).

- Crear y gestionar **equipos de Pokémon** para cada entrenador.

- Asignar, organizar y remover Pokémon dentro de un equipo.

- Exponer endpoints REST organizados en una arquitectura limpia por capas:

- `controller`

- `service`

- `repository`

- `dto`

- `mapper`

  

### 👥 ¿Para quién es?

Para estudiantes, desarrolladores y el equipo del curso de Ingeniería de Software quienes necesitan un backend funcional, seguro y escalable para una Pokédex o app similar.

  

### ¿Qué problema resuelve?

Centraliza la lógica de negocio en un backend bien estructurado que:

- Maneja usuarios, roles y seguridad.

- Expone endpoints seguros con tokens JWT.

- Gestiona Pokémon y equipos de forma sólida y consistente.

- Permite conectar sin problemas un frontend web o app móvil.

  

---

  

## 2. Requisitos previos

  

Debes tener instalado:

  

- **Java JDK 21**

- **Maven 3.9+** (o usar los wrappers incluidos)

- **PostgreSQL 14+**

- **Git**

- Opcional: **IntelliJ IDEA / VSCode *

  

> Todas las dependencias se descargan automáticamente al compilar con Maven.

  

---

  

## 3. ⚙️ Instalación paso a paso

  

### 1- Clonar el repositorio

  

```bash

git clone https://github.com/tu-usuario/pokedex-backend.git

cd pokedex-backencd
```
### 2-  Crear la base de datos PostgreSQL

`CREATE DATABASE "pokedex-backend";`
### 3- Mapear variables en `application.yaml`

`spring:  datasource:  url:  ${DB_URL} 
username:  ${DB_USERNAME} 
 password:  ${DB_PASSWORD}  
 jwt:  secret:  key:  ${JWT_SECRET_KEY}  
 time:  expiration:  ${JWT_EXPIRATION_MS}  
 server:  port:  ${SERVER_PORT:8080}` 

### 4- Compilar el proyecto

Linux/Mac:

`./mvnw clean install` 

Windows:

`mvnw.cmd clean install`

--- 
## 4. Ejecución de la aplicación

### Opción A — Ejecutar con Maven

`./mvnw spring-boot:run` 

Windows:

`mvnw.cmd spring-boot:run` 

### Opción B — Ejecutar el JAR

`java -jar target/pokedex-backend-0.0.1-SNAPSHOT.jar` 

### URL base

`http://localhost:8080`
## 5. 🔐 Endpoints principales (overview)

### Autenticación

-   `POST /api/v2/auth/register`
    
-   `POST /api/v2/auth/login`
    

### Entrenadores

-   `/pokedexV2/api/entrenador`
    

### Pokémon

-   `/pokedexV2/api/pokemon`
    

### Equipos

-   `/pokedexV2/api/equipo`
    

### Pokémon–Equipo

-   `/pokedexV2/api/pokemon-equipo`
    

> Detalles completos en la carpeta `controller`

## 7. 🗂️ Estructura del proyecto

src/
└── main/
    ├── java/com/software/pokedexV2
    │   ├── config/        # Seguridad, CORS, JWT, OAuth
    │   ├── controller/    # Endpoints REST
    │   ├── dto/           # DTOs de request/response
    │   ├── entities/      # Entidades JPA
    │   ├── exception/     # Manejo global de errores
    │   ├── mapper/        # Conversión Entity ↔ DTO
    │   ├── repository/    # Repositorios JPA
    │   └── service/       # Lógica de negocio
    └── resources/
        └── application.yaml



