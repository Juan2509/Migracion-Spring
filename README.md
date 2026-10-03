# Rangel: esquema de MindConnect

Proyecto Spring Boot con migraciones Flyway para PostgreSQL. El diagrama de
MindConnect es la fuente del esquema; los proyectos compartidos se utilizan como
referencias de organización.

## Guía de lectura

- [Funcionamiento](#funcionamiento)
- [Estructura y responsabilidades](#organización-del-proyecto)
- [Inventario de las 52 migraciones](#entrega-1-v1-a-v10)
- [Configuración y ejecución](#conexión-y-ejecución)
- [Cómo reconstruir el proyecto](#cómo-reconstruir-el-proyecto)
- [Cómo reproducir el trabajo desde el diagrama](#cómo-reproducir-el-trabajo-desde-el-diagrama)
- [Problemas frecuentes](#problemas-frecuentes)

## Funcionamiento

El entregable crea el esquema de MindConnect mediante 52 archivos SQL
versionados. Actualmente contiene la estructura modular y las migraciones;
los módulos de negocio incluyen la base compartida y el contexto country con
casos de uso, entidad JPA y API REST. También está implementado stateregion,
que referencia a country por su ID, y citymunicipality, que referencia a
stateregion por su ID. También están implementados documenttype, gender y
professionaltype, con sus restricciones de unicidad, y relationshiptype y
study, adaptados a V7–V8, professional según V9, contact según V10 y
phonecontact/emailcontact según V11–V12, professionalstudy según V13 y patient
según V14.
Los demás contextos siguen pendientes.
Los scripts crean tablas y restricciones, sin cargar datos iniciales
ni trasladar registros de otra base.

Al iniciar `RangelApplication`, Spring Boot obtiene la conexión desde las
variables de entorno y Flyway busca SQL en `classpath:db/migration`.
Flyway compara los archivos con su tabla `flyway_schema_history`, valida las
migraciones registradas y aplica las pendientes por número de versión:
V1, V2, …, V52. El orden permite crear cada tabla antes de que otra la referencie.

En una base vacía se aplican las 52 versiones. En los siguientes inicios se
aplican únicamente las nuevas. Flyway registra versiones y checksums; editar
un archivo ya aplicado puede causar un error de validación. Las modificaciones
posteriores deben introducirse en una nueva migración.

`spring.jpa.hibernate.ddl-auto=validate` evita que Hibernate cree o cambie
tablas. Su validación cubre las entidades mapeadas; actualmente country es la
primera, acompañada por stateregion, citymunicipality y los tres catálogos
documenttype, gender y professionaltype, por lo que no sustituye
la revisión del esquema SQL completo. La creación de las
tablas corresponde a Flyway. `spring.flyway.clean-disabled=true` mantiene
deshabilitada la limpieza de la base mediante Flyway.

**Estado de verificación:** se comprobó la numeración V1–V52, el orden de
referencias y la compilación Maven, y se verificó que el JAR incluye los 52 SQL.
La ejecución real en PostgreSQL sigue pendiente de configurar la conexión.
La FK entre modelos de IA y proveedores también está pendiente por los tipos
incompatibles descritos en la entrega 4.

## Organización del proyecto

El proyecto padre Maven agrupa tres módulos. Java Projects permite trabajar
con sus fuentes y recursos una vez importados los POM:

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

| Elemento | Responsabilidad | Estado actual |
| --- | --- | --- |
| `pom.xml` raíz | Agrupar módulos, versión de Java y configuración heredada de Spring Boot | Proyecto padre; no es una aplicación ejecutable |
| `domain` | Modelo y reglas de negocio independientes de infraestructura | Base compartida y catorce contextos con agregados, IDs, eventos y puertos |
| `application` | Casos de uso que utilizan el dominio | Base compartida y cinco casos de uso CRUD por contexto implementado |
| `infrastructure` | Integraciones, configuración y arranque de Spring Boot | Arranque, SQL, API REST y adaptadores JPA de los catorce contextos |
| `.mvn/`, `mvnw`, `mvnw.cmd` | Ejecutar Maven con el wrapper del proyecto | Incluidos en el entregable |
| `.gitignore` | Excluir compilación, archivos locales y referencias | Conserva fuentes y configuración compartida |

Las dependencias entre módulos siguen esta dirección:

```text
infrastructure → application → domain
```

La clase principal está en
`infrastructure/src/main/java/com/migracion/rangel/RangelApplication.java`.
El archivo de configuración que se utiliza está en
`infrastructure/src/main/resources/application.properties`; cualquier pestaña
del editor que conserve la ruta anterior `src/main/resources` debe actualizarse.

`domain` y `application` contienen la base compartida para implementar los contextos;
country es el primer contexto implementado; los demás se añadirán en las siguientes entregas. Las dependencias de
Spring, JPA y Flyway quedan en `infrastructure`. Las migraciones conservan
sus nombres y contenido; su ubicación en el classpath continúa siendo
`db/migration`.

### Trabajar desde Java Projects en VS Code

La documentación de referencia recomienda esta vista y la presentación
jerárquica. Los ejemplos modulares separan domain, application e infrastructure;
el ejemplo del compañero coloca sus SQL en `src/main/resources/db/migration`.
En este proyecto esa misma ruta pertenece al módulo infrastructure.

Para abrir los SQL desde Java Projects, desplegar:

```text
Java Projects
└── rangel / infrastructure (según la presentación del editor)
    └── infrastructure / src/main/resources
        └── db
            └── migration
                ├── V1__create_countries_table.sql
                ├── ...
                └── V52__create_chat_escalation_status_history_table.sql
```

Si la vista muestra el árbol de carpetas bajo rangel, seguir
`infrastructure → src → main → resources → db → migration`.
La flecha de migration debe estar desplegada para ver los 52 archivos.
Para crear una nueva migración, utilizar la acción de nuevo archivo en esa
carpeta; para crear clases, utilizar los paquetes de `src/main/java` del módulo
correspondiente. Los SQL son recursos de infrastructure y no clases Java.

La configuración compartida `.vscode/settings.json` activa la presentación
jerárquica, el servidor Java en modo Standard y la importación de Maven con
actualización automática de la configuración de compilación. Este archivo
se incluye en Git para recuperar la misma configuración al clonar.

Si solo aparece rangel y los módulos no se reconocen como proyectos Maven:

1. Abrir la raíz que contiene el POM padre y los tres módulos.
2. Ejecutar **Java: Clean Java Language Server Workspace** desde la paleta
   (`Ctrl+Shift+P`) y permitir el reinicio.
3. Esperar a que finalice la importación Java/Maven y actualizar Java Projects.
4. Desplegar los recursos de infrastructure y la carpeta migration.

`JRE System Library` representa el JDK y `Maven Dependencies` las bibliotecas.
Las carpetas `target` contienen resultados generados al compilar; las fuentes
que se editan están en `src`. La vista puede agrupar nodos de distintas formas,
pero las rutas Maven de los archivos siguen siendo las indicadas arriba.

### Base compartida de DDD y arquitectura hexagonal

Esta primera entrega de implementación conserva Java 25 y el paquete base
`com.migracion.rangel`. Los archivos se encuentran en los paquetes common de
domain y application, accesibles desde Java Projects:

| Clase | Ubicación dentro del módulo | Función |
| --- | --- | --- |
| DomainEvent | domain: common/event | Contrato de eventos con occurredOn() |
| AggregateRoot | domain: common/model | Registrar eventos, consultar una copia inmutable y limpiar la lista |
| ApplicationException | application: common/exception | Base de excepciones con mensaje y causa opcional |

Estas clases usan únicamente Java. El registro de eventos es en memoria:
no publica mensajes ni persiste eventos. Cada agregado futuro heredará de
AggregateRoot y registrará sus eventos al crear o actualizar; restaurarlo desde
la base no debe generar un nuevo evento de registro.

Los primeros catorce contextos ya están implementados. La siguiente entrega
corresponde a patient_contacts y patient_allergies. Las entregas
agrupan 2–3 tablas sencillas; los contextos complejos se revisan individualmente.
Las migraciones existentes seguirán siendo la fuente de columnas y tipos.

### Contexto country: primera tabla implementada

La implementación de countries está documentada en [Guía de country](docs/country.md).
Incluye las tres capas, los cinco endpoints CRUD y pruebas de dominio,
casos de uso y conversión de persistencia. Las columnas se obtienen de V1;
los archivos de migración no se han modificado.

### Contexto stateregion: primera tabla con referencia a otro agregado

State_regions tiene las tres capas y sus cinco operaciones CRUD. Su país se
representa mediante CountryId en el dominio y UUID en JPA, sin ManyToOne.
Al registrar o actualizar, los casos de uso consultan el puerto CountryRepository
para comprobar la existencia del país. La FK de V2 sigue garantizando la
integridad referencial en PostgreSQL.

Consultar [Guía de state_regions](docs/stateregion.md) para los campos, rutas,
ejemplos y pruebas. La migración V2 conserva su contenido original.

### Contexto citymunicipality: ciudades y municipios

City_municipalities tiene las tres capas y sus cinco operaciones CRUD. La
región se representa por StateRegionId en el dominio y UUID en JPA.
Register y Update consultan StateRegionRepository para comprobar que exista.
Se conserva la columna code_citi y la diferencia temporal de V3: created_at
es TIMESTAMPTZ (OffsetDateTime) y updated_at es TIMESTAMP (LocalDateTime).

Consultar [Guía de city_municipalities](docs/citymunicipality.md) para las rutas,
campos, pruebas y ejemplos. La migración V3 conserva su contenido original.

### Catálogos documenttype, gender y professionaltype

Los contextos correspondientes a V4–V6 incluyen dominio, cinco casos de uso
CRUD, REST y persistencia JPA. Respetan las columnas y la unicidad del SQL:
code en document_types, description en genders y name en professional_types.
La actualización permite conservar el valor único del propio registro, pero
rechaza el de otro. Los conflictos se devuelven como 409 y las restricciones
SQL siguen protegiendo la integridad ante operaciones concurrentes.

Consultar [Guía de catálogos V4–V6](docs/catalogos-v4-v6.md) para los campos,
endpoints y ejemplos. Las migraciones existentes no se han modificado.

### Contextos relationshiptype y study

Relationship_types y studies incluyen las tres capas y sus cinco operaciones
CRUD. RelationshipType solo contiene id y description: no se añaden fechas
de auditoría ausentes de V7, aunque sus eventos sí tienen occurredOn.
Description es único. Study conserva las fechas de V8 y admite nombres
repetidos, porque name no lleva UNIQUE.

Consultar [Guía de relaciones y estudios](docs/relaciones-estudios.md) para los
campos, rutas, pruebas y ejemplos. V7 y V8 mantienen su contenido original.

### Contexto professional

Professionals incluye las tres capas, CRUD REST y persistencia según V9.
Referencia documentos, tipos profesionales y ciudades mediante IDs tipados
en el dominio y UUID en JPA. Se comprueba que existan antes de guardar.
La unicidad de documento, nombre, apellido y licencia se conserva tal como
está declarada. Ambas fechas usan OffsetDateTime para TIMESTAMPTZ.

Consultar [Guía de professionals](docs/professional.md). La migración V9
conserva su contenido original.

### Contexto contact

Contacts tiene las tres capas y su CRUD según V10. Referencia a una ciudad y
a los profesionales que crean y actualizan el registro mediante IDs, sin
asociaciones JPA entre agregados. El creador y createdAt se conservan al
actualizar; updatedBy admite null. Notes utiliza TEXT y las fechas TIMESTAMPTZ
se representan mediante OffsetDateTime. El correo admite valores repetidos.

Consultar [Guía de contacts](docs/contact.md). V10 no se ha modificado.

### Contextos phonecontact y emailcontact

Ambos contextos referencian ContactId y comprueban la existencia del contacto
al registrar y actualizar. PhoneContact permite notes nulo y no tiene fechas
de auditoría, según V11. EmailContact exige notes, conserva la auditoría
TIMESTAMP de V12 y valida el correo único, excluyendo el propio ID al actualizar.
No se añaden Column1 ni Column2 ni asociaciones JPA entre agregados.

Consultar [Guía de teléfonos y correos](docs/telefonos-correos.md). V11 y V12
mantienen su contenido original.

### Contexto professionalstudy

ProfessionalStudy incluye las tres capas y CRUD en `/api/professional-studies`.
Comprueba que existan el estudio, el profesional y el país antes de guardar.
ResolutionNumber admite null; no se impone unicidad a las asociaciones.
Las fechas TIMESTAMP se representan mediante LocalDateTime. V13 se conserva.
Consultar [Guía de estudios profesionales](docs/professionalstudy.md).

### Contexto patient

Patient incluye las tres capas y CRUD en `/api/patients`, según V14.
Comprueba las referencias a document_types, genders (sexo e identidad), ciudad
y profesionales de autoría. Los segundos nombres y apellidos, createdBy y
updatedBy admiten null. Solo email es único; al actualizar se excluye el propio
ID de esa comprobación. CreatedBy y createdAt se conservan.
BirthDate usa LocalDate y las fechas TIMESTAMP usan LocalDateTime.
Consultar [Guía de pacientes](docs/patient.md). V14 mantiene su contenido.

### Avance de implementación

| Parte del trabajo | Estado |
| --- | --- |
| Scripts de creación del esquema | 52 de 52 creados (100 %) |
| Contextos en dominio, aplicación e infraestructura | 14 de 52 implementados (26,9 %) |
| Integración HTTP y validación en PostgreSQL | Pendiente de conexión |

El porcentaje comunicado en cada entrega se calcula como contextos implementados
dividido por 52. Es una medida de cobertura, no de horas consumidas: la complejidad
de las tablas varía y no incluye la integración pendiente. Tener todos los SQL
creados no significa que se hayan ejecutado o validado en una base real.

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

### Requisitos

- JDK 25, de acuerdo con `<java.version>` del POM raíz; comprobar con `java -version`.
- PostgreSQL disponible y una base vacía para la primera ejecución.
- Un usuario con permisos para conectar y crear tablas y restricciones en el esquema destino.
- Acceso a las dependencias Maven en la primera compilación, o una caché local completa.

La versión de Spring Boot declarada en el POM es 4.1.1. El wrapper permite
compilar sin instalar Maven por separado. Todos los comandos siguientes se
ejecutan en PowerShell desde la raíz del proyecto.

### Preparar la conexión

La base PostgreSQL debe existir. Antes de iniciar el proyecto, definir en la
terminal `DB_URL` (formato `jdbc:postgresql://HOST:PUERTO/BASE`), `DB_USERNAME`
y `DB_PASSWORD` con los datos de conexión que se proporcionen. Por ejemplo,
para una base local de desarrollo llamada `mindconnect`:

```powershell
$env:DB_URL = 'jdbc:postgresql://localhost:5432/mindconnect'
$env:DB_USERNAME = 'usuario_desarrollo'
$env:DB_PASSWORD = 'reemplazar_por_la_clave_local'
```

Estos valores son ejemplos y deben reemplazarse. Las variables pertenecen a
la sesión actual de la terminal. `application.properties` contiene referencias
a ellas, sin credenciales guardadas. Un archivo `.env` no se carga automáticamente
con la configuración actual.

La base puede crearse desde pgAdmin o, con una cuenta autorizada, ejecutando:

```sql
CREATE DATABASE mindconnect;
```

Flyway crea las tablas dentro de la base; no crea la base PostgreSQL.

### Compilar y arrancar

Con un JDK 25 disponible, ejecutar desde la raíz:

```powershell
.\mvnw.cmd clean install -DskipTests
.\mvnw.cmd -pl infrastructure spring-boot:run
```

El primer comando compila todos los módulos e instala sus artefactos en el
repositorio Maven local. Esto permite que el segundo resuelva las dependencias
de application y domain al ejecutar infrastructure. `-DskipTests` omite la
ejecución de pruebas; un BUILD SUCCESS con esta opción no prueba la conexión.

También puede ejecutarse el JAR generado, con las mismas variables definidas:

```powershell
java -jar .\infrastructure\target\infrastructure-0.0.1-SNAPSHOT.jar
```

Para detener la ejecución desde la terminal, utilizar `Ctrl+C`.

### Comprobar el resultado

El inicio aplica las migraciones pendientes. Hibernate utiliza `validate` para
que Flyway gestione el esquema. Para comprobar el historial en PostgreSQL:

```sql
SELECT version, description, success
FROM flyway_schema_history
ORDER BY installed_rank;
```

En una primera ejecución completa deben aparecer las versiones 1 a 52 con
`success = true`. Revisar también las tablas y sus restricciones desde pgAdmin.
Para ejecutar la prueba de contexto una vez disponible la conexión:

```powershell
.\mvnw.cmd test
```

Esta prueba arranca el contexto de Spring Boot y puede aplicar migraciones;
utilizar una base de desarrollo dedicada. No es una prueba exhaustiva de las
52 tablas ni de las reglas de negocio.

La ejecución contra PostgreSQL está pendiente de los datos de conexión. Este
bloque crea estructura, no transfiere registros desde otra base de datos.
El siguiente paso es configurar la conexión y validar las 52 migraciones en
PostgreSQL. No modificar SQL
que ya se haya aplicado; los cambios posteriores requieren una nueva versión.

## Cómo reconstruir el proyecto

### Recuperar la aplicación desde el repositorio

1. Clonar el repositorio o recuperar una copia que contenga los POM, los tres
   módulos, las migraciones y el wrapper Maven.
2. Instalar o seleccionar JDK 25 y abrir la raíz en VS Code.
3. Definir las variables de conexión en una terminal nueva.
4. Ejecutar `.\mvnw.cmd clean install -DskipTests` y arrancar infrastructure
   con los comandos de la sección anterior.

Las carpetas `target` se regeneran al compilar. `clean` elimina resultados de
compilación; no borra tablas ni reinicia el historial de Flyway. Las referencias
de `revision_adjuntos` están ignoradas y no son necesarias para ejecutar la
copia del repositorio. Conservar aparte el diagrama y la documentación si se
necesitan para revisar o reproducir las decisiones del diseño.

### Reconstruir el esquema en una base nueva

1. Crear otra base vacía, por ejemplo `mindconnect_reconstruida`.
2. Cambiar `DB_URL` a `jdbc:postgresql://localhost:5432/mindconnect_reconstruida`
   y configurar las credenciales correspondientes.
3. Arrancar la aplicación. Flyway aplicará V1–V52 porque esa base no tiene historial.
4. Comprobar `flyway_schema_history` y las tablas generadas.

Este procedimiento reproduce el esquema sin eliminar la base anterior.
Los datos de una base existente requieren una copia de seguridad y restauración
o un proceso de transferencia independiente: los SQL actuales no los recuperan.
No borrar únicamente `flyway_schema_history` para repetir la migración sobre
tablas existentes; los CREATE TABLE volverían a intentar crear esas tablas.

### Continuar un esquema ya aplicado

Mantener V1–V52 y agregar el siguiente archivo, por ejemplo
`V53__descripcion_del_cambio.sql`, con los ALTER TABLE o instrucciones
necesarias. Revisar primero los datos existentes si se introducen restricciones
o cambios de tipo. Compilar y probar sobre una base de desarrollo antes de
ejecutar el cambio sobre una base con datos importantes.

## Cómo reproducir el trabajo desde el diagrama

Para volver a implementar el proyecto, seguir este orden:

1. Reunir el diagrama legible y la documentación de requisitos. Los proyectos
   de ejemplo sirven para orientar la estructura; el esquema se obtiene del diagrama.
2. Inventariar cada tabla, columna, tipo, longitud y marcas PK, FK, U y N.
   Registrar inconsistencias antes de escribir SQL. En esta implementación,
   U significa UNIQUE y N permite NULL; las demás columnas son obligatorias.
3. Dibujar las dependencias entre tablas y ordenar su creación: catálogos y
   tablas padre primero, tablas dependientes y de asociación después.
4. Crear el padre Maven y los módulos domain, application e infrastructure,
   con las dependencias en la dirección indicada arriba. Configurar el wrapper,
   Java y las dependencias de Spring Boot, PostgreSQL y Flyway.
5. Crear la clase principal en infrastructure y configurar el datasource,
   Flyway y Hibernate en su archivo application.properties.
6. Escribir una migración por tabla en `infrastructure/src/main/resources/db/migration`.
   Utilizar `Vnumero__descripcion.sql`, con dos guiones bajos; comenzar por
   `V1__create_countries_table.sql`. Mantener nombres, tipos y restricciones
   acordados, sin añadir defaults ni cascadas ausentes del diagrama.
7. Revisar el inventario de este README y las decisiones particulares de cada
   bloque: Column1/Column2 omitidas, nombres conservados, fechas obligatorias,
   marcas temporales interpretadas y la relación pendiente de proveedores de IA.
8. Verificar versiones consecutivas, referencias a tablas anteriores y
   compilación. Ejecutar las migraciones en una base vacía y revisar el historial
   y las restricciones. La compilación por sí sola no verifica el SQL en PostgreSQL.
9. Guardar fuentes y documentación en Git. Se puede repetir la revisión y el
   commit por bloques de diez migraciones, terminando con V51 y V52.

## Problemas frecuentes

| Síntoma | Qué revisar |
| --- | --- |
| Java no admite la versión de compilación | JDK 25 activo y configuración JAVA_HOME |
| Faltan DB_URL, DB_USERNAME o DB_PASSWORD | Definir las tres variables en la terminal que ejecuta la aplicación |
| PostgreSQL rechaza la conexión | Servidor iniciado, host, puerto, nombre de base, usuario y contraseña |
| Permiso denegado al crear tablas | Permisos del usuario sobre la base y el esquema destino |
| Maven no encuentra application o domain al arrancar infrastructure | Ejecutar primero `clean install -DskipTests` desde la raíz |
| Flyway detecta un checksum diferente | Comparar el SQL aplicado con Git; restaurar su contenido original y crear una nueva versión para el cambio |
| Una tabla ya existe en el primer inicio | Usar una base vacía; una base preexistente necesita un plan específico de adopción, no repetir CREATE TABLE |
| Java Projects muestra la organización anterior | Abrir la raíz y ejecutar Java: Clean Java Language Server Workspace |

Ante un error de Flyway, revisar el mensaje y el estado de la base antes de
reintentar. No cambiar checksums o borrar el historial para ocultar una diferencia
entre los archivos y el esquema instalado.
