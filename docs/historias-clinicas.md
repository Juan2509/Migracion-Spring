# Estados e historias clínicas (V17–V18)

ClinicalRecordStatus y ClinicalRecord tienen dominio, aplicación e infraestructura.
En Java Projects, cada módulo contiene los archivos en src/main/java bajo
com/migracion/rangel: agregados, IDs, eventos y puertos en domain; comandos,
respuestas, excepciones y cinco casos de uso en application; API REST, solicitudes,
manejo de errores, beans, entidades JPA, mappers y adaptadores en infrastructure.
Las migraciones permanecen en infrastructure/src/main/resources/db/migration.

## ClinicalRecordStatus

| Campo JSON | Tipo | Restricción de V17 |
| --- | --- | --- |
| code | String | Obligatorio; máximo 20; único |
| name | String | Obligatorio; máximo 50; único |

Id se genera al registrar. CreatedAt y updatedAt usan LocalDateTime porque el
esquema declara TIMESTAMP. Al actualizar se conserva la fecha de creación y
se excluye el propio ID de las comprobaciones de unicidad. Código y nombre son
únicos por separado; no se añade active ni se cargan estados predefinidos.

Ejemplo de POST en `/api/clinical-record-statuses`:

```json
{
  "code": "CLOSED",
  "name": "Cerrada"
}
```

## ClinicalRecord

| Campo JSON | Tipo | Restricción de V18 |
| --- | --- | --- |
| patientId | UUID | Obligatorio; patients.id |
| creationDate | Fecha y hora sin offset | Obligatoria; TIMESTAMP |
| recordNumber | String | Obligatorio; máximo 50; admite repetidos |
| openedAt | Fecha y hora con offset | Obligatoria; TIMESTAMPTZ |
| closedAt | Fecha y hora con offset | Obligatoria; TIMESTAMPTZ |
| statusId | UUID | Obligatorio; clinical_record_statuses.id |
| createdBy | UUID | Obligatorio al registrar; professionals.id |

Id y createdAt se generan al registrar. CreatedAt usa OffsetDateTime en UTC,
con almacenamiento nativo de zona en JPA. OpenedAt y closedAt los proporciona
la solicitud y también usan OffsetDateTime. PostgreSQL conserva el instante,
no necesariamente el offset original. CreationDate usa LocalDateTime.

CreatedAt y createdBy se conservan en PUT y no forman parte de sus campos
editables. V18 no tiene updatedAt: actualizar produce un evento en memoria,
sin inventar esa columna. Las referencias se representan mediante IDs tipados
y UUID en JPA, sin @ManyToOne. Al registrar se comprueban paciente, estado y
profesional; al actualizar se comprueban paciente y estado.

ClosedAt es obligatorio en V18. Por tanto, el CRUD actual requiere fecha de
cierre incluso al registrar. Admitir historias abiertas sin cierre requeriría
acordar un cambio y crear una nueva migración; no se modifica V18. No se añaden
reglas de orden temporal ni estados automáticos ausentes del esquema. Tampoco
se exige una sola historia por paciente ni número de historia único.

Crear antes un paciente, un estado y un profesional con sus respectivas
referencias. Ejemplo de POST en `/api/clinical-records`, reemplazando los UUID
por IDs existentes:

```json
{
  "patientId": "11111111-1111-1111-1111-111111111111",
  "creationDate": "2026-10-03T08:00:00",
  "recordNumber": "HC-001",
  "openedAt": "2026-10-03T09:30:00-05:00",
  "closedAt": "2026-10-03T10:30:00-05:00",
  "statusId": "22222222-2222-2222-2222-222222222222",
  "createdBy": "33333333-3333-3333-3333-333333333333"
}
```

Para PUT, enviar los mismos campos editables y omitir createdBy. La respuesta
incluye id, los campos clínicos, createdBy y createdAt. Configurar conexión y
arranque según el README.

## Rutas y errores

Ambas rutas base ofrecen:

| Método | Ruta | Resultado |
| --- | --- | --- |
| POST | Ruta base | 201 y registro creado |
| GET | Ruta base | 200 y lista |
| GET | Ruta base/{id} | 200 y registro |
| PUT | Ruta base/{id} | 200 y registro actualizado |
| DELETE | Ruta base/{id} | 204 |

PUT reemplaza todos los campos editables y exige los obligatorios. Una solicitud
inválida produce 400; un registro o referencia inexistente produce 404. Código
o nombre de estado duplicados producen 409, al igual que una violación de
integridad de la base. Las FK y UNIQUE protegen frente a cambios concurrentes.

## Verificación

Las doce pruebas nuevas cubren límites y obligatoriedad, eventos, restauración
sin nuevos eventos, CRUD, unicidad independiente de código y nombre, conservación
de valores únicos propios, referencias inexistentes sin mutación parcial,
números de historia repetidos, cierre obligatorio, autoría inmutable, cambios
de referencias y conversión JPA con los tipos temporales de cada columna.

```powershell
.\mvnw.cmd -o '-Dtest=*Test,!RangelApplicationTests' '-Dsurefire.failIfNoSpecifiedTests=false' test
```

Las pruebas usan puertos en memoria. La integración HTTP y PostgreSQL sigue
pendiente de conexión; la compilación no ejecuta Flyway. V17 y V18 no se modifican.
Los eventos siguen en memoria, sin publicación ni persistencia independiente.
