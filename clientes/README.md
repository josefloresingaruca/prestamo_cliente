<img src="https://cdn.icon-icons.com/icons2/632/PNG/512/users_icon-icons.com_57999.png" width="20%" style="float: right">

# API de Clientes y Préstamos

## Descripción
Este proyecto es una API RESTful desarrollada con **Spring Boot** para gestionar **clientes** y **préstamos** en un sistema bancario.

---

## Tecnologías Utilizadas
- **Java 17**
- **Spring Boot 3.4.0**
- **Lombok** - Reducir código repetitivo
- **MapStruct** - Mapeo entre entidades y DTOs
- **JUnit 5** y **Mockito** - Pruebas unitarias
- **Swagger 2.0** - Documentación de API
- **Maven** - Gestión de dependencias

---

## Endpoints Disponibles

### Clientes
| Método | Endpoint             | Descripción                      |
|--------|----------------------|----------------------------------|
| GET    | `/api/clientes`      | Obtener lista de clientes        |
| POST   | `/api/clientes`      | Crear un nuevo cliente           |
| PUT    | `/api/clientes/{id}` | Actualizar un cliente existente  |
| DELETE | `/api/clientes/{id}` | Eliminar un cliente por ID       |
| GET    | `/api/clientes/{id}/accion` | Acción según tipo de cliente |

### Préstamos
| Método | Endpoint              | Descripción                      |
|--------|-----------------------|----------------------------------|
| GET    | `/api/prestamos`      | Obtener lista de préstamos activos |
| POST   | `/api/prestamos`      | Crear un nuevo préstamo          |
| PUT    | `/api/prestamos/{id}` | Actualizar un préstamo existente |
| DELETE | `/api/prestamos/{id}` | Eliminar un préstamo por ID      |



---

## **Observaciones**
Este proyecto sigue las mejores prácticas de **Clean Code** y arquitectura de software, incluyendo:

- **Organización por capas:** Separación en `Controller`, `Service` y `Repository`.
- **Uso de DTOs:** Para separar la lógica de negocio y datos.
- **Pruebas unitarias:** Implementadas con **JUnit 5** y **Mockito** para asegurar la calidad del código.
- **Documentación:** Detallada con **Swagger/OpenAPI**.
- **Mapeo de entidades:** A través de **MapStruct** para un mapeo eficiente.


<h2>‍👨‍💻Autor</h2>
<ul>
<li><b>José Miguel Flores Ingaruca</b> - <a href="https://www.linkedin.com/in/jose-miguel-flores-ingaruca/">Linkedin</a> </li> 
</ul>
---
