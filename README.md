# ForoHub

**ForoHub** es una aplicación construida con **Spring Boot** que simula el funcionamiento backend de un foro donde los usuarios deben autenticarse para gestionar los tópicos de discusión.  
La aplicación utiliza **Spring Security** para la gestión de autenticación y autorización mediante **JWT (JSON Web Tokens)**, y está respaldada por **PostgreSQL** como base de datos relacional.

---
La aplicación utilizó:
- **Spring Security** para la autenticación y autorización mediante **JWT (JSON Web Tokens)**.
- **PostgreSQL** como base de datos relacional.
- **Flyway** para la gestión de migraciones y carga de datos iniciales.
- **Insomnia** como herramienta para pruebas de API.

## Funcionalidades principales

- Registro y autenticación de usuarios con contraseñas cifradas con **BCrypt**.  
  *(por el momento se utiliza un usuario base cargado automáticamente)*
- Inicio de sesión mediante **JWT**, con manejo seguro de sesiones.
- Creación, listado y gestión de **tópicos de discusión** (foro).
- Migraciones de base de datos automatizadas con **Flyway**.

---

## Configuración inicial

Para ejecutar la aplicación en tu entorno local, es necesario configurar la base de datos y las variables de entorno requeridas.

### 1. Base de datos

Asegúrate de tener instalado **PostgreSQL** y crea la base de datos inicial:

```sql
CREATE DATABASE foroHub;
```

## Variables de entorno
Antes de ejecutar la aplicación, define las siguientes variables de entorno en tu sistema (o en un archivo .env si lo gestionas con Docker/IntelliJ/VSCode):

- **DB_HOST** → dirección del servidor de PostgreSQL (ejemplo: localhost:5432)

- **DB_USER** → usuario de la base de datos

- **DB_PASSWORD** → contraseña del usuario de la base de datos

- **TOKEN_SECRET** → (opcional) clave secreta para la generación de JWT.
Si no defines esta variable, se utilizará el valor por defecto **12345678**.

## ▶️ Ejecución y pruebas

La aplicación corre por defecto en:
```
http://localhost:8080
```
---

### 🔓 Rutas públicas (no requieren autenticación)
- **POST** `/login` → para autenticarse y obtener un token JWT
- **GET** `/topicos` → trae el listado de tópicos cargados automáticamente con Flyway

---

### 🔐 Rutas protegidas (requieren autenticación con JWT)
- **POST** `/topicos` → crear un nuevo tópico
- **PUT** `/topicos/{id}` → editar un tópico existente
- **DELETE** `/topicos/{id}` → eliminar un tópico

---

### 🔑 Cómo obtener el token
1. Abre **Insomnia** (o cualquier cliente API).
2. Crea una petición:
   - Ruta: http://localhost:8080/login
   - Método: POST
   - Body → JSON:
```json
{
"email": "test@mail.com",
"password": "123456"
}
```
3. La respuesta será un **token JWT** que deberás copiar.
4. En las siguientes peticiones protegidas, agrega en la pestaña **Auth - Bearer Token**: 
```
TOKEN: tu_token_generado
```
---

### 📝 Ejemplo de prueba de creación de un tópico
1. Autentícate y consigue el token como se explicó antes.
2. Haz una petición en **Insomnia**:
   - Ruta: http://localhost:8080/topicos
   - Método: POST
   - Body → JSON:
```json
{
  "titulo": "Consulta sobre Querys",
  "mensaje": "Procedimiento para hacer JOIN de tres tablas",
  "fechaCreacion": "2025-08-13T17:20:59.704556",
  "status": "ABIERTO",
  "autor": "Angela Martinez",
  "curso": "Spring Data JPA"
} 
```
✅ Esto insertará un nuevo tópico en la base de datos y podrás visualizarlo al consultar con:
```
GET - http://localhost:8080/topicos
```