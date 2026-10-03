# Contexto citymunicipality

Implementación de city_municipalities según V3, con Java 25 y paquete base
com.migracion.rangel. Desde Java Projects, abrir el paquete citymunicipality
en src/main/java de domain, application o infrastructure.

## Estructura y referencia a la región

| Capa | Contenido |
| --- | --- |
| domain/citymunicipality | Agregado CityMunicipality, ID, tres eventos, excepción y puerto de repositorio |
| application/citymunicipality | Commands, response, excepción y cinco casos de uso CRUD |
| infrastructure/citymunicipality | Controller, requests, manejo de errores, entidad JPA, repositorio, mapper, adaptador y configuración de beans |

El dominio referencia a la región por StateRegionId y no contiene un agregado
StateRegion. JPA utiliza UUID para region_id sin ManyToOne. Los casos de uso
de registro y actualización comprueban la región mediante StateRegionRepository
antes de guardar o modificar. La FK de PostgreSQL sigue siendo la garantía
final ante cambios concurrentes.

## Campos y auditoría

| JSON / Java | SQL | Condición |
| --- | --- | --- |
| id | id UUID | Generado al registrar; recibido en la URL para otras operaciones |
| nameCity | name_city VARCHAR(50) | Obligatorio, máximo 50 caracteres |
| codeCiti | code_citi VARCHAR(10) | Obligatorio, máximo 10 caracteres; se conserva la grafía de V3 |
| description | description VARCHAR(100) | Obligatorio, máximo 100 caracteres |
| isActive | is_active BOOLEAN | Obligatorio; admite false |
| regionId | region_id UUID | Obligatorio; debe existir en state_regions |
| createdAt | created_at TIMESTAMPTZ | OffsetDateTime; generado en UTC al registrar, conservado al actualizar |
| updatedAt | updated_at TIMESTAMP | LocalDateTime; generado al registrar y renovado al actualizar |

TIMESTAMPTZ conserva el instante; PostgreSQL puede devolverlo con un offset
diferente al original. TIMESTAMP no incorpora zona horaria. No se igualan
ambos tipos porque V3 los distingue. El ID y las fechas se generan en el
servidor y no forman parte de los requests.

PUT reemplaza todos los campos editables y permite cambiar de región.
No se añaden UNIQUE, defaults ni cascadas. Los textos permiten cadenas vacías
porque el SQL no las prohíbe. register y update registran eventos; restore no
genera eventos. El caso de eliminación devuelve su evento después de eliminar.
No hay publicación ni persistencia externa de eventos.

## API

| Método | Ruta | Resultado |
| --- | --- | --- |
| POST | /api/city-municipalities | 201 con la ciudad creada |
| GET | /api/city-municipalities | 200 con la lista |
| GET | /api/city-municipalities/{id} | 200 con la ciudad o 404 |
| PUT | /api/city-municipalities/{id} | 200 con la ciudad actualizada o 404 |
| DELETE | /api/city-municipalities/{id} | 204 sin cuerpo o 404 |

POST y PUT producen 404 si la región no existe. Un request con campos
obligatorios ausentes, longitudes excedidas o UUID inválidos produce 400.
Las operaciones que incumplen restricciones de la base producen 409, como
eliminar una ciudad referenciada por pacientes, profesionales o contactos.

## Prueba manual

Con PostgreSQL configurado, arrancar como indica el README. Crear primero
un país y después una región. Usar el ID real de esa región en Postman y
enviar POST /api/city-municipalities con Content-Type: application/json:

```json
{
  "nameCity": "Medellín",
  "codeCiti": "MED",
  "description": "Ciudad de prueba",
  "isActive": true,
  "regionId": "00000000-0000-0000-0000-000000000001"
}
```

El UUID mostrado es un ejemplo que debe reemplazarse. Consultar la lista y
el ID creado. Modificar mediante PUT y comprobar que createdAt se conserva.
Intentar actualizar con una región inexistente: debe devolver 404 sin cambiar
la ciudad. Eliminarla y repetir GET para comprobar el 404.

## Pruebas sin PostgreSQL

Desde la raíz, en PowerShell:

```powershell
.\mvnw.cmd '-Dtest=CityMunicipalityTest,CityMunicipalityUseCasesTest,CityMunicipalityPersistenceMapperTest' '-Dsurefire.failIfNoSpecifiedTests=false' test
```

Las pruebas cubren eventos, auditoría, restauración sin eventos, actualización
inválida sin cambios parciales, CRUD, cambio de región y región inexistente
sin guardar ni modificar. El mapper conserva los campos, el UUID de región y
ambos tipos temporales, incluida una fecha de creación con offset distinto de UTC.
Estas pruebas no arrancan Spring ni necesitan PostgreSQL. La prueba HTTP y
la validación JPA en una base real quedan pendientes de conexión.
