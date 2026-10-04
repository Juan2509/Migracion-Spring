# Niveles y evaluaciones de riesgo (V25–V26)

Los contextos risklevel y riskassessment mantienen las tres capas del proyecto.
En Java Projects, los archivos están bajo src/main/java de cada módulo:
domain contiene agregados, IDs, eventos y puertos; application contiene comandos,
respuestas, excepciones y cinco casos de uso por contexto; infrastructure contiene
solicitudes, controladores REST, manejo de errores, beans, entidades JPA, mappers
y adaptadores. Los SQL permanecen en infrastructure/src/main/resources/db/migration.

## RiskLevel

| Campo JSON | Tipo | Restricción de V25 |
| --- | --- | --- |
| code | String | Obligatorio; máximo 20; único |
| name | String | Obligatorio; máximo 50; admite repetidos |
| active | Boolean | Obligatorio; admite false |
| severity | Integer | Obligatorio; sin rango adicional |

Id se genera al registrar. CreatedAt y updatedAt usan LocalDateTime (TIMESTAMP).
Al actualizar se conserva createdAt y se excluye el propio ID al comprobar
el código único. No se añade unicidad a name ni severity. V25 no define un rango
para severity ni una relación entre su valor y un nombre de nivel.

Ejemplo de POST en `/api/risk-levels`, sin implicar datos precargados:

```json
{"code": "LEVEL_1", "name": "Nivel de ejemplo", "active": true, "severity": 1}
```

## RiskAssessment

| Campo JSON | Tipo | Restricción de V26 |
| --- | --- | --- |
| encounterId | UUID | Obligatorio; encounters.id |
| riskLevelId | UUID | Obligatorio; risk_levels.id |
| suicidalIdeation | Boolean | Obligatorio; admite false |
| suicidePlan | Boolean | Obligatorio; admite false |
| suicideIntent | Boolean | Obligatorio; admite false |
| selfHarm | Boolean | Obligatorio; admite false |
| harmToOthers | Boolean | Obligatorio; admite false |
| riskFactors | String | Obligatorio; TEXT |
| protectiveFactors | String | Obligatorio; TEXT |
| clinicalActions | String | Obligatorio; TEXT |
| observations | String | Obligatorio; TEXT |
| assessedAt | Fecha y hora con offset | Obligatoria; TIMESTAMPTZ |
| assessedBy | UUID | Obligatorio; professionals.id |

El ID se genera al registrar. La solicitud proporciona assessedAt y assessedBy,
ambos editables con PUT. AssessedAt usa OffsetDateTime con almacenamiento nativo
de zona en JPA. PostgreSQL conserva el instante, no necesariamente el offset
original. No se añaden createdAt, updatedAt ni otras columnas ausentes de V26.

Los cuatro textos no tienen límite VARCHAR ni restricción de contenido no vacío
añadida. Se permiten varias evaluaciones con las mismas referencias, porque no
hay UNIQUE. No se calcula un nivel de riesgo a partir de los indicadores ni
se restringen sus combinaciones: el nivel lo proporciona la solicitud. Tampoco
se rechaza un nivel por active=false sin una regla de negocio acordada.

Se comprueba que existan encuentro, nivel y evaluador antes de registrar o
actualizar. Las referencias usan IDs tipados y UUID en JPA, sin @ManyToOne.
Las FK y el código UNIQUE protegen la integridad frente a cambios concurrentes.

Ejemplo de POST en `/api/risk-assessments`, reemplazando los UUID por registros
existentes. Los valores son marcadores para probar el CRUD, no una evaluación:

```json
{
  "encounterId": "11111111-1111-1111-1111-111111111111",
  "riskLevelId": "22222222-2222-2222-2222-222222222222",
  "suicidalIdeation": false,
  "suicidePlan": false,
  "suicideIntent": false,
  "selfHarm": false,
  "harmToOthers": false,
  "riskFactors": "Información de ejemplo",
  "protectiveFactors": "Información de ejemplo",
  "clinicalActions": "Información de ejemplo",
  "observations": "Información de ejemplo",
  "assessedAt": "2026-10-03T10:30:00-05:00",
  "assessedBy": "33333333-3333-3333-3333-333333333333"
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
añade id; RiskLevel añade createdAt y updatedAt. Solicitudes inválidas producen
400, registros o referencias inexistentes 404, código de nivel duplicado o
violaciones de integridad de persistencia 409. Configurar conexión y arranque
conforme al README.

## Verificación

Las doce pruebas nuevas cubren CRUD, código único, nombres repetidos, severity
obligatorio y sin rango añadido, false, auditoría de niveles, las tres referencias
de evaluaciones, cambios de campos, indicadores y textos obligatorios, textos
extensos, evaluaciones repetidas y rechazo sin mutación parcial ni guardado.
Los mappers se prueban con todas las columnas y fechas. Restaurar desde JPA
no genera eventos; estos permanecen en memoria.

```powershell
.\mvnw.cmd -o '-Dtest=*Test,!RangelApplicationTests' '-Dsurefire.failIfNoSpecifiedTests=false' test
```

Las pruebas usan puertos en memoria. La integración HTTP y PostgreSQL sigue
pendiente de conexión; compilar no ejecuta Flyway. V25 y V26 no se modifican.
