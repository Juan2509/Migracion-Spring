# Errores de IA: V47

ChatAiRunError implementa chat_ai_run_errors en los paquetes chatairunerror de
domain, application e infrastructure. Las fuentes se editan desde src/main/java
de cada módulo en Java Projects. V47 conserva su contenido.

| Columna SQL | Campo JSON | Regla |
| --- | --- | --- |
| id | id | UUID generado al registrar |
| ai_run_id | aiRunId | UUID obligatorio, referencia a chat_ai_runs |
| error_message | errorMessage | TEXT obligatorio, sin límite artificial |
| error_code | errorCode | Obligatorio, máximo 80 caracteres |
| provider_error_id | providerErrorId | Obligatorio, máximo 120 caracteres |
| created_at | createdAt | TIMESTAMP / LocalDateTime, conservado al actualizar |

No se añade updatedAt ni unicidad; puede haber varios errores para una ejecución.
El dominio usa ChatAiRunId y JPA guarda UUID sin asociaciones entre agregados.
Registro y actualización consultan el puerto de ejecuciones antes de guardar;
la FK de PostgreSQL también protege la integridad. No se añaden reglas de estado
ni se dispara una nueva ejecución de IA.

## API

Ruta base: /api/chat-ai-run-errors. POST registra (201), GET lista (200),
GET /{id} consulta (200), PUT /{id} actualiza (200) y DELETE /{id} elimina (204).
Un ID o ejecución inexistente devuelve 404; los conflictos de integridad devuelven 409.

POST y PUT reciben:

```json
{
  "aiRunId": "00000000-0000-0000-0000-000000000001",
  "errorMessage": "El proveedor no respondió",
  "errorCode": "TIMEOUT",
  "providerErrorId": "request-123"
}
```

Reemplazar aiRunId por una ejecución existente. La respuesta añade id y createdAt.
PUT sustituye los campos editables y conserva id y createdAt. La actualización
registra un evento con occurredOn sin añadir una columna de auditoría.

## Capas y pruebas

Domain contiene agregado, ID, eventos y puerto sin Spring/JPA; application contiene
comandos, respuesta y cinco casos de uso. Infrastructure aporta REST, validación,
configuración y persistencia. Restaurar desde JPA no produce eventos; los eventos
de creación y actualización son en memoria y eliminación devuelve su evento.

Las pruebas cubren CRUD, auditoría, errores repetidos, referencia inexistente,
campos obligatorios, textos largos, límites de longitud y Unicode, actualización
inválida sin cambios parciales y conversión JPA. La ejecución HTTP y la validación
en PostgreSQL siguen pendientes de configurar la conexión.
