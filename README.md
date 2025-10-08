# 🔐 Microservicio de Autenticación – CoE Development Platform

## Antes de Iniciar

Este microservicio forma parte de la plataforma **CoE Development Platform**, una solución diseñada para gestionar el registro, autenticación y validación de usuarios, proporcionando tokens **JWT** utilizados por los demás microservicios del ecosistema (como el microservicio de Cursos 🎓).

Emplea **Java 17**, **Spring Boot 3**, y sigue los lineamientos de **Arquitectura Limpia (Clean Architecture)**, promoviendo la independencia de frameworks, bases de datos y librerías externas.

Lee el artículo [Clean Architecture – Aislando los detalles](https://medium.com/bancolombia-tech/clean-architecture-aislando-los-detalles-4f9530f35d7a)

# Arquitectura

![Clean Architecture](https://miro.medium.com/max/1400/1*ZdlHz8B0-qu9Y-QO3AXR_w.png)

## Domain

Este es el módulo más interno de la arquitectura.  
Pertenece a la capa del **dominio** y encapsula la **lógica y reglas del negocio** mediante entidades y modelos de dominio.

### Contiene:
- **Modelos del dominio:** `UserAccount`, `Credential`, `Role`, `Permission`, `TokenSession`.
- **Interfaces gateway:** Definen los puertos de entrada/salida para los repositorios y la lógica JWT.
- **Excepciones de negocio y técnicas:** Manejo centralizado de errores de dominio.

## Usecases

Este módulo pertenece también a la capa del dominio y define la **lógica de aplicación**.  
Implementa los casos de uso que orquestan las reglas de negocio para los flujos principales:

- Registro de usuario
- Inicio de sesión
- Validación de token JWT
- Cierre de sesión
- Consulta de información de usuario

Los casos de uso no dependen de ningún framework ni tecnología, solo del dominio.

## Infrastructure

Contiene las implementaciones concretas de los puertos definidos en el dominio.  
Esta capa se divide en tres partes: **Helpers**, **Driven Adapters** y **Entry Points**.

### Helpers

Incluyen utilidades generales utilizadas por los adapters y controladores.  
Ejemplos:
- Codificación y encriptación con BCrypt.
- Generación y validación de tokens JWT.
- Excepciones y utilidades genéricas para repositorios.

Estas utilidades están basadas en patrones como [Repository y Unit of Work](https://medium.com/@krzychukosobudzki/repository-design-pattern-bc490b256006).

### Driven Adapters

Representan implementaciones externas al sistema.  
Este microservicio utiliza los siguientes:

- **JPA Adapter:** Conexión a **PostgreSQL** para la persistencia de usuarios, roles, credenciales y sesiones.

Cada adapter implementa un **gateway** definido en el dominio.

### Entry Points

Representan los puntos de entrada a la aplicación, exponiendo la lógica del negocio mediante **REST Controllers**.

Endpoints principales expuestos:

| Método | Endpoint | Descripción |
|--------|-----------|-------------|
| `POST` | `/api/v1/auth/register` | Registra un nuevo usuario |
| `POST` | `/api/v1/auth/login` | Autentica y genera un JWT |
| `GET` | `/api/v1/auth/validate` | Valida la autenticidad del token |
| `DELETE` | `/api/v1/auth/logout/{userId}` | Cierra la sesión del usuario |
| `GET` | `/api/v1/auth/user/{userId}` | Consulta los datos del usuario |

## Application

Es el módulo más externo de la arquitectura.  
Se encarga de **ensamblar todos los módulos**, resolver las dependencias y crear los beans de los casos de uso.

En esta capa se encuentra la clase principal que contiene el método:
```java
public static void main(String[] args)
```

### Responsabilidades:
- Configuración de **Spring Boot**.
- Definición de Beans y componentes.
- Escaneo automático de componentes (`@ComponentScan`).
- Inicialización del contexto de aplicación.

## Ejecución Local

### ⚙️ Requisitos previos

| Requisito | Versión mínima |
|------------|----------------|
| **Java JDK** | 17 |
| **PostgreSQL** | 15 o superior |
| **Gradle** | 8.x |

### 🧾 Configuración del entorno

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/andreaccamachoj/kata-course-service
   ```

2. **Crear la base de datos:**
   
   Ejecutar el archivo `init.sql` que se encuentra en: `deployment/db/init_db.sql`

3. 
   

3. **Configurar `application.yml`:**
   ```yaml
   server:
     port: 8080

   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/authdb
       username: postgres
       password: postgres
       driver-class-name: org.postgresql.Driver
     jpa:
       hibernate:
         ddl-auto: update
       show-sql: true
   ```

### ▶️ Compilación y ejecución

1. **Compilar el proyecto:**
   ```bash
   ./gradlew clean build
   ```

2. **Ejecutar la aplicación:**
   ```bash
   java -jar build/libs/auth.jar
   ```

3. **Probar el servicio:**
   ```
   http://localhost:8080/api/v1/auth/validate
   ```

## Flujo de Autenticación

1. El usuario se registra (`/register`).
2. Inicia sesión (`/login`) y recibe un **token JWT**.
3. Los microservicios (por ejemplo, Cursos) validan el token con `/validate`.
4. El usuario puede cerrar sesión con `/logout/{userId}`.

## Variables de Entorno

| Variable | Descripción |
|-----------|-------------|
| `URL_DB` | URL de la base de datos de PostgreSQL  |
| `USERNAME_DB` | Usuario de base de datos               |
| `PASSWORD_DB` | Contraseña de base de datos            |

## Autor
Desarrollado por **Andrea C.**