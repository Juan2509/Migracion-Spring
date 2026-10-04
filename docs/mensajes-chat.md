# Mensajes de chat: V45

ChatMessage implementa chat_messages en los paquetes chatmessage de domain,
application e infrastructure desde src/main/java de cada módulo en Java Projects.
V45 conserva su contenido.

| Columna SQL | Campo JSON | Regla |
| --- | --- | --- |
| id | id | UUID generado al registrar |
| conversation_id | conversationId | UUID obligatorio, referencia a chat_conversations |
| message_type_id | messageTypeId | UUID obligatorio, referencia a message_types |
| participant_id | participantId | UUID obligatorio, referencia a chat_participants |
| content | content | JSONB obligatorio |
| metadata | metadata | JSONB obligatorio |
| created_at | createdAt | TIMESTAMP / LocalDateTime, conservado al actualizar |

No se añade updatedAt ni unicidad. El dominio usa IDs tipados, mientras JPA guarda
UUID sin asociaciones entre agregados. Registro y actualización comprueban las
tres referencias antes de guardar; las FK de PostgreSQL protegen la integridad.
No se exige que el participante pertenezca a la misma conversación, porque V45
solo declara las tres FK independientes.

## JSON y API

Ruta base: /api/chat-messages. POST registra (201), GET lista (200), GET /{id}
consulta (200), PUT /{id} actualiza (200) y DELETE /{id} elimina (204).
Un ID o referencia inexistente devuelve 404; los conflictos de integridad devuelven 409.

POST y PUT reciben, por ejemplo:

```json
{
  "conversationId": "00000000-0000-0000-0000-000000000001",
  "messageTypeId": "00000000-0000-0000-0000-000000000002",
  "participantId": "00000000-0000-0000-0000-000000000003",
  "content": { "text": "Hola" },
  "metadata": { "source": "web" }
}
```

Reemplazar los IDs por registros existentes. La respuesta añade id y createdAt,
y conserva content y metadata como valores JSON, sin doble codificación.
Se admiten objetos, arreglos y valores escalares; el JSON literal null es distinto
de SQL NULL. No se impone una estructura concreta a estos documentos.

Para mantener domain y application independientes de Jackson, los documentos se
representan allí como texto JSON no nulo. El adaptador REST usa JsonNode al recibir
y devolver datos; JPA usa String con JdbcTypeCode(JSON) y columnas JSONB.
PostgreSQL valida el documento al persistir. Quien invoque los casos de uso
directamente debe proporcionar texto JSON válido.

PUT reemplaza los campos editables y mantiene id y createdAt. El dominio registra
el evento de actualización con occurredOn, sin añadir una columna de auditoría.
Restaurar desde JPA no produce eventos; los eventos son en memoria y eliminación
devuelve su evento.

## Verificación

Las pruebas cubren CRUD, mensajes repetidos, auditoría, referencias inexistentes
al registrar y actualizar, actualización inválida sin cambios parciales, conversión
JPA y serialización REST de JSON estructurado. La ejecución HTTP y la validación
real de JSONB en PostgreSQL siguen pendientes de configurar la conexión.
