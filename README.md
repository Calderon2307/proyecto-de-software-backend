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

---
## 6. 📘 Documentación automática con Swagger / OpenAPI

Este proyecto integra **springdoc-openapi**, lo que genera automáticamente la documentación de la API.

### ✔️ Acceso local

**Swagger UI:**  
👉 http://localhost:8080/swagger-ui/index.html

**OpenAPI JSON:**  
👉 http://localhost:8080/v3/api-docs

**OpenAPI YAML:**  
👉 http://localhost:8080/v3/api-docs.yaml


---

## 7. 🛡️ Seguridad (JWT + OAuth2 + CORS)

El proyecto implementa:

- **JWT** para login y validación de peticiones.  
- **OAuth2** para autenticación con Google.  
- **Filtro JWT personalizado.**  
- **CORS** configurado para entornos de desarrollo y producción.  
- **Seguridad expuesta en la clase `SecurityConfig`.**

Además, **Swagger está habilitado explícitamente dentro de la configuración de seguridad**.

> Detalles completos en la carpeta `controller`
---
## Manual de Desarrollo / Contribución

Esta sección describe cómo está organizado el desarrollo del proyecto **Pokedex V2 – Backend** y qué pasos debe seguir cualquier desarrollador para colaborar de forma consistente.

---

### 4.1 Guía de estilo de código

El backend está desarrollado en **Java 21 + Spring Boot 3** con arquitectura por capas (`controller`, `service`, `repository`, `dto`, `mapper`, etc.).  
Las principales convenciones son:

- **Lenguaje y formato**
  - Java siguiendo el estilo estándar de **Google/Oracle**:
    - Clases y `@Records`: `PascalCase` (por ejemplo, `EntrenadorController`, `PokemonService`).
    - Métodos y variables: `camelCase` (por ejemplo, `findByEmail`, `pokemonRepository`).
    - Constantes: `UPPER_SNAKE_CASE`.
  - Sangría de **4 espacios**, llaves de apertura en la misma línea.
  - Un archivo por clase / interfaz.

- **Paquetes**
  - Organización lógica por dominio y capa, por ejemplo:
    - `com.software.pokedexv2.controller`
    - `com.software.pokedexv2.service`
    - `com.software.pokedexv2.repository`
    - `com.software.pokedexv2.dto.request` / `dto.response`
    - `com.software.pokedexv2.mapper`
  - Evitar lógica de negocio dentro de `controller` o `repository`; concentrarla en `service`.

- **Controladores (REST)**
  - Usar `@RestController` y agrupar rutas bajo `@RequestMapping("/api/...")`.
  - Devolver siempre `ResponseEntity<...>` con:
    - `200/201` para respuestas exitosas.
    - `400`, `401`, `403`, `404`, `409`, `500`, etc. según corresponda.
  - Validar entrada con `@Valid` y anotaciones de `jakarta.validation` en los DTOs.

- **DTOs, Entities y Mappers**
  - Las entidades JPA representan tablas de base de datos, ubicadas en `entity` o `model`.
  - Los DTOs se usan para entrada/salida de API (nunca exponer directamente las entidades).
  - Uso de **MapStruct** o mapeadores manuales en la capa `mapper` para convertir entre Entity ↔ DTO.

- **Seguridad**
  - Autenticación con **JWT** y **Google OAuth2**.
  - No exponer secretos en el código: usar variables de entorno / `application-*.yml`.
  - Rutas públicas y protegidas definidas en la configuración de seguridad (por ejemplo, `SecurityConfig`).

- **Otras prácticas**
  - Manejo de errores centralizado con `@ControllerAdvice` (excepciones personalizadas).
  - Comentarios breves y claros solo cuando el código no sea obvio.
  - Tests unitarios preferentemente con JUnit/Mockito para servicios críticos.

---

### 4.2 Proceso de desarrollo con Git (Branching Strategy)

El flujo de trabajo con Git se basa en ramas separadas para desarrollo y nuevas funcionalidades:

- **Ramas principales**
  - `main`: rama estable de producción. Solo recibe merges desde `develop` cuando se libera una versión.
  - `develop`: rama de integración donde se van uniendo las funcionalidades completadas y probadas.

- **Ramas de trabajo**
  - `feature/...`: para nuevas funcionalidades.
    - Ejemplos: `feature/registro-entrenador`, `feature/equipos-pokemon`.
  - `hotfix/...`: para correcciones urgentes en producción.
    - Ejemplo: `hotfix/fix-refresh-token`.
  - (Opcional) `bugfix/...` o `chore/...` para ajustes menores, refactors, tareas técnicas.

