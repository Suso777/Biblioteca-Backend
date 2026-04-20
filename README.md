# Biblioteca Backend

API REST para la gestión de una biblioteca: autores y libros. Construida con Spring Boot y MySQL.

## Tecnologías

| Capa | Tecnología |
|------|-----------|
| Lenguaje | Java 25 |
| Framework | Spring Boot 4.0.5 |
| ORM | Spring Data JPA / Hibernate |
| Base de datos | MySQL |
| Validación | Jakarta Validation |
| Utilidades | Lombok |
| Build | Maven (con wrapper) |

## Requisitos previos

- Java 25
- MySQL 8+ corriendo en `localhost:3306`
- Maven (o usar el wrapper incluido `mvnw`)

## Instalación y ejecución

### 1. Crear la base de datos

```sql
CREATE DATABASE biblioteca_db;
```

### 2. Configurar credenciales (opcional)

Edita `src/main/resources/application.properties` si tus credenciales de MySQL son distintas:

```properties
spring.datasource.username=root
spring.datasource.password=root
```

### 3. Ejecutar la aplicación

```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

La aplicación arranca en `http://localhost:8080`.  
Hibernate crea las tablas automáticamente en el primer arranque (`ddl-auto=update`).

---

## Estructura del proyecto

```
src/main/java/com/biblioteca/backend/
├── BibliotecaBackendApplication.java
├── author/
│   ├── controller/AuthorController.java
│   ├── model/Author.java
│   ├── repository/AuthorRepository.java
│   └── service/AuthorService.java
└── book/
    ├── controller/BookController.java
    ├── model/Book.java
    ├── repository/BookRepository.java
    └── service/BookService.java
```

---

## Modelo de datos

### Author

| Campo | Tipo | Validación |
|-------|------|-----------|
| id | Long | PK, autoincrement |
| name | String | @NotBlank |
| surname | String | @NotBlank |
| nationality | String | @NotBlank |
| birthYear | Integer | @NotNull |
| alive | Boolean | @NotNull |
| books | List\<Book\> | OneToMany |

### Book

| Campo | Tipo | Validación |
|-------|------|-----------|
| id | Long | PK, autoincrement |
| title | String | @NotBlank |
| ISBN | String | @NotBlank |
| publicationYear | Integer | @NotNull |
| image | String | @NotBlank |
| author | Author | ManyToOne |

**Relación:** Un autor puede tener muchos libros (`OneToMany` / `ManyToOne`).

---

## Endpoints

### Autores — `/authors`

| Método | Ruta | Descripción | Body | Respuesta |
|--------|------|-------------|------|-----------|
| GET | `/authors` | Listar todos los autores | — | `200 OK` + `List<Author>` |
| GET | `/authors/{id}` | Obtener autor por ID | — | `200 OK` + `Author` / `404` |
| POST | `/authors` | Crear nuevo autor | `Author` JSON | `201 Created` + `Author` |
| PUT | `/authors/{id}` | Actualizar autor | `Author` JSON | `200 OK` + `Author` / `404` |
| DELETE | `/authors/{id}` | Eliminar autor | — | `204 No Content` / `404` |

#### Ejemplo — crear autor

```json
POST /authors
{
  "name": "Gabriel",
  "surname": "García Márquez",
  "nationality": "Colombiana",
  "birthYear": 1927,
  "alive": false
}
```

---

### Libros — `/books`

| Método | Ruta | Descripción | Body | Respuesta |
|--------|------|-------------|------|-----------|
| GET | `/books` | Listar todos los libros | — | `200 OK` + `List<Book>` |
| GET | `/books/{id}` | Obtener libro por ID | — | `200 OK` + `Book` / `404` |
| POST | `/books` | Crear nuevo libro | `Book` JSON | `200 OK` + `Book` |
| PUT | `/books/{id}` | Actualizar libro | `Book` JSON | `200 OK` + `Book` / `404` |
| DELETE | `/books/{id}` | Eliminar libro | — | `200 OK` |

#### Ejemplo — crear libro

```json
POST /books
{
  "title": "Cien años de soledad",
  "ISBN": "978-84-397-0494-4",
  "publicationYear": 1967,
  "image": "https://ejemplo.com/cien-años.jpg",
  "author": { "id": 1 }
}
```

> El autor debe existir previamente en la base de datos.

---

## Configuración de la base de datos

```properties
# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/biblioteca_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

---

## Build y empaquetado

```bash
# Compilar y generar JAR
./mvnw clean package

# Ejecutar el JAR directamente
java -jar target/biblioteca-backend-0.0.1-SNAPSHOT.jar

# Ejecutar tests
./mvnw test
```

---

## Estado del proyecto

Este proyecto es una API de gestión de biblioteca en estado de desarrollo. Actualmente no incluye:

- Autenticación ni autorización (Spring Security)
- DTOs separados de las entidades JPA
- Manejo global de excepciones
- Documentación OpenAPI / Swagger
- Configuración para entornos de producción