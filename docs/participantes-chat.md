# Participantes de chat: V42

ChatParticipant implementa chat_participants en los paquetes chatparticipant
de domain, application e infrastructure, desde src/main/java de cada módulo
en Java Projects. V42 conserva su contenido original.

| Columna SQL | Campo JSON | Regla |
| --- | --- | --- |
| id | id | UUID generado al registrar |
| conversation_id | conversationId | UUID obligatorio, referencia a chat_conversations |
| participant_type_id | participantTypeId | UUID obligatorio, referencia a sender_types |
| patient_id | patientId | UUID opcional, referencia a patients |
| professional_id | professionalId | UUID opcional, referencia a professionals |
| created_at | createdAt | TIMESTAMP / LocalDateTime, conservado al actualizar |
| updated_at | updatedAt | TIMESTAMP / LocalDateTime, actualizado por el dominio |

El dominio usa ChatConversationId, SenderTypeId, PatientId y ProfessionalId.
JPA guarda UUID sin asociaciones entre agregados. Los casos de registro y
actualización consultan los puertos correspondientes antes de guardar; las
referencias opcionales se comprueban solo si se proporcionan. Las FK de PostgreSQL
también garantizan la integridad.

No se añade una regla que exija exactamente paciente o profesional. Ambos pueden
estar presentes o ser null; V42 no declara exclusividad ni una relación entre el
tipo y estas referencias. Las asociaciones repetidas también se permiten.

## API

Ruta base: /api/chat-participants. POST registra (201), GET lista (200),
GET /{id} consulta (200), PUT /{id} actualiza (200) y DELETE /{id} elimina (204).
Un ID o referencia inexistente devuelve 404; los conflictos de integridad devuelven 409.

POST y PUT reciben:

```json
{
  "conversationId": "00000000-0000-0000-0000-000000000001",
  "participantTypeId": "00000000-0000-0000-0000-000000000002",
  "patientId": null,
  "professionalId": null
}
```

Reemplazar los IDs del ejemplo por registros existentes. La respuesta añade id,
createdAt y updatedAt. PUT sustituye las referencias editables; los opcionales
enviados como null u omitidos quedan en null. El ID y createdAt se conservan.

## Capas y verificación

Domain contiene agregado, ID, eventos y puerto sin Spring/JPA; application
contiene comandos, respuesta y los cinco casos de uso. Infrastructure aporta REST,
validación, configuración y persistencia. Restaurar desde JPA no produce eventos;
creación y actualización los registran en memoria y eliminación devuelve su evento.

Las pruebas cubren CRUD, auditoría, asociaciones repetidas, campos opcionales,
las cuatro referencias inexistentes al registrar y actualizar, actualización
inválida sin cambios parciales y conversión JPA con referencias opcionales
presentes o null. La ejecución HTTP y la validación en PostgreSQL siguen pendientes
de configurar la conexión.
