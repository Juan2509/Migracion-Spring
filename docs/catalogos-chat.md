# Catálogos de chat: V38–V40

ConversationStatus, Priority y SenderType implementan los catálogos previos
a las conversaciones. Cada uno contiene las tres capas y cinco casos de uso CRUD.
Los paquetes conversationstatus, priority y sendertype se editan desde
src/main/java de cada módulo en Java Projects.

| Migración | Tabla | Campo JSON | Ruta base |
| --- | --- | --- | --- |
| V38 | conversations_statuses | nameStatus | /api/conversation-statuses |
| V39 | priorities | namePriority | /api/priorities |
| V40 | sender_types | nameType | /api/sender-types |

Cada tabla contiene id UUID, su nombre VARCHAR(50), created_at y updated_at
TIMESTAMP. Todos los campos son obligatorios. Los nombres pueden repetirse;
no se añaden code, active, restricciones UNIQUE, relaciones ni datos iniciales.
Se conservan las grafías name_status, name_priority y name_type del SQL.

## API

POST en la ruta base registra (201), GET lista (200), GET /{id} consulta (200),
PUT /{id} actualiza (200) y DELETE /{id} elimina (204). Un ID inexistente devuelve
404. Los conflictos de integridad de PostgreSQL se traducen a 409.

Cuerpos de POST y PUT para cada ruta:

```json
{ "nameStatus": "Abierta" }
```

```json
{ "namePriority": "Normal" }
```

```json
{ "nameType": "Paciente" }
```

La respuesta añade id, createdAt y updatedAt al campo de nombre correspondiente.
El dominio genera el ID y la auditoría al registrar; conserva id y createdAt
al actualizar. Las fechas utilizan LocalDateTime. No se implementan transiciones
de estado, comparaciones de prioridad ni valores predefinidos de remitente.

## Capas y verificación

Domain contiene agregado, ID, eventos y puerto sin Spring/JPA. Application
contiene comandos, respuesta y cinco casos de uso CRUD. Infrastructure aporta
REST, validación, configuración y persistencia JPA. Restaurar un agregado no
produce eventos nuevos; creación y actualización los registran en memoria,
y el caso de eliminación devuelve su evento.

Las pruebas cubren CRUD, registros inexistentes, auditoría, nombres repetidos,
límites de longitud y caracteres Unicode, actualización inválida sin cambios
parciales y conversión JPA. Desde la raíz:

```powershell
.\mvnw.cmd -o -Dtest=*Test,!RangelApplicationTests -Dsurefire.failIfNoSpecifiedTests=false test
```

V38–V40 conservan su contenido original. La ejecución HTTP y la validación
en PostgreSQL siguen pendientes de configurar la conexión.
