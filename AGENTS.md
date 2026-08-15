# AGENTS.md — Contexto del Proyecto WebIglesia (ICC San Luis)

## Descripción General
Sistema de gestión para la Iglesia Cristiana Conciliar (ICC) San Luis. API REST backend para administrar usuarios (administradores, profesores, estudiantes), grupos, clases y asistencias.

## Stack Tecnológico
- **Lenguaje**: Java 21
- **Framework**: Spring Boot 4.0.3
- **Build**: Maven
- **Base de datos**: PostgreSQL (hosteada en **Neon** cloud)
- **Autenticación**: JWT (HS512, expiración 2h)
- **Seguridad**: Spring Security con roles (ADMIN, PROFESOR, ESTUDIANTE)
- **Documentación API**: Springdoc OpenAPI (Swagger UI en `/swagger-ui.html`)
- **ORM**: Spring Data JPA + Hibernate (`ddl-auto=update`)
- **Utilidades**: Lombok, spring-dotenv

## Arquitectura
Arquitectura Hexagonal (Puertos y Adaptadores):

```
src/main/java/icc/sanluis/webiglesia/
├── domain/          # Modelos de dominio, puertos (interfaces)
├── application/     # Casos de uso (interfaces + servicios)
└── infrastructure/  # Adaptadores (controllers, repos, security, config)
```

### Paquetes Principales
- `domain.usuario.model` — Modelos: Usuario, Persona (abstract), Administrador, Profesor, Estudiante, Grupo, Clase, Asistencia, Rol (enum)
- `domain.usuario.ports.in` — Comandos de entrada (requests)
- `domain.usuario.ports.out` — Puertos de salida (repositorios, PasswordHasher)
- `application.usuario.usecases` — Interfaces de casos de uso
- `application.usuario.services` — Implementaciones de servicios
- `infrastructure.adapters.in.controllers` — REST controllers + DTOs
- `infrastructure.adapters.out.persistence` — Entidades JPA, repositorios Spring Data, mappers, adapters JPA
- `infrastructure.adapters.out.security` — JWT, BCrypt
- `infrastructure.config` — SecurityConfig, CORS, AdminSeeder, wiring de beans

## Modelos de Dominio y Relaciones

```
Persona (abstract, @MappedSuperclass)
  ├── Administrador ──1:1──► Usuario (mismo UUID como PK)
  ├── Profesor ──1:1──► Usuario (mismo UUID como PK)
  └── Estudiante ──1:1──► Usuario (mismo UUID como PK)
                              └── Estudiante.grupoId ──FK──► Grupo.id

Grupo
  ├── ──M:N──► Profesor (tabla intermedia "grupo_profesores")
  └── ──1:N──► Estudiante (FK grupo_id en estudiante)

Clase
  ├── grupoId (UUID, columna simple, no FK JPA)
  └── ──1:N──► Asistencia (cascade ALL, orphanRemoval)

Asistencia
  ├── ──N:1──► Clase
  └── ──N:1──► Estudiante

Usuario
  └── ──@ElementCollection──► usuario_roles (tabla intermedia: usuario_id, rol)
```

### Enum Rol
Valores: `PROFESOR`, `ADMIN`, `ESTUDIANTE`

## Entidades JPA (tablas generadas por Hibernate)

| Entidad | Tabla | Notas |
|---|---|---|
| `UsuarioEntity` | `usuario` | PK: UUID, `@ElementCollection` genera `usuario_roles` |
| `Persona` | (MappedSuperclass) | Hereda ID, nombre, apellido, telefono, fecha_de_nacimiento, correo |
| `AdministradorEntity` | `administrador` | Extiende Persona, `@OneToOne` con Usuario |
| `ProfesorEntity` | `profesor` | Extiende Persona, `@OneToOne` con Usuario |
| `EstudianteEntity` | `estudiante` | Extiende Persona, tiene `grupo_id` FK |
| `GrupoEntity` | `grupo` | `@ManyToMany` genera `grupo_profesores`, `@OneToMany` con Estudiante |
| `ClaseEntity` | `clase` | `grupoId` como columna simple (no FK JPA) |
| `AsistenciaEntity` | `asistencia` | `@ManyToOne` con Clase y Estudiante |

## ⚠️ IMPORTANTE: Neon PostgreSQL — PRIMARY KEY requerido

**Neon exige que TODAS las tablas tengan PRIMARY KEY para poder ejecutar UPDATE/DELETE.**

Las tablas generadas por `@ManyToMany` y `@ElementCollection` de Hibernate **no crean PRIMARY KEY automáticamente**. Si necesitás borrar registros de estas tablas, primero agregá la PK en Neon:

```sql
-- Tabla de roles de usuario (generada por @ElementCollection)
ALTER TABLE usuario_roles
ADD CONSTRAINT pk_usuario_roles PRIMARY KEY (usuario_id, rol);

-- Tabla de relación grupo-profesores (generada por @ManyToMany)
ALTER TABLE grupo_profesores
ADD CONSTRAINT pk_grupo_profesores PRIMARY KEY (grupo_id, profesor_id);
```

Estas migraciones se deben hacer manualmente ya que no hay Flyway ni Liquibase configurado.

## Endpoints API

### Autenticación
| Método | Ruta | Auth | Descripción |
|---|---|---|---|
| `POST` | `/usuarios/login` | Público | Login, retorna JWT |

