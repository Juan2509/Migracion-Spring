# Rangel: esquema de MindConnect

Proyecto Spring Boot con migraciones Flyway para PostgreSQL. El diagrama de
MindConnect es la fuente del esquema; los proyectos compartidos se utilizan como
referencias de organización.

## Organización del proyecto

El proyecto padre Maven agrupa tres módulos, visibles por separado en Java Projects:

```text
rangel/
├── pom.xml                  # Padre y configuración común
├── domain/                  # Modelo y reglas de negocio
├── application/             # Casos de uso; depende de domain
└── infrastructure/          # Spring Boot; depende de application
    └── src/
        ├── main/java/       # RangelApplication
        ├── main/resources/
        │   ├── application.properties
        │   └── db/migration/ # V1–V52
        └── test/java/
```

`domain` y `application` contienen por ahora la declaración de sus paquetes;
su implementación funcional se añadirá cuando corresponda. Las dependencias de
Spring, JPA y Flyway quedan en `infrastructure`. Las migraciones conservan
sus nombres y contenido; su ubicación en el classpath continúa siendo
`db/migration`.

En VS Code, abrir la raíz `rangel` y actualizar Java Projects. Si sigue mostrando
la estructura anterior, ejecutar **Java: Clean Java Language Server Workspace**
desde la paleta de comandos y permitir el reinicio.

## Entrega 1: V1 a V10

Las migraciones están en `infrastructure/src/main/resources/db/migration`. Este bloque crea:

| Versión | Tabla | Referencias |
| --- | --- | --- |
| V1 | countries | — |
| V2 | state_regions | countries |
| V3 | city_municipalities | state_regions |
| V4 | document_types | — |
| V5 | genders | — |
| V6 | professional_types | — |
| V7 | relationship_types | — |
| V8 | studies | — |
| V9 | professionals | document_types, professional_types, city_municipalities |
| V10 | contacts | city_municipalities, professionals |

Cada FK referencia una tabla creada previamente. Las relaciones de este bloque
son 1:N; `updated_by` es opcional. Se interpretan las marcas `U` como UNIQUE y
`N` como columnas que admiten NULL; las restantes son obligatorias.

Se conserva `code_citi`, tal como figura en la imagen. `studies.name` no lleva
UNIQUE porque el diagrama no lo marca. Las marcas temporales que aparecen como
`TIMESTAMPT` se interpretan como `TIMESTAMPTZ`, siguiendo la distinción temporal
acordada en la conversación: `TIMESTAMPT` no es un tipo válido de PostgreSQL.
Las restricciones UNIQUE sobre nombres de profesionales se mantienen porque
están marcadas en el diagrama, aunque impiden registrar nombres repetidos.
No se agregan valores por defecto ni reglas de borrado en cascada.

## Entrega 2: V11 a V20

| Versión | Tabla | Referencias |
| --- | --- | --- |
| V11 | phone_contacts | contacts |
| V12 | email_contacts | contacts |
| V13 | professional_studies | studies, professionals, countries |
| V14 | patients | document_types, genders, professionals, city_municipalities |
| V15 | patient_contacts | contacts, patients, relationship_types |
| V16 | patient_allergies | patients, professionals |
| V17 | clinical_record_statuses | — |
| V18 | clinical_records | patients, clinical_record_statuses, professionals |
| V19 | encounter_types | — |
| V20 | encounter_modalities | — |

`phone_contacts` conserva la tabla y omite `Column1` y `Column2`; no se
inventan columnas de auditoría. `email_contacts.email` conserva la marca UNIQUE.
`professional_studies.country_id` y su FK están incluidos según el diagrama.
`patient_contacts` representa la relación N:M entre pacientes y contactos.
Los campos de autoría referencian `professionals.id`.

En `clinical_records`, `creation_date` es TIMESTAMP y `record_number` tiene
longitud 50. No se añade `active` a `clinical_record_statuses`, porque no aparece
en la imagen. `birth_date` y `clinical_records.closed_at` son obligatorios al no
tener marca N; esto exige proporcionar fecha de cierre al crear una historia.
Se mantiene esa restricción del diagrama, pendiente de una decisión funcional
si deben admitirse historias abiertas sin fecha de cierre.

## Entrega 3: V21 a V30

| Versión | Tabla | Referencias |
| --- | --- | --- |
| V21 | encounter_statuses | — |
| V22 | encounters | clinical_records, professionals, encounter_types, encounter_modalities, encounter_statuses |
| V23 | clinical_notes | encounters, professionals |
| V24 | mental_status_exams | encounters, professionals |
| V25 | risk_levels | — |
| V26 | risk_assessments | encounters, risk_levels, professionals |
| V27 | treatment_statuses | — |
| V28 | treatment_plans | encounters, professionals, treatment_statuses |
| V29 | treatment_goal_statuses | — |
| V30 | treatment_goals | treatment_plans, treatment_goal_statuses |

`clinical_notes.assessment` y `plan` son TEXT, según la imagen. Solo `code`
lleva UNIQUE en `risk_levels`. Se conserva el nombre `treatment_goal_id`, cuya
FK apunta al catálogo `treatment_goal_statuses`.

