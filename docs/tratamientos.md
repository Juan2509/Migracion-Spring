# Estados y planes de tratamiento (V27–V28)

Los contextos treatmentstatus y treatmentplan mantienen las tres capas.
En Java Projects, los archivos están en src/main/java de cada módulo, bajo
com/migracion/rangel: agregados, IDs, eventos y puertos en domain; comandos,
respuestas, excepciones y cinco casos de uso en application; solicitudes,
controladores REST, manejo de errores, beans, entidades JPA, mappers y adaptadores
en infrastructure. Los SQL permanecen en infrastructure/src/main/resources/db/migration.

## TreatmentStatus

| Campo JSON | Tipo | Restricción de V27 |
| --- | --- | --- |
| code | String | Obligatorio; máximo 20; único |
| name | String | Obligatorio; máximo 50; único |
| active | Boolean | Obligatorio; admite false |

Código y nombre son únicos por separado. Al actualizar se excluye el propio ID.
CreatedAt y updatedAt usan LocalDateTime (TIMESTAMP); createdAt se conserva.
El ID se genera al registrar. No se cargan estados iniciales ni reglas de transición.

Ejemplo de POST en `/api/treatment-statuses`:

```json
{"code": "COMPLETED", "name": "Finalizado", "active": true}
```

## TreatmentPlan

| Campo JSON | Tipo | Restricción de V28 |
| --- | --- | --- |
| encounterId | UUID | Obligatorio; encounters.id |
| professionalId | UUID | Obligatorio; professionals.id |
| title | String | Obligatorio; máximo 200 |
| description | String | Obligatorio; TEXT |
| startDate | Fecha YYYY-MM-DD | Obligatoria; DATE |
| endDate | Fecha YYYY-MM-DD | Obligatoria; DATE |
| treatmentStatusId | UUID | Obligatorio; treatment_statuses.id |

La aplicación comprueba las tres referencias antes de registrar y actualizar.
Se usan IDs tipados y UUID en JPA, sin @ManyToOne. Las FK y restricciones UNIQUE
protegen la integridad frente a escrituras concurrentes.

El ID se genera al registrar. CreatedAt y updatedAt usan OffsetDateTime en UTC
con almacenamiento nativo de zona en JPA (TIMESTAMPTZ). PostgreSQL conserva
el instante, no necesariamente el offset original. CreatedAt se conserva al
actualizar; updatedAt se renueva. StartDate y endDate usan LocalDate.

EndDate es obligatorio incluso al crear un plan. Admitir planes sin finalización
requiere acordar un cambio del esquema y añadir otra migración. No se añaden
reglas de duración, orden de fechas, transición de estado ni rechazo de estados
inactivos que no estén definidos. Description no lleva un límite VARCHAR.
V28 no declara UNIQUE: se admiten títulos y planes repetidos.

Crear antes encuentro, profesional y estado con sus referencias. Ejemplo de
POST en `/api/treatment-plans`, sustituyendo los UUID por registros existentes:

```json
{
  "encounterId": "11111111-1111-1111-1111-111111111111",
  "professionalId": "22222222-2222-2222-2222-222222222222",
  "title": "Plan de ejemplo",
  "description": "Información de ejemplo para probar el CRUD",
  "startDate": "2026-10-03",
  "endDate": "2026-10-10",
  "treatmentStatusId": "33333333-3333-3333-3333-333333333333"
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

Las doce pruebas nuevas cubren CRUD, código y nombre únicos por separado,
conservación de valores propios y auditoría, validaciones sin mutación parcial,
los tres padres del plan, fechas obligatorias, límite del título, descripción
extensa, planes repetidos, estados inactivos y conversión JPA de todas las columnas.
Restaurar desde JPA no genera eventos; estos permanecen en memoria.

```powershell
.\mvnw.cmd -o '-Dtest=*Test,!RangelApplicationTests' '-Dsurefire.failIfNoSpecifiedTests=false' test
```

Las pruebas usan puertos en memoria. La integración HTTP y PostgreSQL sigue
pendiente de conexión; compilar no ejecuta Flyway. V27 y V28 no se modifican.
