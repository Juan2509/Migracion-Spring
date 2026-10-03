# Contactos y alergias de pacientes (V15–V16)

Los contextos patientcontact y patientallergy siguen la organización existente.
En Java Projects, cada módulo contiene sus archivos en src/main/java:
domain incluye agregado, ID, eventos, excepción y puerto; application incluye
comandos, respuesta y cinco casos de uso; infrastructure incluye controlador,
solicitudes, manejo de errores, beans, entidad JPA, mapper y adaptador.
Los SQL permanecen en infrastructure/src/main/resources/db/migration.

## PatientContact

| Campo JSON | Tipo | Restricción de V15 |
| --- | --- | --- |
| contactId | UUID | Obligatorio; contacts.id |
| patientId | UUID | Obligatorio; patients.id |
| isPrimaryContact | Boolean | Obligatorio; admite false |
| isEmergencyContact | Boolean | Obligatorio; admite false |
| relationshipTypeId | UUID | Obligatorio; relationship_types.id |

La tabla representa vínculos entre pacientes y contactos, con un parentesco.
No tiene auditoría ni UNIQUE: se pueden repetir asociaciones y registrar varios
contactos principales o de emergencia para el mismo paciente. No se añade una
regla que desactive otros contactos al marcar uno. El ID se genera al registrar.
Los eventos tienen occurredOn, sin añadir esa columna a la tabla.

Crear antes un paciente, un contacto y un parentesco. Ejemplo de POST en
`/api/patient-contacts`, reemplazando los UUID por registros existentes:

```json
{
  "contactId": "11111111-1111-1111-1111-111111111111",
  "patientId": "22222222-2222-2222-2222-222222222222",
  "isPrimaryContact": false,
  "isEmergencyContact": true,
  "relationshipTypeId": "33333333-3333-3333-3333-333333333333"
}
```

## PatientAllergy

| Campo JSON | Tipo | Restricción de V16 |
| --- | --- | --- |
| patientId | UUID | Obligatorio; patients.id |
| substance | String | Obligatorio; máximo 200 caracteres |
| reaction | String | Opcional; TEXT sin límite VARCHAR |
| severity | String | Obligatorio; máximo 20 caracteres |
| active | Boolean | Obligatorio; admite false |
| recordedAt | Fecha y hora con offset | Obligatoria; TIMESTAMPTZ |
| recordedBy | UUID | Obligatorio; professionals.id |

RecordedAt lo proporciona la solicitud y usa OffsetDateTime con almacenamiento
nativo de zona en JPA. PostgreSQL conserva el instante, no necesariamente el
offset original. CreatedAt y updatedAt usan LocalDateTime porque son TIMESTAMP;
se generan en el dominio y createdAt se conserva al actualizar.

No hay UNIQUE: un paciente puede tener varias filas con la misma sustancia.
Severity conserva texto libre dentro de su longitud; no se impone un enum
ni se crea un catálogo ausente del esquema. PUT permite cambiar también el
paciente, recordedAt y recordedBy, conservando el ID y la fecha de creación.

Crear antes un paciente y el profesional que registra. Ejemplo de POST en
`/api/patient-allergies`, reemplazando los UUID por registros existentes:

```json
{
  "patientId": "22222222-2222-2222-2222-222222222222",
  "substance": "Penicilina",
  "reaction": null,
  "severity": "Alta",
  "active": true,
  "recordedAt": "2026-10-03T09:30:00-05:00",
  "recordedBy": "44444444-4444-4444-4444-444444444444"
}
```

## Operaciones y errores

Ambas rutas base ofrecen las mismas operaciones:

| Método | Ruta | Resultado |
| --- | --- | --- |
| POST | Ruta base | 201 y registro creado |
| GET | Ruta base | 200 y lista |
| GET | Ruta base/{id} | 200 y registro |
| PUT | Ruta base/{id} | 200 y registro actualizado |
| DELETE | Ruta base/{id} | 204 |

PUT reemplaza todos los campos editables y exige los obligatorios. La respuesta
añade id; PatientAllergy añade también createdAt y updatedAt. La aplicación
comprueba las referencias antes de registrar o actualizar; una referencia o
registro inexistente produce 404. Una solicitud inválida produce 400 y una
violación de integridad de la base produce 409. Las FK protegen la integridad
frente a cambios concurrentes. Las entidades utilizan UUID, sin @ManyToOne.

## Verificación

Las doce pruebas nuevas cubren eventos, validaciones sin mutación parcial,
CRUD y cambios de referencias, referencias inexistentes al crear y actualizar,
valores false, datos repetidos, reaction nula o extensa, auditoría y conversión
JPA de todas las columnas, incluida la fecha con offset. Restaurar desde JPA no
genera eventos de registro; los eventos siguen en memoria, sin publicación.

```powershell
.\mvnw.cmd -o '-Dtest=*Test,!RangelApplicationTests' '-Dsurefire.failIfNoSpecifiedTests=false' test
```

Los puertos de prueba usan memoria. La integración HTTP y PostgreSQL sigue
pendiente de conexión. V15 y V16 mantienen sus archivos originales.
