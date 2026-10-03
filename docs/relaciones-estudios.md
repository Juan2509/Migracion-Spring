# Contextos relationshiptype y study

Implementación de relationship_types y studies según V7 y V8. Conserva Java
25 y com.migracion.rangel. Desde Java Projects, abrir los paquetes
relationshiptype y study en src/main/java del módulo correspondiente.

## Estructura

Cada contexto tiene su agregado, ID, eventos Registered/Updated/Deleted,
excepción y puerto de repositorio en domain; commands, response, excepciones
y cinco casos de uso en application; controller, requests, manejo de errores,
entidad JPA, repositorio, mapper, adaptador y beans en infrastructure.
Dominio y aplicación usan Java puro. Ambos catálogos carecen de FK salientes.

## Campos y diferencias

| Contexto | Campos editables | Campos generados | Unicidad |
| --- | --- | --- | --- |
| RelationshipType | description: String, obligatorio, máximo 50 caracteres | id UUID | description |
| Study | name: String, obligatorio, máximo 40 caracteres | id UUID, createdAt y updatedAt | Ninguna para name |

RelationshipType no tiene createdAt, updatedAt ni active, porque V7 no los
declara. Su respuesta contiene únicamente id y description. Los eventos
tienen una fecha occurredOn en memoria; esa fecha no representa una columna
de la tabla ni se persiste automáticamente.

Study no tiene active. Su respuesta contiene id, name, createdAt y updatedAt.
Las fechas son LocalDateTime para TIMESTAMP: ambas se asignan al registrar;
al actualizar se conserva createdAt y se renueva updatedAt. Diferentes
registros pueden compartir nombre, también después de actualizar.

PUT reemplaza todos los campos editables. Los textos permiten cadenas vacías
porque el SQL no las prohíbe. La descripción única de relaciones se comprueba
antes de guardar, excluyendo el propio ID al actualizar; la restricción UNIQUE
de PostgreSQL sigue siendo la garantía final ante operaciones concurrentes.

Register y Update generan eventos; Restore no genera eventos. Delete devuelve
un evento después de eliminar. No existe publicación externa de eventos.

## API

| Contexto | Ruta base |
| --- | --- |
| RelationshipType | /api/relationship-types |
| Study | /api/studies |

| Método | Ruta | Resultado |
| --- | --- | --- |
| POST | Ruta base | 201 con el registro creado |
| GET | Ruta base | 200 con la lista |
| GET | Ruta base/{id} | 200 con el registro o 404 |
| PUT | Ruta base/{id} | 200 con el registro actualizado o 404 |
| DELETE | Ruta base/{id} | 204 sin cuerpo o 404 |

Requests o UUID inválidos producen 400. Una descripción de relación duplicada
produce 409; conservar la descripción del propio registro al actualizar está
permitido. Un nombre de estudio repetido se permite. Una FK que impida
eliminar un registro referenciado produce 409; no se añaden cascadas.

## Pruebas manuales en Postman

Configurar y arrancar con PostgreSQL según el README. Enviar JSON con
Content-Type: application/json a la ruta base correspondiente:

RelationshipType:

```json
{ "description": "Madre" }
```

Study:

```json
{ "name": "Psicología" }
```

Guardar el id, consultar, actualizar mediante PUT y eliminar. GET después de
eliminar debe producir 404. Repetir POST de la misma relación debe producir
409. Repetir POST del mismo estudio debe producir 201 y generar otro ID.
Actualizar otro estudio para compartir ese nombre también debe permitirse.

## Pruebas automatizadas

Desde la raíz, en PowerShell:

```powershell
.\mvnw.cmd '-Dtest=*Test,!RangelApplicationTests' '-Dsurefire.failIfNoSpecifiedTests=false' test
```

Las pruebas de estos contextos cubren eventos, restauración, límites de
longitud, actualización inválida sin cambios parciales, CRUD, IDs inexistentes,
descripción duplicada, conservación de la descripción propia y nombres de
estudio repetidos al registrar y actualizar. También comprueban los mappers
y que RelationshipTypeJpaEntity solo tenga las dos columnas de V7.

Las pruebas utilizan repositorios en memoria y no necesitan PostgreSQL. La
prueba HTTP, las consultas derivadas JPA y la validación contra la base real
siguen pendientes de conexión.
