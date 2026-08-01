# Informe: cambios de API por la implementación de seguridad (JWT + roles)

Este documento resume qué cambia en el backend para que el equipo de frontend adapte la app. Aplica a partir del branch `funcionalidades` (commits de seguridad de agosto 2026).

## 1. Login ahora devuelve un token

`POST /usuarios/login` sigue recibiendo `{ "nombreusuario": "...", "contrasena": "..." }`, pero la respuesta ahora incluye un campo `token`:

```json
{
  "id": "uuid",
  "username": "admin",
  "rol": "ADMIN",
  "activo": true,
  "token": "eyJhbGciOiJIUzUxMiJ9..."
}
```

El frontend debe guardar ese `token` (localStorage, memoria, etc.) y enviarlo en **todas** las demás peticiones a la API con el header:

```
Authorization: Bearer <token>
```

**El token expira en 2 horas y no hay refresh token.** Cuando expire, cualquier request devolverá 401 y el usuario debe volver a hacer login. No hay forma de renovar el token sin credenciales.

## 2. Login bloqueado para usuarios con rol ESTUDIANTE

Los estudiantes son solo registros de datos gestionados por ADMIN/PROFESOR, nunca inician sesión. Si por error se intenta loguear con una cuenta de rol `ESTUDIANTE`, la API responde:

```json
{ "message": "Los estudiantes no pueden iniciar sesión", "errors": {} }
```
`HTTP 403`

## 3. Nuevos códigos de error a manejar en el frontend

| Código | Cuándo | Body |
|---|---|---|
| `401` | Falta el header `Authorization`, el token es inválido o expiró | `{"message":"No autenticado","errors":{}}` |
| `403` | El usuario está autenticado pero su rol no tiene permiso para esa acción, o intenta acceder a un recurso que no es suyo | `{"message":"No tiene permisos para esta acción","errors":{}}` |

Recomendado: si el frontend recibe un 401 en cualquier endpoint, debe limpiar la sesión local y redirigir a la pantalla de login.

## 4. Endpoint eliminado

**`POST /usuarios/crear` ya no existe** (devuelve 404). Permitía crear un `Usuario` suelto con cualquier rol (incluido ADMIN) sin restricción — era redundante y un riesgo de seguridad. Los flujos de creación correctos y ya existentes son:
- `POST /administradores` (solo ADMIN)
- `POST /profesores` (solo ADMIN)
- `POST /estudiantes` (ADMIN o PROFESOR)

Si el frontend usaba `/usuarios/crear` en algún flujo, debe migrarse a uno de los anteriores.

## 5. Cambio de contrato: `PUT /usuarios/editar/{id}`

Antes `nombreusuario` y `contrasena` eran ambos obligatorios en cada edición (forzaba reenviar la contraseña aunque solo se quisiera cambiar el username). Ahora **ambos campos son opcionales**: solo se actualiza lo que venga con valor no vacío.

```json
{ "nombreusuario": "nuevo_username" }
```
Ya no requiere incluir `contrasena`. Si se omite o va vacía, la contraseña actual no se toca.

## 6. Matriz de permisos por endpoint

`ADMIN` = solo administradores. `PROFESOR` = el profesor dueño del recurso (o su propio perfil). Cualquier caso no listado como público requiere `Authorization: Bearer <token>` válido.

| Endpoint | Quién puede |
|---|---|
| `POST /usuarios/login` | Público (nadie necesita token) |
| `GET /usuarios/{id}` | ADMIN o el propio usuario |
| `PUT /usuarios/editar/{id}` | ADMIN o el propio usuario |
| `PATCH /usuarios/cambiar-estado/{id}` | Solo ADMIN |
| `POST /administradores`, `GET /administradores`, `GET /administradores/{id}` | Solo ADMIN |
| `GET /profesores` (listar todos) | Solo ADMIN |
| `GET /profesores/{id}`, `GET /profesores/{id}/grupos`, `PUT /profesores/{id}` | ADMIN o el propio profesor |
| `POST /profesores` | Solo ADMIN |
| `POST /profesores/{profesorId}/estudiantes[/multiples]` | ADMIN o el propio profesor |
| `POST /estudiantes`, `PUT /estudiantes/{id}`, `GET /estudiantes`, `GET /estudiantes/{id}` | ADMIN o cualquier PROFESOR |
| `POST /grupos`, `PUT /grupos/{id}`, `DELETE /grupos/{id}` | Solo ADMIN |
| `GET /grupos` (listar todos) | ADMIN o cualquier PROFESOR |
| `GET /grupos/{id}` | ADMIN o el profesor asignado a ese grupo |
| `POST /grupos/{grupoId}/clases`, `GET /grupos/{grupoId}/clases` | ADMIN o el profesor asignado a ese grupo |
| `GET /clases/{id}` | ADMIN o el profesor del grupo de esa clase |

## 7. Rutas que siguen públicas (sin token)

- `POST /usuarios/login`
- `/swagger-ui/**`, `/v3/api-docs/**` (documentación de la API)
- `/actuator/**`

## 8. Nada cambia en formato de request/response salvo lo indicado arriba

Los demás DTOs (creación/edición de administradores, profesores, estudiantes, grupos, clases) no cambian de forma. Solo cambia que ahora todas las peticiones (salvo login) necesitan el header `Authorization: Bearer <token>`.
