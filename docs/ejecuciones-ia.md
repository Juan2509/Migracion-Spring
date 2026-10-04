# Ejecuciones de IA: V46

ChatAiRun implementa chat_ai_runs en los paquetes chatairun de domain,
application e infrastructure, desde src/main/java de cada módulo en Java Projects.
V46 conserva su contenido.

| Columna SQL | Campo JSON | Regla |
| --- | --- | --- |
| id | id | UUID generado al registrar |
| conversation_id | conversationId | UUID obligatorio, referencia a chat_conversations |
| message_id | messageId | UUID obligatorio, referencia a chat_messages |
| model_id | modelId | UUID obligatorio, referencia a ai_models |
| ai_run_status_id | aiRunStatusId | UUID obligatorio, referencia a ai_run_statuses |
| created_at | createdAt | TIMESTAMP / LocalDateTime, conservado al actualizar |
| updated_at | updatedAt | TIMESTAMP / LocalDateTime, actualizado por el dominio |

El dominio usa ChatConversationId, ChatMessageId, AiModelId y AiRunStatusId.
JPA guarda UUID sin asociaciones entre agregados. Registro y actualización
consultan los cuatro puertos antes de guardar; las FK de PostgreSQL también
protegen la integridad.

No se añade unicidad, una condición sobre isActive del modelo, transiciones de
estado ni una regla que exija que el mensaje pertenezca a la conversación.
V46 solo declara las cuatro FK independientes. Este contexto registra ejecuciones,
sin iniciar tareas ni invocar proveedores de IA.

## API

Ruta base: /api/chat-ai-runs. POST registra (201), GET lista (200), GET /{id}
consulta (200), PUT /{id} actualiza (200) y DELETE /{id} elimina (204).
Un ID o referencia inexistente devuelve 404; los conflictos de integridad devuelven 409.

POST y PUT reciben:

```json
{
  "conversationId": "00000000-0000-0000-0000-000000000001",
  "messageId": "00000000-0000-0000-0000-000000000002",
  "modelId": "00000000-0000-0000-0000-000000000003",
  "aiRunStatusId": "00000000-0000-0000-0000-000000000004"
}
```

Reemplazar los IDs por registros existentes. La respuesta añade id, createdAt
y updatedAt. PUT sustituye las cuatro referencias y conserva id y createdAt.
Se permiten varias ejecuciones con las mismas referencias.

## Capas y pruebas

Domain contiene agregado, ID, eventos y puerto sin Spring/JPA. Application
contiene comandos, respuesta y cinco casos de uso. Infrastructure aporta REST,
validación, configuración y persistencia. Restaurar desde JPA no produce eventos;
creación y actualización los registran en memoria y eliminación devuelve su evento.

Las pruebas cubren CRUD, auditoría, ejecuciones repetidas, campos obligatorios,
las cuatro referencias inexistentes al registrar y actualizar, actualización
inválida sin cambios parciales y conversión JPA. La ejecución HTTP y la validación
en PostgreSQL siguen pendientes de configurar la conexión.
