# Estados de ejecución de IA: V37

AiRunStatus implementa ai_run_statuses en los paquetes airunstatus de domain,
application e infrastructure. Las fuentes se editan desde src/main/java de
cada módulo en Java Projects; V37 conserva su contenido.

| Columna SQL | Campo Java / JSON | Regla |
| --- | --- | --- |
| id | id | UUID generado al registrar |
| name_status | nameStatus | Obligatorio, máximo 50 caracteres, admite repetidos |
| created_at | createdAt | TIMESTAMP / LocalDateTime; conservado al actualizar |
| updated_at | updatedAt | TIMESTAMP / LocalDateTime; actualizado por el dominio |

No se añaden code, active, relaciones, valores iniciales ni reglas de transición.

## API

Ruta base: /api/ai-run-statuses.
POST registra (201), GET lista (200), GET /{id} consulta (200), PUT /{id} actualiza
(200) y DELETE /{id} elimina (204). Un ID inexistente devuelve 404; los conflictos
de integridad de la base se traducen a 409.

POST y PUT reciben:

```json
{ "nameStatus": "Pendiente" }
```

La respuesta contiene id, nameStatus, createdAt y updatedAt. El ID y createdAt
se conservan al actualizar. Los nombres repetidos se permiten tanto al registrar
como al actualizar.

Domain contiene agregado, ID, eventos y puerto sin Spring/JPA. Application tiene
los cinco casos de uso CRUD y infrastructure aporta REST, validaciones,
configuración y persistencia. La restauración JPA no registra eventos nuevos;
creación y actualización los registran en memoria, y eliminación devuelve su evento.

Las pruebas cubren CRUD, registros inexistentes, nombres repetidos, límites
de longitud y caracteres Unicode, auditoría, actualización inválida sin cambios
parciales y conversión JPA. Desde la raíz:

```powershell
.\mvnw.cmd -o -Dtest=*Test,!RangelApplicationTests -Dsurefire.failIfNoSpecifiedTests=false test
```

La validación HTTP/PostgreSQL sigue pendiente de configurar la conexión.
