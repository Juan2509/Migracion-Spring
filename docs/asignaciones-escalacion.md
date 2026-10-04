# Asignaciones de escalación: V51

ChatEscalationAssignment implementa chat_escalation_assignments en el paquete
chatescalationassignment de domain, application e infrastructure. Sus fuentes
se editan desde src/main/java de cada módulo en Java Projects. V51 se conserva.

| Columna SQL | Campo JSON | Regla |
| --- | --- | --- |
| id | id | UUID generado al registrar |
| escalation_id | escalationId | Obligatorio; referencia chat_escalations |
| professional_id | professionalId | Obligatorio; referencia professionals |
| assigned_at | assignedAt | Obligatorio; TIMESTAMP / LocalDateTime |

assignedAt se proporciona en POST y PUT, sin zona horaria. Representa la fecha
de asignación y puede actualizarse explícitamente; no es una fecha automática
de creación. La tabla no contiene createdAt ni updatedAt.

## API

Ruta base: `/api/chat-escalation-assignments`. POST registra (201), GET lista
(200), GET /{id} consulta (200), PUT /{id} actualiza (200), DELETE /{id}
elimina (204). POST y PUT reciben IDs existentes:

```json
{
  "escalationId": "11111111-1111-1111-1111-111111111111",
  "professionalId": "22222222-2222-2222-2222-222222222222",
  "assignedAt": "2026-10-03T14:30:00"
}
```

La respuesta añade id. Una asignación, escalación o profesional inexistente
devuelve 404. Los conflictos de integridad devuelven 409. Los campos nulos
se rechazan mediante validación REST y dominio.

Las FK permiten varias asignaciones por escalación y por profesional, incluso
con el mismo par de referencias. No se añade UNIQUE, restricción sobre la fecha,
condición de profesional activo ni cambio automático del estado de la escalación.

## Capas y verificación

Domain contiene agregado, ID, eventos y puerto sin Spring/JPA. Application
contiene comandos, respuesta y cinco casos de uso CRUD. Registrar y actualizar
verifican ambas referencias antes de guardar o modificar el agregado.
Infrastructure aporta REST, validación, configuración y persistencia JPA.

Los eventos de registro y actualización utilizan el momento de la operación;
assignedAt conserva el valor recibido. Restaurar desde JPA no genera eventos.
El caso de uso de eliminación devuelve su evento.

Las pruebas comprueban CRUD, asignaciones repetidas, fechas recibidas,
referencias inexistentes sin guardado ni modificaciones parciales, campos
obligatorios y conversión JPA. La ejecución HTTP y validación en PostgreSQL
siguen pendientes de configurar la conexión.
