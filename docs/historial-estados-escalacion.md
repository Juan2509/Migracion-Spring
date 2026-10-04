# Historial de estados de escalación: V52

ChatEscalationStatusHistory implementa chat_escalation_status_history en el
paquete chatescalationstatushistory de domain, application e infrastructure.
Sus fuentes se editan desde src/main/java de cada módulo en Java Projects.
La migración V52 conserva su contenido original.

| Columna SQL | Campo JSON | Regla |
| --- | --- | --- |
| id | id | UUID generado al registrar |
| escalation_id | escalationId | Obligatorio; referencia chat_escalations |
| escalation_status_id | escalationStatusId | Obligatorio; referencia escalations_statuses |
| created_at | createdAt | TIMESTAMP / LocalDateTime; generado al registrar y conservado |
| changed_at | changedAt | TIMESTAMP / LocalDateTime obligatorio; recibido en POST y PUT |

changedAt representa la fecha del cambio documentado y no lleva zona horaria.
Puede actualizarse explícitamente. No se añade updatedAt ni restricción de orden
entre las fechas porque V52 no la define.

## API

Ruta base: `/api/chat-escalation-status-history`. POST registra (201), GET lista
(200), GET /{id} consulta (200), PUT /{id} actualiza (200), DELETE /{id}
elimina (204). POST y PUT reciben IDs existentes:

```json
{
  "escalationId": "11111111-1111-1111-1111-111111111111",
  "escalationStatusId": "22222222-2222-2222-2222-222222222222",
  "changedAt": "2026-10-03T14:30:00"
}
```

La respuesta añade id y createdAt. Un historial, escalación o estado inexistente
devuelve 404. Los conflictos de integridad devuelven 409. Los campos nulos
se rechazan mediante validación REST y dominio.

Se permiten varias entradas para una escalación y estado, incluso con las
mismas referencias y fecha. Registrar o actualizar una entrada gestiona esa
fila del historial; el estado actual de la escalación se gestiona mediante
su propio caso de uso. No se añade un flujo automático de transiciones.

## Capas y verificación

Domain contiene agregado, ID, eventos y puerto sin Spring/JPA. Application
contiene comandos, respuesta y cinco casos de uso CRUD. Registrar y actualizar
verifican ambas referencias antes de guardar o modificar el agregado.
Infrastructure aporta REST, validación, configuración y persistencia JPA.

Los eventos utilizan el momento de la operación. Restaurar desde JPA conserva
las fechas y no genera eventos. El caso de uso de eliminación devuelve su evento.

Las pruebas comprueban CRUD, entradas repetidas, fecha de cambio recibida,
fecha de creación conservada, campos obligatorios y referencias inexistentes
sin guardado ni modificaciones parciales, además de conversión JPA.
La ejecución HTTP y validación real en PostgreSQL siguen pendientes de conexión.

Con V52 quedan implementados los 52 contextos. Esto completa la cobertura de
las tablas en las tres capas; la verificación de integración y la FK pendiente
entre modelos de IA y proveedores se describen en el README.
