# API REST de Gestión de Productos

API RESTful desarrollada con Spring Boot para la gestión de productos.

## Características

- CRUD completo de productos
- Paginación de resultados
- Búsqueda con filtros por nombre
- Validación de datos
- Base de datos en memoria (H2)
- Identificadores UUID

## Tecnologías

- Java 21
- Spring Boot 3.3.5
- Spring Data JPA
- H2 Database
- Maven

## Estructura del Proyecto

```
src/main/java/com/example/demo/
├── controller/       # Controladores REST
├── dto/              # Objetos de Transferencia de Datos
├── entity/           # Entidades JPA
├── exception/        # Manejo de excepciones
├── repository/       # Repositorios de datos
└── service/          # Lógica de negocio
```

## Cómo Ejecutar

### Requisitos Previos

- JDK 21 o superior
- Maven 3.8+

### Compilar y Ejecutar

```bash
mvn clean install
mvn spring-boot:run
```

La aplicación démarre en `http://localhost:8080`

## Endpoints API

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/productos` | Listar todos los productos |
| GET | `/api/productos/{id}` | Obtener producto por ID (UUID) |
| POST | `/api/productos` | Crear nuevo producto |
| PUT | `/api/productos/{id}` | Actualizar producto |
| DELETE | `/api/productos/{id}` | Eliminar producto |
| GET | `/api/productos/paginado` | Listar con paginación |
| GET | `/api/productos/buscar` | Buscar por nombre |

## Ejemplos de Uso

### Crear Producto

```bash
curl -X POST http://localhost:8080/api/productos \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Laptop",
    "descripcion": "Laptop Gaming",
    "precio": 1500.00,
    "stock": 10
  }'
```

### Listar Productos

```bash
curl http://localhost:8080/api/productos
```

### Buscar con Paginación

```bash
curl "http://localhost:8080/api/productos/paginado?page=0&size=10"
```

### Buscar por Nombre

```bash
curl "http://localhost:8080/api/productos/buscar?nombre=laptop&page=0&size=10"
```

## Estructura del JSON

### Request (ProductoRequestDTO)

```json
{
  "nombre": "string (requerido, 2-100 caracteres)",
  "descripcion": "string (opcional, max 500)",
  "precio": "decimal (requerido, > 0)",
  "stock": "integer (requerido, >= 0)",
  "activo": "boolean (opcional, default true)"
}
```

### Response (ProductoResponseDTO)

```json
{
  "id": "uuid-string",
  "nombre": "string",
  "descripcion": "string",
  "precio": "decimal",
  "stock": "integer",
  "activo": "boolean",
  "fechaCreacion": "datetime",
  "fechaActualizacion": "datetime"
}
```

## Notas

- La base de datos H2 se crea automáticamente al iniciar la aplicación
- Los IDs ahora son UUID en lugar de números secuenciales
- La API acepta solicitudes desde cualquier origen (CORS habilitado)
