# Escalaciones de chat: V50

ChatEscalation implementa chat_escalations en los paquetes chatescalation de
domain, application e infrastructure. Las fuentes se editan desde src/main/java
de cada módulo en Java Projects. La migración V50 conserva su contenido.

| Columna SQL | Campo JSON | Regla |
| --- | --- | --- |
| id | id | UUID generado al registrar |
| conversation_id | conversationId | UUID obligatorio; referencia chat_conversations |
| status_id | statusId | UUID obligatorio; referencia escalations_statuses |
| from_ai | fromAi | Boolean obligatorio; admite true y false |
| reason | reason | Texto obligatorio, sin límite VARCHAR |
| created_at | createdAt | TIMESTAMP / LocalDateTime; conservado al actualizar |

## API

Ruta base: `/api/chat-escalations`. POST registra (201), GET lista (200),
GET /{id} consulta (200), PUT /{id} actualiza (200), DELETE /{id} elimina (204).
POST y PUT reciben, utilizando IDs existentes:

```json
{
  "conversationId": "11111111-1111-1111-1111-111111111111",
  "statusId": "22222222-2222-2222-2222-222222222222",
  "fromAi": true,
  "reason": "La conversación requiere revisión profesional"
}
```

La respuesta añade id y createdAt. Una escalación, conversación o estado
inexistente devuelve 404. Los conflictos de integridad devuelven 409.
Los casos de uso verifican ambas referencias antes de guardar o actualizar.
Se permiten varias escalaciones por conversación y estado según las FK de V50.

## Capas y verificación

Domain contiene agregado, ID, eventos y puerto sin Spring/JPA. Application
contiene comandos, respuesta y cinco casos de uso CRUD. Infrastructure aporta
REST, validación, configuración y persistencia JPA. La creación y actualización
registran eventos en memoria; restaurar desde JPA no genera eventos. El caso de
uso de eliminación devuelve su evento.

No se añade updatedAt ni automatización de cambios de estado o asignaciones.
Las pruebas cubren CRUD, referencias inexistentes sin guardado ni modificación
parcial, valores false/true, motivos largos, auditoría, registros repetidos y
conversión JPA. La ejecución HTTP y la validación real en PostgreSQL siguen
pendientes de configurar la conexión.