### Usuarios (`/usuarios`)
| Método | Ruta | Auth | Descripción |
|---|---|---|---|
| `GET` | `/usuarios/{id}` | ADMIN o propio | Obtener usuario |
| `PUT` | `/usuarios/editar/{id}` | ADMIN o propio | Editar usuario |
| `PATCH` | `/usuarios/cambiar-estado/{id}` | ADMIN | Activar/desactivar |
| `PATCH` | `/usuarios/{id}/roles` | ADMIN o propio | Asignar roles |

### Administradores (`/administradores`)
| Método | Ruta | Auth | Descripción |
|---|---|---|---|
| `POST` | `/administradores` | ADMIN | Crear |
| `GET` | `/administradores` | ADMIN | Listar todos |
| `GET` | `/administradores/{id}` | ADMIN | Obtener por ID |

### Profesores (`/profesores`)
| Método | Ruta | Auth | Descripción |
|---|---|---|---|
| `GET` | `/profesores` | ADMIN | Listar (?activo=true/false/all) |
| `GET` | `/profesores/{id}` | ADMIN o propio | Obtener por ID |
| `GET` | `/profesores/{id}/grupos` | ADMIN o propio | Grupos del profesor |
| `POST` | `/profesores` | ADMIN | Crear (auto-genera Usuario) |
| `PUT` | `/profesores/{id}` | ADMIN o propio | Editar |
| `POST` | `/profesores/{profesorId}/estudiantes` | ADMIN o propio | Crear estudiante |
| `POST` | `/profesores/{profesorId}/estudiantes/multiples` | ADMIN o propio | Crear varios estudiantes |

### Estudiantes (`/estudiantes`)
| Método | Ruta | Auth | Descripción |
|---|---|---|---|
| `POST` | `/estudiantes` | ADMIN o PROFESOR | Crear |
| `PUT` | `/estudiantes/{id}` | ADMIN o PROFESOR | Editar |
| `GET` | `/estudiantes` | ADMIN o PROFESOR | Listar (?query=, ?activo=true/false/all) |
| `GET` | `/estudiantes/{id}` | ADMIN o PROFESOR | Obtener por ID |

### Grupos (`/grupos`)
| Método | Ruta | Auth | Descripción |
|---|---|---|---|
| `POST` | `/grupos` | ADMIN | Crear (1-2 profesores) |
| `PUT` | `/grupos/{id}` | ADMIN o profesor asignado | Editar |
| `GET` | `/grupos` | ADMIN o PROFESOR | Listar (?query=) |
| `GET` | `/grupos/{id}` | ADMIN o profesor asignado | Obtener por ID |
| `DELETE` | `/grupos/{id}` | ADMIN | Eliminar |

### Clases (`/grupos/{grupoId}/clases`, `/clases/{id}`)
| Método | Ruta | Auth | Descripción |
|---|---|---|---|
| `POST` | `/grupos/{grupoId}/clases` | ADMIN o profesor del grupo | Registrar asistencia |
| `GET` | `/grupos/{grupoId}/clases` | ADMIN o profesor del grupo | Clases del grupo |
| `GET` | `/clases/{id}` | ADMIN o profesor de la clase | Obtener clase por ID |

## Variables de Entorno (.env)

| Variable | Propósito |
|---|---|
| `DATABASE_URL` | URL JDBC de PostgreSQL (Neon) |
| `frontend_url` | Origen CORS permitido (default: `http://localhost:5173`) |
| `ADMIN_USERNAME` | Username del admin seed inicial |
| `ADMIN_PASSWORD` | Password del admin seed inicial |
| `JWT_SECRET` | Clave HMAC para JWT (HS512) |
| `JWT_EXPIRATION_MS` | Expiración del token (7200000 = 2h) |

**NOTA**: `.env` y `application.properties` están en `.gitignore`. Usar `spring-dotenv` para cargar `.env` como properties de Spring.

## Configuración Hibernate
```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

## Docker
- Multi-stage build: Eclipse Temurin 21 JDK → JRE
- Puerto configurable via `$PORT`
- Compatible con Render

## Convenciones del Proyecto
- **No hay sistema de migraciones** (sin Flyway/Liquibase). Los cambios de schema se manejan con `ddl-auto=update` + SQL manual en Neon.
- **Beans**: No se usa `@Service`. Todos los casos de uso se wiring manualmente en `UsuarioUseCaseConfig` con `@Bean`.
- **IDs**: Todos los IDs son `UUID` generados en Java (no en DB).
- **Passwords**: Hasheados con BCrypt.
- **Username generation**: Automático basado en nombre + día/mes de nacimiento (ej: `juan158`).
- **Frontend**: Separado (probablemente Vite/React en `http://localhost:5173`). Este proyecto es solo el backend REST.

## Archivos Clave para Modificar
- `infrastructure.config.UsuarioUseCaseConfig` — Wiring de todos los use cases
- `infrastructure.config.SecurityConfig` — Reglas de seguridad y endpoints públicos
- `infrastructure.config.CorsConfig` — Orígenes permitidos
- `infrastructure.config.AdminSeeder` — Seed del admin inicial
- `infrastructure.adapters.out.persistence.entities.*` — Entidades JPA (definición de tablas)
