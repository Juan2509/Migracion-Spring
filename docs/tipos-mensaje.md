# Tipos de mensaje: V44

MessageType implementa message_types en los paquetes messagetype de domain,
application e infrastructure. Las fuentes se editan desde src/main/java de cada
módulo en Java Projects. V44 conserva su contenido.

| Columna SQL | Campo JSON | Regla |
| --- | --- | --- |
| id | id | UUID generado al registrar |
| name_type | nameType | Obligatorio, máximo 50 caracteres; admite repetidos |
| created_at | createdAt | TIMESTAMP / LocalDateTime, conservado al actualizar |
| updated_at | updatedAt | TIMESTAMP / LocalDateTime, actualizado por el dominio |

No se añaden code, active, UNIQUE, relaciones ni valores iniciales.

## API

Ruta base: /api/message-types. POST registra (201), GET lista (200),
GET /{id} consulta (200), PUT /{id} actualiza (200), DELETE /{id} elimina (204).
Un ID inexistente devuelve 404; los conflictos de integridad devuelven 409.

POST y PUT reciben:

```json
{ "nameType": "Texto" }
```

La respuesta añade id, createdAt y updatedAt. ID y createdAt se conservan al
actualizar. Registrar y actualizar permiten nombres repetidos.

## Capas y verificación

Domain contiene agregado, ID, eventos y puerto sin Spring/JPA. Application
contiene comandos, respuesta y cinco casos de uso CRUD. Infrastructure aporta
REST, validación, configuración y persistencia. Restaurar desde JPA no produce
eventos nuevos; creación y actualización los registran en memoria y eliminación
devuelve su evento.

Las pruebas cubren CRUD, registros inexistentes, auditoría, nombres repetidos,
límites de longitud y caracteres Unicode, actualización inválida sin cambios
parciales y conversión JPA. La ejecución HTTP y la validación en PostgreSQL
siguen pendientes de configurar la conexión.
