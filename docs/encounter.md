# Encuentros (V22)

El contexto encounter sigue la estructura modular existente. En Java Projects,
sus archivos están en src/main/java bajo com/migracion/rangel de cada módulo:
domain contiene agregado, ID, eventos y puerto; application contiene comandos,
respuesta, excepciones y cinco casos de uso; infrastructure contiene solicitudes
REST, controlador, manejo de errores, beans, entidad JPA, mapper y adaptador.
El SQL permanece en infrastructure/src/main/resources/db/migration.

## Campos y referencias

| Campo JSON | Tipo | Restricción de V22 |
| --- | --- | --- |
| clinicalRecordId | UUID | Obligatorio; clinical_records.id |
| professionalId | UUID | Obligatorio; professionals.id |
| encounterTypeId | UUID | Obligatorio; encounter_types.id |
| startedAt | Fecha y hora con offset | Obligatoria; TIMESTAMPTZ |
| endedAt | Fecha y hora con offset | Obligatoria; TIMESTAMPTZ |
| reasonForVisit | String | Obligatorio; TEXT |
| currentCondition | String | Obligatorio; TEXT |
| modalityId | UUID | Obligatorio; encounter_modalities.id |
| statusId | UUID | Obligatorio; encounter_statuses.id |
| createdBy | UUID | Obligatorio al registrar; professionals.id |
| updatedBy | UUID | Obligatorio al registrar y actualizar; professionals.id |

Todas las fechas usan OffsetDateTime con almacenamiento nativo de zona en JPA.
CreatedAt y updatedAt se generan en UTC al registrar; al actualizar se conserva
createdAt y se renueva updatedAt. CreatedBy también se conserva y no forma parte
de la solicitud PUT. ProfessionalId identifica al profesional del encuentro;
createdBy y updatedBy identifican su autoría y pueden ser profesionales distintos.
La aplicación comprueba las siete referencias al registrar y las seis editables
al actualizar. Se usan IDs tipados en el dominio y UUID en JPA, sin @ManyToOne.
Las FK protegen la integridad frente a cambios concurrentes.

PostgreSQL conserva el instante de TIMESTAMPTZ, no necesariamente el offset
original. Los textos no llevan un límite VARCHAR ni una restricción de contenido
no vacío añadida. V22 no declara UNIQUE: se admiten encuentros repetidos con las
mismas referencias. No se añaden reglas de transición, orden de fechas ni rechazo
de catálogos inactivos que no estén definidos en el esquema.

EndedAt es obligatorio en V22, incluso al registrar. Admitir encuentros abiertos
sin fecha de finalización requiere acordar un cambio del esquema y añadir otra
migración. V22 se mantiene sin modificaciones.

## API REST

| Método | Ruta | Resultado |
| --- | --- | --- |
| POST | /api/encounters | 201 y encuentro creado |
| GET | /api/encounters | 200 y lista |
| GET | /api/encounters/{id} | 200 y encuentro |
| PUT | /api/encounters/{id} | 200 y encuentro actualizado |
| DELETE | /api/encounters/{id} | 204 |

La respuesta añade id, createdAt y updatedAt a los datos registrados. PUT
reemplaza todos los campos editables, exige updatedBy y omite createdBy. Una
solicitud inválida produce 400; un encuentro o referencia inexistente produce
404; una violación de integridad de persistencia produce 409.

Crear antes una historia clínica, profesionales, tipo, modalidad y estado de
encuentro con sus respectivas referencias. Ejemplo de POST para Postman,
sustituyendo los UUID por registros existentes:

```json
{
  "clinicalRecordId": "11111111-1111-1111-1111-111111111111",
  "professionalId": "22222222-2222-2222-2222-222222222222",
  "encounterTypeId": "33333333-3333-3333-3333-333333333333",
  "startedAt": "2026-10-03T09:30:00-05:00",
  "endedAt": "2026-10-03T10:30:00-05:00",
  "reasonForVisit": "Consulta de seguimiento",
  "currentCondition": "Información clínica de ejemplo",
  "modalityId": "44444444-4444-4444-4444-444444444444",
  "statusId": "55555555-5555-5555-5555-555555555555",
  "createdBy": "22222222-2222-2222-2222-222222222222",
  "updatedBy": "22222222-2222-2222-2222-222222222222"
}
```

Para PUT, usar el ID del encuentro en la ruta y quitar createdBy del cuerpo.
Configurar conexión y arranque conforme al README.

## Verificación

Las siete pruebas nuevas cubren CRUD, cambios de todas las referencias editables,
validación de las siete FK, rechazo sin mutación parcial ni guardado, autoría
inmutable, eventos, textos extensos, cierre y actualizador obligatorios, encuentros
repetidos, catálogos inactivos y conversión JPA de todas las columnas y fechas.
Restaurar desde JPA no genera eventos; estos permanecen en memoria.

```powershell
.\mvnw.cmd -o '-Dtest=*Test,!RangelApplicationTests' '-Dsurefire.failIfNoSpecifiedTests=false' test
```

Las pruebas unitarias usan puertos en memoria. La integración HTTP y PostgreSQL
sigue pendiente de conexión; compilar no ejecuta Flyway. V22 no se modifica.
