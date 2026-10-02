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
        │   └── db/migration/ # V1–V20
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
Después del commit de este bloque se continúa con V21–V30. No modificar SQL
que ya se haya aplicado; los cambios posteriores requieren una nueva versión.
