# 📚 Practica 2 - Spring Boot

Proyecto de práctica desarrollado con **Spring Boot 3.4.3**, que forma parte del módulo de **Desarrollo Backend** de la carrera de informática. El sistema gestiona registros universitarios utilizando autenticación JWT, conexión a PostgreSQL, almacenamiento de sesiones con JDBC y caché con Redis.

---

## 🚀 Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 3.4.3**
- **Spring Web / JPA / Validation / Security**
- **JWT (Json Web Token)**
- **PostgreSQL**
- **Spring Session JDBC**
- **Redis (para cacheo)**
- **Springdoc OpenAPI (Swagger)**
- **Lombok**
- **Maven**

---

## ⚙️ Configuración del Proyecto

### `application.properties` (ubicado en `src/main/resources/`)

```properties
# Puerto y conexión a PostgreSQL
server.port=8080
spring.datasource.url=jdbc:postgresql://localhost:5432/universidad
spring.datasource.username=postgres
spring.datasource.password=123456

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# JWT
app.jwtSecret=CLAVE_SECRETA_SEGURA
app.jwtExpirationMs=86400000

# Spring Session con JDBC
spring.session.store-type=jdbc
spring.session.jdbc.initialize-schema=always
spring.session.timeout=30m
````

---

## 🔐 Seguridad y Autenticación

El proyecto implementa autenticación con JWT. Los archivos de seguridad incluyen:

* `SecurityConfig.java` – configuración principal de seguridad.
* `JwtAuthenticationFilter.java` – filtro que intercepta y valida tokens.
* `JwtUtils.java` – clase utilitaria para generar y validar JWT.
* `WebSecurityConfig.java` – reglas de autorización y protección de endpoints.

---

## 🧠 Estructura del Proyecto

```
src
├── main
│   ├── java/com/universidad/
│   │   ├── config/         # Configuración (Swagger, CORS, Seguridad, etc.)
│   │   ├── controller/     # Controladores REST: Auth, Usuario, Materia, etc.
│   │   ├── dto/            # DTOs para transferencia de datos
│   │   ├── entity/         # Entidades JPA (Estudiante, Materia, etc.)
│   │   ├── repository/     # Repositorios JPA
│   │   ├── service/        # Lógica de negocio
│   │   └── MiProyectoApplication.java  # Clase principal (@SpringBootApplication)
│   └── resources/
│       ├── application.properties
│       └── schema.sql / data.sql (si corresponde)
```

---

## 🔧 Instrucciones para Ejecutar

1. Clona el repositorio:

```bash
git clone https://github.com/Amilcar06/Practica2_SpringBoot.git
```

2. Abre el proyecto en tu IDE (recomendado: IntelliJ o VS Code).

3. Configura PostgreSQL local con una base de datos llamada `universidad`.

4. Actualiza `application.properties` si cambiaste usuario o contraseña de PostgreSQL.

5. Ejecuta el proyecto con:

```bash
./mvnw spring-boot:run
```

---

## 📩 Endpoints REST (Swagger UI)

El proyecto incluye documentación automática con **Swagger (Springdoc)**.

Una vez que esté corriendo, accede a:

```
http://localhost:8080/swagger-ui.html
```

---

## ✅ Estado del Proyecto

🔄 En desarrollo activo
🛡️ Con soporte de autenticación y sesiones
📦 Soporte para caché y documentación automática

---

## 🧑 Autor

**Amílcar Yujra**
📧 *[amilcaryujra23@gmail.com](mailto:amilcaryujra23@gmail.com)*
🔗 [GitHub/Amilcar06](https://github.com/Amilcar06)

---

## 📄 Licencia

Este proyecto está bajo la licencia MIT - consulta el archivo `LICENSE` para más detalles.

```

