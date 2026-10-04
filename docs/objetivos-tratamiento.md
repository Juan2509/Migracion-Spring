# Estados y objetivos de tratamiento (V29–V30)

Los contextos treatmentgoalstatus y treatmentgoal mantienen las tres capas.
En Java Projects, los archivos están en src/main/java bajo com/migracion/rangel
de cada módulo: agregados, IDs, eventos y puertos en domain; comandos, respuestas,
excepciones y cinco casos de uso en application; solicitudes, controladores REST,
manejo de errores, beans, entidades JPA, mappers y adaptadores en infrastructure.
Los SQL permanecen en infrastructure/src/main/resources/db/migration.

## TreatmentGoalStatus

| Campo JSON | Tipo | Restricción de V29 |
| --- | --- | --- |
| code | String | Obligatorio; máximo 20; único |
| name | String | Obligatorio; máximo 50; único |
| active | Boolean | Obligatorio; admite false |

Código y nombre son únicos por separado. Al actualizar se excluye el propio ID.
CreatedAt y updatedAt usan LocalDateTime (TIMESTAMP); createdAt se conserva.
El ID se genera al registrar. No se cargan estados ni reglas de transición.

Ejemplo de POST en `/api/treatment-goal-statuses`:

```json
{"code": "COMPLETED", "name": "Finalizado", "active": true}
```

## TreatmentGoal

| Campo JSON | Tipo | Restricción de V30 |
| --- | --- | --- |
| treatmentPlanId | UUID | Obligatorio; treatment_plans.id |
| description | String | Obligatorio; TEXT |
| targetDate | Fecha YYYY-MM-DD | Obligatoria; DATE |
| completedAt | Fecha y hora con offset | Obligatoria; TIMESTAMPTZ |
| notes | String | Obligatorio; TEXT |
| treatmentGoalId | UUID | Obligatorio; treatment_goal_statuses.id |

TreatmentGoalId, como campo del registro, representa el estado, siguiendo el
nombre treatment_goal_id de V30. Es distinto del ID del propio objetivo y usa
TreatmentGoalStatusId en el dominio. La aplicación comprueba plan y estado
antes de registrar o actualizar. JPA usa UUID, sin @ManyToOne. Las FK y las
restricciones UNIQUE protegen la integridad frente a escrituras concurrentes.

El ID se genera al registrar. CreatedAt y updatedAt usan LocalDateTime, porque
V30 declara TIMESTAMP; createdAt se conserva al actualizar. TargetDate usa
LocalDate y completedAt usa OffsetDateTime con almacenamiento nativo de zona.
PostgreSQL conserva el instante de finalización, no necesariamente el offset
original. Las fechas y referencias del objetivo son editables mediante PUT.

CompletedAt es obligatorio incluso al registrar. Admitir objetivos pendientes
sin finalización requiere acordar un cambio del esquema y añadir una migración.
No se añaden reglas de orden temporal, transición o rechazo de estados inactivos
que no estén definidas. Description y notes no llevan límites VARCHAR. Se
permiten objetivos repetidos, porque V30 no declara UNIQUE.

Crear antes un plan y estado con sus referencias. Ejemplo de POST en
`/api/treatment-goals`, reemplazando los UUID por registros existentes:

```json
{
  "treatmentPlanId": "11111111-1111-1111-1111-111111111111",
  "description": "Objetivo de ejemplo",
  "targetDate": "2026-10-10",
  "completedAt": "2026-10-10T10:30:00-05:00",
  "notes": "Información de ejemplo para probar el CRUD",
  "treatmentGoalId": "22222222-2222-2222-2222-222222222222"
}
```

## Operaciones y errores

Ambas rutas base ofrecen:

| Método | Ruta | Resultado |
| --- | --- | --- |
| POST | Ruta base | 201 y registro creado |
| GET | Ruta base | 200 y lista |
| GET | Ruta base/{id} | 200 y registro |
| PUT | Ruta base/{id} | 200 y registro actualizado |
| DELETE | Ruta base/{id} | 204 |

PUT reemplaza todos los campos editables y exige los obligatorios. La respuesta
añade id, createdAt y updatedAt. Solicitudes inválidas producen 400; registros
o referencias inexistentes, 404; código o nombre de estado duplicados y
violaciones de integridad de persistencia, 409. Configurar conexión y arranque
conforme al README.

## Verificación

Las doce pruebas nuevas cubren CRUD, unicidad de código y nombre por separado,
conservación de valores propios y auditoría, referencias inexistentes sin
mutación parcial ni guardado, fechas obligatorias, textos extensos, objetivos
repetidos, estados inactivos y conversión JPA con los tres tipos temporales.
Restaurar desde JPA no genera eventos; estos permanecen en memoria.

```powershell
.\mvnw.cmd -o '-Dtest=*Test,!RangelApplicationTests' '-Dsurefire.failIfNoSpecifiedTests=false' test
```

Las pruebas usan puertos en memoria. La integración HTTP y PostgreSQL sigue
pendiente de conexión; compilar no ejecuta Flyway. V29 y V30 no se modifican.
