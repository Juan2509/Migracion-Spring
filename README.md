# Rangel: esquema de MindConnect

Proyecto Spring Boot con migraciones Flyway para PostgreSQL. El diagrama de
MindConnect es la fuente del esquema; los proyectos compartidos se utilizan como
referencias de organización.

## Entrega 1: V1 a V10

Las migraciones están en `src/main/resources/db/migration`. Este bloque crea:

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

## Conexión y ejecución

La base PostgreSQL debe existir. Antes de iniciar el proyecto, definir en la
terminal `DB_URL` (formato `jdbc:postgresql://HOST:PUERTO/BASE`), `DB_USERNAME`
y `DB_PASSWORD` con los datos de conexión que se proporcionen.

Con un JDK 25 disponible, ejecutar desde la raíz:

```powershell
.\mvnw.cmd spring-boot:run
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
Después del commit de este bloque se continúa con V11–V20. No modificar SQL
que ya se haya aplicado; los cambios posteriores requieren una nueva versión.
