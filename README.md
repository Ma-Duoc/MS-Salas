# MS-Salas

Microservicio para gestión de salas construido con Spring Boot.

## Descripción

MS-Salas es un microservicio REST API que permite la gestión de salas, proporcionando operaciones CRUD para crear, listar y consultar salas por ID. Es parte de una arquitectura de microservicios para la gestión de recursos.

## Tecnologías

- **Java 21**
- **Spring Boot 3.2.5**
- **Spring Data JPA** - Para el acceso a datos
- **H2 Database** - Base de datos en memoria para desarrollo
- **MySQL Connector** - Conector para base de datos MySQL (opcional)
- **Lombok** - Para reducir código boilerplate
- **Jakarta Validation** - Para validación de datos
- **Maven** - Gestión de dependencias

## Estructura del Proyecto

```
src/main/java/com/example/mssalas/
├── MsSalasApplication.java          # Clase principal de la aplicación
├── controller/
│   └── SalaController.java          # Controlador REST con endpoints API
├── dto/
│   └── SalaDto.java                 # Data Transfer Object para salas
├── exception/
│   └── SalaException.java           # Excepción personalizada (404)
├── model/
│   └── Sala.java                    # Entidad JPA de Sala
├── repository/
│   └── SalaRepository.java          # Repositorio JPA
└── service/
    └── SalaService.java             # Lógica de negocio
```

## Configuración

### Puerto
El microservicio se ejecuta en el puerto **8084**.

### Base de Datos
- **Desarrollo**: H2 Database en memoria
- **Consola H2**: Disponible en `/h2-console`
- **URL JDBC**: `jdbc:h2:mem:testdb`
- **Usuario**: `sa`
- **Contraseña**: (vacía)

## Endpoints API

### Crear Sala
- **POST** `/api/salas`
- **Body**: 
```json
{
  "nombre": "Sala A",
  "tipo": "Conferencia",
  "capacidad": 50
}
```
- **Response**: `201 CREATED` con la sala creada

### Listar Todas las Salas
- **GET** `/api/salas`
- **Response**: `200 OK` con lista de salas

### Obtener Sala por ID
- **GET** `/api/salas/{id}`
- **Response**: `200 OK` con la sala solicitada
- **Error**: `404 NOT_FOUND` si la sala no existe

## Modelo de Datos

### Sala
- `id`: Long (autogenerado)
- `nombre`: String (requerido, no puede estar vacío)
- `tipo`: String (opcional)
- `capacidad`: Integer (opcional)

La aplicación estará disponible en `http://localhost:8084`

### Consola H2
Accede a la consola de H2 en: `http://localhost:8084/h2-console`

- **JDBC URL**: `jdbc:h2:mem:testdb`
- **User Name**: `sa`
- **Password**: (dejar vacío)

## Validación

El campo `nombre` es obligatorio y no puede estar vacío. Si se envía una solicitud sin este campo, se retornará un error de validación.

## Manejo de Errores

- **404 NOT_FOUND**: Se retorna cuando se intenta obtener una sala que no existe por ID
- **400 BAD_REQUEST**: Se retorna cuando hay errores de validación en los datos de entrada
