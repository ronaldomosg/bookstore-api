# bookstore API

API REST para la gestion de una libreria en linea. El proyecto implementa arquitectura por capas, Spring Security con JWT, Spring Data JPA, H2 para desarrollo, contratos normalizados de respuesta y flujo Git basado en ramas por feature.

## Tecnologias

- Java 21
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- H2 Database
- OpenAPI / Swagger UI
- Maven
- Lombok

## Arquitectura

El proyecto sigue una arquitectura por capas:

- `controller`: expone endpoints REST.
- `service`: contiene la logica de negocio.
- `repository`: acceso a datos con Spring Data JPA.
- `entity`: modelo de dominio JPA.
- `dto.request`: contratos de entrada.
- `dto.response`: contratos de salida.
- `mapper`: conversion manual entre entidades y DTOs.
- `security`: autenticacion y autorizacion JWT.
- `exception`: excepciones personalizadas y handler global.
- `config`: configuraciones generales.

## Configuracion local

La API usa H2 en memoria para desarrollo.

Archivo principal:

```properties
spring.datasource.url=jdbc:h2:mem:bookstoredb
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.servlet.context-path=/api/v1
app.jwt.secret=${JWT_SECRET:mysecretkeymysecretkeymysecretkeymysecretkey}
app.jwt.expiration=86400000
```

Opcionalmente puedes definir la variable de entorno:

```bash
JWT_SECRET=mysecretkeymysecretkeymysecretkeymysecretkey
```

## Ejecutar el proyecto

```bash
.\mvnw.cmd spring-boot:run
```

La API queda disponible en:

```text
http://localhost:8080/api/v1
```

La consola H2 queda disponible en:

```text
http://localhost:8080/api/v1/h2-console
```

Swagger UI queda disponible en:

```text
http://localhost:8080/api/v1/swagger-ui/index.html
```

Credenciales H2:

```text
JDBC URL: jdbc:h2:mem:bookstoredb
User: sa
Password:
```

## Usuario administrador inicial

Al iniciar la aplicacion se crea un usuario administrador por defecto:

```text
email: admin@bookstore.com
password: Admin1234
role: ADMIN
```

## Ejecutar pruebas

```bash
.\mvnw.cmd test
```

## Contrato de respuesta exitosa

```json
{
  "status": "success",
  "code": 200,
  "message": "Operation completed",
  "data": {},
  "timestamp": "2026-04-22T00:00:00Z"
}
```

## Contrato de respuesta de error

```json
{
  "status": "error",
  "code": 404,
  "message": "Resource not found",
  "errors": [
    "Book not found with id: 99"
  ],
  "timestamp": "2026-04-22T00:00:00Z",
  "path": "/api/v1/books/99"
}
```

## Endpoints principales

### Auth

```text
POST /auth/register
POST /auth/login
```

### Books

```text
GET    /books
GET    /books/{id}
POST   /books
PUT    /books/{id}
DELETE /books/{id}
```

Filtros:

```text
GET /books?authorId=1
GET /books?categoryId=1
GET /books?authorId=1&categoryId=1
```

### Authors

```text
GET    /authors
GET    /authors/{id}
POST   /authors
PUT    /authors/{id}
DELETE /authors/{id}
GET    /authors/{id}/books
```

### Categories

```text
GET    /categories
GET    /categories/{id}
POST   /categories
PUT    /categories/{id}
DELETE /categories/{id}
GET    /categories/{id}/books
```

### Orders

```text
POST  /orders
GET   /orders/my
GET   /orders
PATCH /orders/{id}/confirm
PATCH /orders/{id}/cancel
```

## Seguridad

Endpoints publicos:

```text
POST /auth/register
POST /auth/login
GET  /books/**
GET  /authors/**
GET  /categories/**
GET  /v3/api-docs/**
GET  /swagger-ui/**
```

Endpoints de ADMIN:

```text
POST   /books
PUT    /books/**
DELETE /books/**
POST   /authors
PUT    /authors/**
DELETE /authors/**
POST   /categories
PUT    /categories/**
DELETE /categories/**
GET    /orders
```

Endpoints de USER:

```text
POST  /orders
GET   /orders/my
PATCH /orders/{id}/confirm
PATCH /orders/{id}/cancel
```

## Reglas de negocio implementadas

- No se puede registrar un usuario con email duplicado.
- No se puede consultar un recurso inexistente.
- No se puede crear un pedido con stock insuficiente.
- El total del pedido se calcula en el service.
- Al confirmar un pedido se descuenta el stock.
- Un usuario solo puede consultar sus propios pedidos.
- Un pedido confirmado no se puede cancelar.
- No se puede eliminar un autor que tenga libros asociados.

## Flujo Git usado

Ramas principales:

```text
main
develop
feature/auth-module
feature/book-catalog
feature/order-management
feature/author-category
feature/project-deliverables
```

Convencion de commits:

```text
feat: add JWT authentication filter
fix: correct price validation in BookRequest
refactor: extract order total calculation to service
docs: add endpoint documentation
test: add unit tests
chore: update configuration
```

## Entregables

- Repositorio GitHub con ramas por feature.
- Pull Requests hacia develop.
- Coleccion Postman exportada en JSON: `postman/bookstore-api.postman_collection.json`.
- README con instrucciones de ejecucion.
- Diagrama ER del modelo de datos: `docs/er-diagram.md`.