- **Flujo típico de trabajo**

  1. **Crear rama de trabajo** desde `develop`:
     ```bash
     git checkout develop
     git pull
     git checkout -b feature/nombre-descriptivo
     ```
  2. Implementar cambios localmente y realizar **commits pequeños y descriptivos**:
     - Se recomienda usar estilo tipo Conventional Commits:
       - `feat:`, `fix:`, `chore:`, `refactor:`, etc.  
       Ejemplo: `feat: add endpoint to create pokemon team`.
  3. Subir la rama al repositorio remoto:
     ```bash
     git push -u origin feature/nombre-descriptivo
     ```
  4. Crear un **Pull Request (PR)** hacia `develop`:
     - Describir el objetivo de la rama, cambios principales y cómo probarlos.
     - Al menos una revisión de otro miembro del equipo antes del merge.
  5. Tras aprobar el PR:
     - Hacer **merge a `develop`**, resolver conflictos si los hay y borrar la rama de feature.
  6. Cuando `develop` está estable y probado:
     - Crear un PR de `develop` → `main`.
     - Etiquetar el merge con un **tag** de versión (por ejemplo, `v1.0.0`).

- **Buenas prácticas adicionales**
  - Actualizar frecuentemente la rama de trabajo con `develop` para minimizar conflictos.
  - No hacer commit de archivos generados (`target/`, `.idea/`, etc.); respetar `.gitignore`.
  - Nunca hacer cambios directos en `main` o `develop` (si no es una emergencia justificada).

---

### 4.3 Cómo agregar nuevas funcionalidades (Guía para futuros desarrolladores)

Para agregar una nueva funcionalidad al backend de Pokedex V2, sigue este flujo:

1. **Definir el alcance**
   - Crear un issue o tarea describiendo:
     - Problema a resolver o nueva funcionalidad.
     - Endpoints necesarios (`GET`, `POST`, etc.).
     - Campos y reglas de validación.
   - Acordar con el equipo el nombre de la rama (`feature/...`).

2. **Crear la rama de desarrollo**
   - Desde `develop`:
     ```bash
     git checkout develop
     git pull
     git checkout -b feature/nueva-funcionalidad
     ```

3. **Diseño de modelo y base de datos**
   - Si se necesita una nueva entidad:
     - Agregar entidad JPA (con `@Entity`) y sus relaciones.
     - Crear o actualizar `repository` correspondiente (`extends JpaRepository`).
   - Si se modifican tablas existentes:
     - Ajustar entidades y revisar scripts/migraciones (si se usan).

4. **Crear DTOs y mapeo**
   - Definir `RequestDTO` y `ResponseDTO` en `dto.request` y `dto.response`.
   - Actualizar o crear mapeadores en `mapper` para Entity ↔ DTO.

5. **Implementar la lógica de negocio**
   - Agregar métodos en la capa `service` que contengan la lógica necesaria.
   - Mantener los servicios lo más **cohesivos** posible (un servicio por agregado/dominio).

6. **Exponer los endpoints en el controlador**
   - Crear o actualizar el `@RestController` correspondiente:
     - Definir rutas `@GetMapping`, `@PostMapping`, etc. bajo `/api/...`.
     - Validar parámetros y cuerpo de petición con `@Valid` y anotaciones de validación.
     - Usar `ResponseEntity` para devolver respuestas con el código HTTP adecuado.

7. **Actualizar seguridad**
   - Si los nuevos endpoints deben ser protegidos:
     - Configurarlos en la clase de seguridad para requerir JWT / roles.
   - Si deben ser públicos, agregarlos explícitamente a las rutas permitidas.

8. **Documentar la API (Swagger / OpenAPI)**
   - Asegurarse de que los nuevos endpoints:
     - Tengan anotaciones de documentación (si se usan).
     - Aparezcan correctamente en la UI de Swagger generada.
   - Si se mantiene un archivo `openapi.yml`, actualizarlo con:
     - Nuevos paths, schemas y ejemplos de request/response.

9. **Pruebas**
   - Crear o actualizar **tests unitarios** y/o de integración para:
     - Servicios.
     - Repositorios (cuando aplique).
   - Probar manualmente la funcionalidad usando Swagger, Postman o similar.
   - Verificar casos de éxito y casos de error (validaciones, permisos, etc.).

10. **Commits, push y Pull Request**
    - Hacer commits descriptivos y subir la rama.
    - Crear un Pull Request hacia `develop`:
      - Describir qué se implementó.
      - Incluir pasos para probar y capturas si es necesario.
    - Corregir observaciones de la revisión de código hasta que se apruebe.

11. **Merge y limpieza**
    - Hacer merge del PR en `develop`.
    - Eliminar la rama `feature/...` si ya no será utilizada.
    - Si la versión está lista para liberarse, coordinar el merge de `develop` → `main` y la creación del tag.

---

> **Nota:** Cualquier nueva contribución debe respetar estas reglas de estilo, el flujo de Git y la estructura por capas del proyecto para mantener la consistencia y facilitar el mantenimiento futuro.
