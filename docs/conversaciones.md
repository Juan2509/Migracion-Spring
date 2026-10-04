# Conversaciones: V41

ChatConversation implementa chat_conversations en los paquetes chatconversation
de domain, application e infrastructure. Las fuentes se editan desde src/main/java
de cada módulo en Java Projects. V41 conserva su contenido.

| Columna SQL | Campo JSON | Tipo y regla |
| --- | --- | --- |
| id | id | UUID generado al registrar |
| conversation_status_id | conversationStatusId | UUID obligatorio; referencia a conversations_statuses |
| priority_id | priorityId | UUID obligatorio; referencia a priorities |
| last_message_at | lastMessageAt | LocalDateTime opcional |
| closed | closed | Boolean opcional |
| closed_at | closedAt | LocalDateTime opcional |
| closed_by | closedBy | UUID opcional, sin FK declarada |
| created_at | createdAt | LocalDateTime obligatorio; conservado al actualizar |
| updated_at | updatedAt | LocalDateTime obligatorio; actualizado por el dominio |

Todas las fechas conservan TIMESTAMP. El dominio usa ConversationStatusId y
PriorityId, mientras JPA guarda UUID sin asociaciones entre agregados.
Los casos de registro y actualización consultan los puertos de ambos catálogos
antes de guardar. Las FK de PostgreSQL también protegen la integridad.

## API

Ruta base: /api/chat-conversations. POST registra (201), GET lista (200),
GET /{id} consulta (200), PUT /{id} actualiza (200), DELETE /{id} elimina (204).
Un ID o referencia inexistente devuelve 404; un conflicto de integridad devuelve 409.

POST y PUT reciben:

```json
{
  "conversationStatusId": "00000000-0000-0000-0000-000000000001",
  "priorityId": "00000000-0000-0000-0000-000000000002",
  "lastMessageAt": null,
  "closed": null,
  "closedAt": null,
  "closedBy": null
}
```

Reemplazar los IDs del ejemplo por registros existentes de los catálogos.
La respuesta añade id, createdAt y updatedAt. PUT sustituye los campos editables:
los opcionales enviados como null u omitidos quedan en null. Se conserva ID y
createdAt; updatedAt se actualiza. No se imponen reglas sobre cierre, orden de
fechas, profesionales de cierre o unicidad ausentes de V41.

## Capas y pruebas

Domain contiene agregado, ID, eventos y puerto sin Spring/JPA. Application
contiene comandos, respuesta y los cinco casos de uso. Infrastructure aporta REST,
validación, configuración y persistencia. Restaurar desde JPA no produce eventos;
creación y actualización los registran en memoria y eliminación devuelve su evento.

Las pruebas cubren CRUD, auditoría, conversaciones repetidas, campos opcionales,
referencias ausentes al crear y actualizar, actualización inválida sin cambios
parciales y conversión JPA tanto con null como con todos los campos completos.
La ejecución HTTP y la validación en PostgreSQL siguen pendientes de conexión.