Las relaciones de este bloque son 1:N. Se sigue la interpretación de la marca N:
las columnas no marcadas son obligatorias, incluidas `encounters.ended_at`,
`treatment_plans.end_date` y `treatment_goals.completed_at`. Por ello deben
proporcionarse estas fechas al insertar registros; admitir procesos abiertos
sin fecha de finalización requeriría acordar un cambio del esquema.

## Entrega 4: V31 a V40

| Versión | Tabla |
| --- | --- |
| V31 | medication_routes |
| V32 | assessment_types |
| V33 | consent_types |
| V34 | diagnostic_systems |
| V35 | provider_models_ai |
| V36 | ai_models |
| V37 | ai_run_statuses |
| V38 | conversations_statuses |
| V39 | priorities |
| V40 | sender_types |

Se aplica UNIQUE a `code` en medication_routes, assessment_types y
diagnostic_systems, según la marca U. No se añaden restricciones UNIQUE a
consent_types ni a los nombres de proveedores o catálogos de chat e IA.

En provider_models_ai se conserva `"isActive"` con su grafía exacta y los
TIMESTAMP de auditoría. `razon_social` usa VARCHAR sin longitud, porque el
diagrama no especifica un límite. PostgreSQL permite esta declaración.

`ai_models.provider_model_id` conserva VARCHAR(50), mientras que
`provider_models_ai.id` es UUID. No se crea una FK incompatible: esa relación
queda pendiente de corregir los tipos mediante una decisión sobre el esquema.
Las tablas de este bloque se crean antes de las tablas de chat que las usan.

## Entrega 5: V41 a V50

| Versión | Tabla | Referencias |
| --- | --- | --- |
| V41 | chat_conversations | conversations_statuses, priorities |
| V42 | chat_participants | chat_conversations, sender_types, patients, professionals |
| V43 | chat_conversation_ai_settings | chat_conversations, ai_models |
| V44 | message_types | — |
| V45 | chat_messages | chat_conversations, message_types, chat_participants |
| V46 | chat_ai_runs | chat_conversations, chat_messages, ai_models, ai_run_statuses |
| V47 | chat_ai_run_errors | chat_ai_runs |
| V48 | chat_ai_run_metrics | chat_ai_runs |
| V49 | escalations_statuses | — |
| V50 | chat_escalations | chat_conversations, escalations_statuses |

Se conservan los TIMESTAMP y los JSONB de mensajes, así como DECIMAL(10,6)
para el coste de las ejecuciones. Las relaciones son 1:N y se crean las
tablas referenciadas antes de declarar sus FK.

Las marcas N permiten NULL en `last_message_at`, `closed`, `closed_at` y
`closed_by` de conversaciones, y en `patient_id` y `professional_id` de
participantes. No se añade una regla de exclusión entre paciente y profesional,
porque no aparece en el diagrama. `closed_by` conserva UUID sin FK, al no tener
marca FK ni una relación dibujada. Los nombres de message_types y
escalations_statuses no llevan UNIQUE, porque no tienen marca U.

## Entrega 6: V51 y V52

| Versión | Tabla | Referencias |
| --- | --- | --- |
| V51 | chat_escalation_assignments | chat_escalations, professionals |
| V52 | chat_escalation_status_history | chat_escalations, escalations_statuses |

Las asignaciones vinculan escalaciones y profesionales mediante dos FK,
permitiendo múltiples asignaciones por escalación y por profesional. El historial
permite múltiples cambios por escalación y referencia el catálogo de estados.
Se conservan assigned_at, created_at y changed_at como TIMESTAMP obligatorios,
sin añadir restricciones UNIQUE ni acciones de borrado que no figuren en el diagrama.

Con esta entrega están creadas las 52 migraciones, de V1 a V52. La relación
entre modelos de IA y proveedores sigue pendiente por la incompatibilidad de
tipos documentada en la entrega 4.

## Conexión y ejecución

La base PostgreSQL debe existir. Antes de iniciar el proyecto, definir en la
terminal `DB_URL` (formato `jdbc:postgresql://HOST:PUERTO/BASE`), `DB_USERNAME`
y `DB_PASSWORD` con los datos de conexión que se proporcionen.

Con un JDK 25 disponible, ejecutar desde la raíz:

```powershell
.\mvnw.cmd clean install -DskipTests
.\mvnw.cmd -pl infrastructure spring-boot:run
```

El inicio aplica las migraciones pendientes. Hibernate utiliza `validate` para
que Flyway gestione el esquema. Para comprobar el historial en PostgreSQL:

```sql
SELECT version, description, success
FROM flyway_schema_history
ORDER BY installed_rank;
```

La ejecución contra PostgreSQL está pendiente de los datos de conexión. Este
bloque crea estructura, no transfiere registros desde otra base de datos.
El siguiente paso es configurar la conexión y validar las 52 migraciones en
PostgreSQL. No modificar SQL
que ya se haya aplicado; los cambios posteriores requieren una nueva versión.
