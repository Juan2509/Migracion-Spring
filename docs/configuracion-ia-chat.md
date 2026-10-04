# Configuración de IA del chat: V43

ChatConversationAiSettings implementa chat_conversation_ai_settings en los paquetes
chatconversationaisettings de domain, application e infrastructure, desde
src/main/java de cada módulo en Java Projects. V43 conserva su contenido.

| Columna SQL | Campo JSON | Regla |
| --- | --- | --- |
| id | id | UUID generado al registrar |
| conversation_id | conversationId | UUID obligatorio, referencia a chat_conversations |
| ai_enabled | aiEnabled | Boolean obligatorio |
| default_model_id | defaultModelId | UUID obligatorio, referencia a ai_models |
| created_at | createdAt | TIMESTAMP / LocalDateTime, conservado al actualizar |
| updated_at | updatedAt | TIMESTAMP / LocalDateTime, actualizado por el dominio |

El dominio usa ChatConversationId y AiModelId; JPA guarda UUID sin asociaciones
entre agregados. Registro y actualización consultan ambos puertos antes de guardar.
Las FK de PostgreSQL también protegen la integridad.

El modelo sigue siendo obligatorio y debe existir aunque aiEnabled sea false.
No se añade una condición sobre isActive del modelo ni una restricción UNIQUE:
puede haber varias configuraciones para una conversación. Este contexto almacena
configuración; no ejecuta llamadas a proveedores de IA.

## API

Ruta base: /api/chat-conversation-ai-settings. POST registra (201), GET lista
(200), GET /{id} consulta (200), PUT /{id} actualiza (200), DELETE /{id} elimina
(204). Un ID o referencia inexistente devuelve 404; los conflictos de integridad
devuelven 409.

POST y PUT reciben:

```json
{
  "conversationId": "00000000-0000-0000-0000-000000000001",
  "aiEnabled": false,
  "defaultModelId": "00000000-0000-0000-0000-000000000002"
}
```

Reemplazar los IDs del ejemplo por registros existentes. La respuesta añade id,
createdAt y updatedAt. PUT sustituye los campos editables y conserva id y createdAt.

## Capas y verificación

Domain contiene agregado, ID, eventos y puerto sin Spring/JPA. Application contiene
comandos, respuesta y cinco casos de uso CRUD. Infrastructure aporta REST,
validación, configuración y persistencia. Restaurar desde JPA no produce eventos;
creación y actualización los registran en memoria y eliminación devuelve su evento.

Las pruebas cubren CRUD, auditoría, configuraciones repetidas, campos obligatorios,
modelo con isActive false, referencias inexistentes al registrar y actualizar,
actualización inválida sin cambios parciales y conversión JPA.
La ejecución HTTP y la validación en PostgreSQL siguen pendientes de conexión.
