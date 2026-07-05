# Plan de Implementación: Mejoras al MVP y Gestión de Estudiantes

Este plan detalla la incorporación de funcionalidades clave para el MVP (Minimum Viable Product):
1. **Creación/Edición General de Estudiantes**: Permitir registrar y editar estudiantes independientemente de si están en un grupo.
2. **Asignación con Confirmación**: Permitir cambiar a un estudiante de grupo usando una bandera para confirmar el traslado si ya pertenece a otro.
3. **Consulta de Estudiantes**: Buscar estudiantes y ver a qué grupo pertenecen.
4. **Control de Acceso Básico**: Restringir endpoints según el rol (Administrador vs Profesor).

---

## User Review Required

> [!IMPORTANT]
> **Lógica de Traslado de Estudiantes**:
> - Si un estudiante ya está en un grupo `A` y se le intenta asignar al grupo `B`:
>   - Si `forzarCambio` es `false` (por defecto): El sistema responderá con un mensaje de advertencia indicando que el estudiante ya tiene un grupo asignado.
>   - Si `forzarCambio` es `true`: El sistema trasladará al estudiante del grupo `A` al grupo `B` sin problemas.

> [!NOTE]
> **Roles y Permisos en el MVP**:
> - **Administrador**: Puede crear/editar/eliminar grupos, asignar profesores, y crear/editar estudiantes.
> - **Profesor**: Puede ver sus propios grupos asignados, consultar estudiantes, registrar la asistencia de sus clases, y agregar estudiantes a su grupo (respetando la regla de confirmación).

---

## Proposed Changes

### 1. Dominio (Domain)
- Modificar comandos de entrada o crear nuevos:
  - `CrearEstudianteCommand` (ya existe, pero añadiremos un caso de uso para edición).
  - `EditarEstudianteCommand` (con campos de nombre, apellido, teléfono, correo, etc.).
  - Modificar `CrearGrupoCommand` y `EditarGrupoCommand` para soportar una bandera `forzarCambioGrupo` (opcional).

### 2. Aplicación (Application)
- **Use Cases & Services**:
  - `EditarEstudianteUseCase` y `EditarEstudianteService`.
  - `ObtenerEstudianteUseCase` y `ObtenerEstudianteService` (para consultar todos los estudiantes y buscar por nombre/apellido, mostrando su grupo).
  - Ajustar `CrearGrupoService` y `EditarGrupoService` para manejar la lógica de confirmación (`forzarCambioGrupo`).

### 3. Infraestructura (Infrastructure)
- **Controladores y DTOs**:
  - En `UsuarioController`, ajustar los métodos de estudiantes.
  - En `GrupoController` y `ClaseController`, añadir validaciones de rol (de forma lógica para el MVP antes de meter Spring Security completo si es necesario).
  - Crear endpoints para listar estudiantes, buscar estudiantes y editar sus datos básicos.

---

## Verification Plan

### Automated Tests
- Compilación del proyecto:
  ```powershell
  mvn clean compile
  ```

### Manual Verification
- Validar el flujo enviando JSONs locales:
  1. Crear un estudiante sin grupo.
  2. Intentar asignar un estudiante que ya pertenece a otro grupo con `forzarCambio: false` (debe fallar).
  3. Intentar el mismo paso con `forzarCambio: true` (debe tener éxito y cambiar el grupoId).
