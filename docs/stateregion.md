# Contexto stateregion

Implementación de state_regions en dominio, aplicación e infraestructura,
según `V2__create_state_regions_table.sql`. Mantiene Java 25 y el paquete
base com.migracion.rangel. Desde Java Projects, abrir el paquete stateregion
dentro de src/main/java del módulo correspondiente.

## Estructura y comportamiento

| Capa | Contenido |
| --- | --- |
| domain/stateregion | StateRegion, StateRegionId, tres eventos, excepción y puerto StateRegionRepository |
| application/stateregion | Commands de registro y actualización, response, excepción y cinco casos de uso |
| infrastructure/stateregion | Controller y requests REST, manejo de errores, entidad y repositorio JPA, mapper, adaptador y beans |

Domain y application utilizan Java puro. CountryId referencia al país en el
agregado; no contiene un objeto Country. La entidad guarda country_id como
UUID sin asociación ManyToOne. Los casos de registro y actualización reciben
CountryRepository por constructor para comprobar que el país exista antes
de guardar o modificar el agregado. Si el país desaparece entre esa consulta
y el guardado, la FK de PostgreSQL rechazará la operación.

## Campos

| JSON / Java | SQL | Condición |
| --- | --- | --- |
| id | id UUID | Generado al registrar; recibido en la URL para otras operaciones |
| nameRegion | name_region VARCHAR(50) | Obligatorio, máximo 50 caracteres |
| codeRegion | code_region VARCHAR(10) | Obligatorio, máximo 10 caracteres |
| description | description VARCHAR(100) | Obligatorio, máximo 100 caracteres |
| isActive | is_active BOOLEAN | Obligatorio; admite false |
| countryId | country_id UUID | Obligatorio; debe corresponder a un país existente |
| createdAt | created_at TIMESTAMP | Asignado al registrar; no cambia al actualizar |
| updatedAt | updated_at TIMESTAMP | Asignado al registrar y actualizado al modificar |

Las fechas utilizan LocalDateTime y no se reciben en los requests. PUT
reemplaza todos los campos editables y permite cambiar el país. No se agregan
restricciones UNIQUE ni reglas de borrado en cascada. Los textos admiten cadenas
vacías porque el SQL no las prohíbe. register y update registran eventos;
restore no genera eventos. El caso de eliminación devuelve el evento después
de eliminar; no existe publicación externa de eventos.

## API y prueba manual

| Método | Ruta | Resultado |
| --- | --- | --- |
| POST | /api/state-regions | 201 con la región creada |
| GET | /api/state-regions | 200 con la lista |
| GET | /api/state-regions/{id} | 200 con la región o 404 |
| PUT | /api/state-regions/{id} | 200 con la región actualizada o 404 |
| DELETE | /api/state-regions/{id} | 204 sin cuerpo o 404 |

POST y PUT devuelven 404 si el país no existe. Requests que omiten campos
obligatorios, exceden longitudes o contienen UUID inválidos producen 400.
Una restricción de la base que impida la operación produce 409, por ejemplo,
eliminar una región referenciada por city_municipalities.

Arrancar con PostgreSQL como indica el README. Crear primero un país mediante
/api/countries y utilizar su id en POST /api/state-regions. En Postman, enviar
Content-Type: application/json y sustituir el UUID de ejemplo:

```json
{
  "nameRegion": "Antioquia",
  "codeRegion": "ANT",
  "description": "Región de prueba",
  "isActive": true,
  "countryId": "00000000-0000-0000-0000-000000000001"
}
```

Consultar la lista y el ID devuelto, actualizar mediante PUT y comprobar que
createdAt se conserva. Intentar registrar o actualizar con un país inexistente
para comprobar el 404; la región anterior debe permanecer sin cambios.
Eliminar la región y repetir GET para comprobar el 404.

## Pruebas sin PostgreSQL

Desde la raíz, en PowerShell:

```powershell
.\mvnw.cmd '-Dtest=StateRegionTest,StateRegionUseCasesTest,StateRegionPersistenceMapperTest' '-Dsurefire.failIfNoSpecifiedTests=false' test
```

Las pruebas cubren eventos, auditoría, restauración, actualización inválida
sin cambios parciales, ciclo CRUD, cambio de país, país inexistente sin guardar
o modificar y conservación de todos los campos al convertir dominio/JPA.
Utilizan repositorios en memoria, sin arrancar Spring ni conectar PostgreSQL.
La prueba HTTP y la validación del mapeo contra el esquema real están pendientes
de conexión; la compilación y estas pruebas no las sustituyen.
