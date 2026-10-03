# Catálogos de encuentros (V19–V21)

Los tres contextos mantienen la estructura de dominio, aplicación e
infraestructura y servirán como referencias del contexto encounters (V22).

| Migración | Tabla | Contexto Java | Ruta base |
| --- | --- | --- | --- |
| V19 | encounter_types | encountertype | /api/encounter-types |
| V20 | encounter_modalities | encountermodality | /api/encounter-modalities |
| V21 | encounter_statuses | encounterstatus | /api/encounter-statuses |

En Java Projects, los archivos están en src/main/java de cada módulo, bajo
com/migracion/rangel. Domain contiene agregados, IDs, eventos y puertos;
application contiene comandos, respuestas, excepciones y cinco casos de uso;
infrastructure contiene solicitudes REST, controladores, manejo de errores,
beans, entidades JPA, mappers y adaptadores. Los SQL permanecen en
infrastructure/src/main/resources/db/migration.

## Campos y reglas

| Campo JSON | Tipo | Restricción |
| --- | --- | --- |
| code | String | Obligatorio, máximo 20 caracteres, único |
| name | String | Obligatorio, máximo 50 caracteres, único |
| active | Boolean | Obligatorio; admite false |

Cada tabla tiene su propio espacio de valores únicos: el mismo código o nombre
puede aparecer en catálogos diferentes. Dentro de cada tabla, código y nombre
son únicos por separado. Al actualizar se excluye el propio ID, por lo que se
pueden conservar ambos valores. No se normalizan mayúsculas ni espacios.
Las restricciones UNIQUE de PostgreSQL protegen ante escrituras concurrentes.

El ID se genera al registrar. CreatedAt y updatedAt usan LocalDateTime porque
las tres migraciones declaran TIMESTAMP. CreatedAt se conserva al actualizar.
Restaurar desde JPA no genera nuevos eventos. Los eventos permanecen en memoria,
sin publicación ni persistencia independiente.

No se cargan catálogos predefinidos. Active se puede cambiar con PUT; no impone
por sí solo restricciones sobre futuros encuentros. Estos catálogos no definen
reglas de transición de estados ni relaciones entre tipos y modalidades.

## API REST

Las tres rutas base ofrecen las mismas operaciones:

| Método | Ruta | Resultado |
| --- | --- | --- |
| POST | Ruta base | 201 y registro creado |
| GET | Ruta base | 200 y lista |
| GET | Ruta base/{id} | 200 y registro |
| PUT | Ruta base/{id} | 200 y registro actualizado |
| DELETE | Ruta base/{id} | 204 |

POST y PUT reciben los tres campos obligatorios. PUT reemplaza todos los campos
editables. La respuesta añade id, createdAt y updatedAt. Una solicitud inválida
produce 400, un ID inexistente 404 y código o nombre duplicados 409. Una violación
de integridad de persistencia también produce 409, por ejemplo al eliminar un
catálogo referenciado por encuentros una vez implementado ese contexto.

Ejemplos para Postman, sin implicar datos precargados:

```json
{"code": "CONSULTATION", "name": "Consulta", "active": true}
```

Enviar a `/api/encounter-types`.

```json
{"code": "IN_PERSON", "name": "Presencial", "active": true}
```

Enviar a `/api/encounter-modalities`.

```json
{"code": "COMPLETED", "name": "Finalizado", "active": false}
```

Enviar a `/api/encounter-statuses`. Configurar conexión y arranque según el README.

## Verificación

Las dieciocho pruebas nuevas cubren CRUD, valores false, auditoría, eventos,
restauración sin eventos, límites y obligatoriedad, unicidad independiente de
código y nombre, conservación de valores propios, rechazo sin mutación ni
guardado y conversión JPA de todas las columnas.

```powershell
.\mvnw.cmd -o '-Dtest=*Test,!RangelApplicationTests' '-Dsurefire.failIfNoSpecifiedTests=false' test
```

Las pruebas unitarias utilizan puertos en memoria. La integración HTTP y
PostgreSQL sigue pendiente de conexión. V19–V21 no se modifican.
