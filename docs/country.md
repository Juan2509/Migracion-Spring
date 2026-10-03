# Contexto country

Primera tabla implementada en las tres capas. Java 25 y paquete base
`com.migracion.rangel`. El esquema de referencia es
`infrastructure/src/main/resources/db/migration/V1__create_countries_table.sql`.

## Archivos y responsabilidades

Desde Java Projects, abrir src/main/java del módulo correspondiente:

| Módulo y paquete | Contenido |
| --- | --- |
| domain: domain/country/model | Country y CountryId |
| domain: domain/country/event | Eventos Registered, Updated y Deleted |
| domain: domain/country/exception | CountryNotFoundException |
| domain: domain/country/port/repository | CountryRepository, interfaz sin JPA |
| application: application/country | Commands, response, excepción y cinco casos de uso |
| infrastructure: infrastructure/country/adapters/in/rest | Controller, requests y manejo de errores |
| infrastructure: infrastructure/country/adapters/out/persistence | Entidad, mapper, repositorio JPA y adaptador |
| infrastructure: infrastructure/country/config | Registro de beans mediante CountryBeansConfig |

Dominio y aplicación no utilizan Spring, JPA ni Lombok. Infrastructure inyecta
los casos de uso mediante beans. El flujo es controller → caso de uso → puerto
CountryRepository → adaptador → JpaRepository → PostgreSQL.

## Campos

| JSON / Java | SQL | Requisito |
| --- | --- | --- |
| id | id UUID | Generado al registrar; recibido en la URL para consultar, actualizar y eliminar |
| nameCountry | name_country VARCHAR(50) | Obligatorio, máximo 50 caracteres |
| codeCountry | code_country VARCHAR(10) | Obligatorio, máximo 10 caracteres |
| description | description VARCHAR(100) | Obligatorio, máximo 100 caracteres |
| isActive | is_active BOOLEAN | Obligatorio; false es válido |
| telephonePrefix | telephone_prefix VARCHAR(5) | Obligatorio, máximo 5 caracteres |
| createdAt | created_at TIMESTAMP | Asignado al registrar y conservado al actualizar |
| updatedAt | updated_at TIMESTAMP | Asignado al registrar y renovado al actualizar |

Las fechas se representan mediante LocalDateTime. Los campos de texto permiten
cadenas vacías porque V1 no declara una restricción para impedirlas. No se
añade unicidad de nombres ni códigos. PUT reemplaza todos los campos editables;
no implementa una actualización parcial. El ID y las fechas no se toman del request.

register genera un evento; restore reconstruye sin eventos; update registra
el evento de actualización. DeleteCountryUseCase devuelve un evento tras
eliminar. Estos eventos no se publican ni almacenan por un sistema de mensajería.

## Endpoints

| Método | Ruta | Resultado |
| --- | --- | --- |
| POST | /api/countries | 201 con el país creado |
| GET | /api/countries | 200 con la lista, vacía si no hay países |
| GET | /api/countries/{id} | 200 con el país o 404 |
| PUT | /api/countries/{id} | 200 con el país actualizado o 404 |
| DELETE | /api/countries/{id} | 204 sin cuerpo o 404 |

JSON inválido, UUID inválido o incumplimiento de validaciones del request
produce 400. Una restricción de PostgreSQL que impida la operación produce
409; por ejemplo, borrar un país referenciado por state_regions. El mensaje
de conflicto no expone detalles internos de la base.

## Prueba manual en Postman

Configurar PostgreSQL y arrancar como indica el README. Usar el puerto del
servidor que aparezca en el arranque (8080 si no se cambia) y Content-Type:
application/json. Enviar POST /api/countries con:

```json
{
  "nameCountry": "Colombia",
  "codeCountry": "CO",
  "description": "País de prueba",
  "isActive": true,
  "telephonePrefix": "+57"
}
```

Guardar el id devuelto. Consultar la lista y GET /api/countries/{id}. Ejecutar
PUT con el mismo cuerpo cambiando description o isActive. Comprobar que
createdAt no cambia. Eliminar con DELETE y repetir GET para comprobar el 404.
Para verificar 400, omitir isActive o enviar un nombre que supere 50 caracteres.

## Verificaciones automatizadas

Desde la raíz, ejecutar en PowerShell:

```powershell
.\mvnw.cmd '-Dtest=CountryTest,CountryUseCasesTest,CountryPersistenceMapperTest' '-Dsurefire.failIfNoSpecifiedTests=false' test
```

Estas pruebas no requieren PostgreSQL. Comprueban eventos, auditoría,
actualización inválida sin cambios parciales, ciclo CRUD con repositorio en
memoria, errores por ID inexistente y conservación de campos en el mapper.
Se seleccionan explícitamente para no ejecutar la prueba de contexto que
requiere conexión. No sustituyen la prueba HTTP ni la validación JPA contra
PostgreSQL; esas verificaciones están pendientes hasta disponer de la conexión.
