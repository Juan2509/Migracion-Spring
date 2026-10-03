# Catálogos V4–V6

Implementación independiente de document_types, genders y professional_types
en las tres capas, con Java 25 y el paquete base com.migracion.rangel.
Los SQL V4–V6 son la fuente de campos y restricciones y conservan su contenido.

## Estructura desde Java Projects

Abrir src/main/java del módulo y el paquete documenttype, gender o
professionaltype dentro de com/migracion/rangel/domain, application o
infrastructure, respectivamente.

| Capa | Archivos por catálogo |
| --- | --- |
| domain | Agregado, ID, eventos Registered/Updated/Deleted, excepción y puerto del repositorio |
| application | Commands de registro y actualización, response, excepciones de ausencia y duplicidad, cinco casos de uso |
| infrastructure | Controller, requests, manejo de errores, entidad JPA, repositorio, mapper, adaptador y configuración de beans |

Domain y application utilizan Java puro. Los adaptadores y beans se encuentran
en infrastructure. Los tres catálogos no tienen llaves foráneas salientes.

## Campos

| Tabla | Campos editables del JSON | Restricción UNIQUE |
| --- | --- | --- |
| document_types | code: String (20), name: String (50), active: Boolean | code |
| genders | description: String (50) | description |
| professional_types | name: String (40) | name |

Los valores entre paréntesis son las longitudes máximas. Todos los campos son
obligatorios; active admite false. Gender y ProfessionalType no incorporan un
campo active porque no aparece en sus migraciones. Los textos admiten cadenas
vacías porque el SQL no las prohíbe. La unicidad es exacta; no se convierten a
minúsculas ni se eliminan espacios automáticamente.

Cada respuesta incluye id UUID, createdAt y updatedAt (LocalDateTime para
TIMESTAMP). El servidor genera el ID y ambas fechas al registrar. Al actualizar
conserva el ID y createdAt, renueva updatedAt y reemplaza todos los campos
editables; no hay actualización parcial.

## Unicidad y eventos

El puerto define una consulta de existencia por el campo único y otra que
excluye el ID del registro actualizado. Registro y actualización rechazan
duplicados antes de guardar; actualizar conservando el propio valor sí está
permitido. La restricción UNIQUE de PostgreSQL es la garantía final ante carreras
entre peticiones; una infracción de integridad también se traduce a 409.

No se modifica el agregado cuando la actualización se rechaza por duplicidad.
register y update registran eventos en memoria; restore no genera eventos.
El caso de eliminación devuelve su evento después de eliminar. No se publican
ni persisten eventos mediante un servicio externo.

## API

| Catálogo | Ruta base |
| --- | --- |
| DocumentType | /api/document-types |
| Gender | /api/genders |
| ProfessionalType | /api/professional-types |

Cada ruta ofrece las mismas operaciones:

| Método | Ruta | Resultado |
| --- | --- | --- |
| POST | Ruta base | 201 con el registro creado |
| GET | Ruta base | 200 con la lista |
| GET | Ruta base/{id} | 200 con el registro o 404 |
| PUT | Ruta base/{id} | 200 con el registro actualizado o 404 |
| DELETE | Ruta base/{id} | 204 sin cuerpo o 404 |

Requests inválidos, UUID inválidos o longitudes excedidas producen 400.
Los valores únicos duplicados y las restricciones que impiden eliminar un
registro referenciado producen 409. No se incorporan cascadas.

## Ejemplos para Postman

Con PostgreSQL configurado y la aplicación iniciada según el README, enviar
Content-Type: application/json. Cada ejemplo se envía a su ruta base mediante POST.

DocumentType:

```json
{ "code": "CC", "name": "Cédula de ciudadanía", "active": true }
```

Gender:

```json
{ "description": "Femenino" }
```

ProfessionalType:

```json
{ "name": "Psicólogo" }
```

Guardar el id devuelto, consultar la lista y GET /{id}, actualizar con PUT
y comprobar que createdAt no cambia. Repetir POST con el mismo valor único
para comprobar el 409. Crear un segundo registro con otro valor e intentar
actualizarlo usando el valor único del primero: debe producir 409 sin alterar
el segundo. Actualizar el primer registro conservando su propio valor debe
permitirse. Finalmente eliminar y repetir GET para comprobar el 404.

## Pruebas automatizadas

Desde la raíz, en PowerShell, ejecutar todas las pruebas unitarias disponibles
sin la prueba de contexto que necesita PostgreSQL:

```powershell
.\mvnw.cmd '-Dtest=*Test,!RangelApplicationTests' '-Dsurefire.failIfNoSpecifiedTests=false' test
```

Por cada catálogo se prueban registro, restauración, eventos, auditoría,
actualización inválida sin cambios parciales, CRUD, IDs inexistentes,
duplicidad al registrar y actualizar, conservación del propio valor único y
conversión de los campos dominio/JPA. Los repositorios de estas pruebas son
en memoria. Las consultas derivadas de Spring Data y las restricciones reales
de PostgreSQL requieren la prueba de integración, aún pendiente de conexión,
al igual que la prueba HTTP.
