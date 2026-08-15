---
name: update-agents-md
description: 'Mantiene AGENTS.md actualizado con cambios en endpoints, modelos, seguridad y configuración. Usar después de modificar controllers, entidades, SecurityConfig o cualquier cambio que afecte la documentación del proyecto.'
---

# Skill: Update AGENTS.md

Mantiene el archivo `AGENTS.md` sincronizado con el estado actual del código.

## Cuándo usar

Ejecutar este skill cuando se haya completado uno de estos cambios:

- **Endpoints**: Nuevo endpoint o modificación en controller (método, ruta, auth)
- **Modelos**: Cambios en entidades JPA, relaciones o enums
- **Seguridad**: Cambios en `@PreAuthorize`, SecurityConfig, AuthorizationService
- **Config**: Nuevas variables de entorno, cambio en configuración
- **Convenciones**: Nuevo patrón o convención adoptada

## Flujo

1. Leer `AGENTS.md` actual
2. Identificar qué sección fue afectada por el cambio
3. Actualizar SOLO esa sección, preservando el resto
4. Verificar que el formato Markdown sea consistente (tablas, encabezados)

## Secciones y qué buscar

| Sección de AGENTS.md | Archivos fuente |
|---|---|
| Endpoints API | `infrastructure/adapters/in/controllers/**/*.java` |
| Modelos de dominio | `domain/usuario/model/*.java` |
| Seguridad | `infrastructure/config/SecurityConfig.java`, `infrastructure/adapters/in/security/AuthorizationService.java` |
| Variables de entorno | `.env`, `infrastructure/config/*.java` (constantes) |
| Entidades JPA | `infrastructure/adapters/out/persistence/entities/*.java` |

## Reglas

- No borrar información no relacionada con el cambio
- Mantener el formato de tablas Markdown existente
- Si hay ambigüedad, conservar la información actual
- Usar español técnico, mismo estilo que el documento existente
