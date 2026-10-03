# Contexto professional

Implementación de professionals según V9. Conserva Java 25 y el paquete base
com.migracion.rangel. Desde Java Projects, abrir professional en src/main/java
de domain, application o infrastructure.

## Estructura

Domain contiene Professional, ProfessionalId, tres eventos, la excepción y
el puerto ProfessionalRepository. Application contiene los commands, response,
excepciones de ausencia y duplicidad y los cinco casos de uso. Infrastructure
incluye controller, requests, manejo de errores, entidad, mapper, repositorio
JPA, adaptador y beans. Dominio y aplicación utilizan Java puro.

Las referencias se expresan como DocumentTypeId, ProfessionalTypeId y
CityMunicipalityId, sin incluir los otros agregados. En JPA se guardan UUID
sin asociaciones ManyToOne. Registro y actualización consultan los puertos
correspondientes para comprobar que los tres registros existan.

## Campos

| JSON / Java | SQL | Condición |
| --- | --- | --- |
| id | id UUID | Generado al registrar; recibido en la URL para otras operaciones |
| documentTypeId | document_type_id UUID | Obligatorio, documento existente |
| documentNumber | document_number VARCHAR(30) | Obligatorio y único |
| firstName | first_name VARCHAR(60) | Obligatorio y único |
| lastName | last_name VARCHAR(60) | Obligatorio y único |
| professionalType | professional_type UUID | Obligatorio, tipo profesional existente |
| licenseNumber | license_number VARCHAR(100) | Obligatorio y único |
| active | active BOOLEAN | Obligatorio; admite false |
| cityId | city_id UUID | Obligatorio, ciudad existente |
| createdAt | created_at TIMESTAMPTZ | Asignado al registrar y conservado al actualizar |
| updatedAt | updated_at TIMESTAMPTZ | Asignado al registrar y renovado al actualizar |

Los límites de los textos corresponden al SQL. No se prohíben cadenas vacías
porque V9 no declara esa condición. La columna professional_type conserva
su nombre sin añadir el sufijo _id. Los UUID y las fechas de respuesta no
forman parte del cuerpo de registro o actualización, salvo las tres referencias.

Las dos fechas son OffsetDateTime, generadas en UTC. JPA utiliza almacenamiento
temporal nativo con zona para ambas. PostgreSQL conserva el instante, aunque
puede devolverlo con un offset distinto. Los eventos siguen el contrato
compartido DomainEvent con LocalDateTime; en este contexto occurredOn se
obtiene en UTC. No hay publicación ni persistencia externa de eventos.

## Restricciones y actualización

V9 establece UNIQUE por separado sobre documento, nombre, apellido y licencia.
Esto impide que dos profesionales compartan incluso un nombre o un apellido;
se conserva la decisión del esquema. No se agrega unicidad sobre las referencias.

Antes de guardar se comprueban los cuatro valores únicos. Al actualizar se
excluye el propio ID, permitiendo conservar los valores actuales. Una referencia
inexistente o un valor único perteneciente a otro registro rechazan la operación
sin modificar el agregado. Las FK y UNIQUE de PostgreSQL son la garantía final
ante cambios concurrentes.

PUT reemplaza todos los campos editables y permite cambiar las referencias.
El ID y createdAt se conservan. Register y Update generan eventos en memoria;
Restore reconstruye sin eventos; Delete devuelve su evento después de eliminar.

## API

| Método | Ruta | Resultado |
| --- | --- | --- |
| POST | /api/professionals | 201 con el profesional creado |
| GET | /api/professionals | 200 con la lista |
| GET | /api/professionals/{id} | 200 con el profesional o 404 |
| PUT | /api/professionals/{id} | 200 con el profesional actualizado o 404 |
| DELETE | /api/professionals/{id} | 204 sin cuerpo o 404 |

Un documento, tipo profesional o ciudad inexistente produce 404 en POST y PUT.
Requests inválidos, UUID inválidos o longitudes excedidas producen 400.
Un valor único duplicado o una restricción SQL que impida la operación produce
409. No se añaden borrados en cascada.

## Prueba manual en Postman

Configurar PostgreSQL y arrancar como indica el README. Crear previamente un
tipo de documento, un tipo profesional y una ciudad (esta requiere región y
país). Sustituir los tres UUID de ejemplo por sus IDs reales. Enviar POST a
/api/professionals con Content-Type: application/json:

```json
{
  "documentTypeId": "00000000-0000-0000-0000-000000000001",
  "documentNumber": "123456789",
  "firstName": "Ana",
  "lastName": "Pérez",
  "professionalType": "00000000-0000-0000-0000-000000000002",
  "licenseNumber": "LIC-001",
  "active": true,
  "cityId": "00000000-0000-0000-0000-000000000003"
}
```

Guardar el id devuelto, consultar la lista y el registro, actualizar con PUT
y verificar que createdAt se conserva. Probar cada referencia con un UUID
inexistente: debe producir 404 sin modificar el registro. Para probar duplicidad,
cambiar todos los valores únicos excepto el que se desea comprobar y enviar
otro POST: debe producir 409. Eliminar y repetir GET para comprobar el 404.

## Pruebas automatizadas

Desde la raíz, en PowerShell:

```powershell
.\mvnw.cmd '-Dtest=*Test,!RangelApplicationTests' '-Dsurefire.failIfNoSpecifiedTests=false' test
```

Las pruebas de professional cubren los límites de todos los VARCHAR, auditoría
con zona, eventos y restauración; actualización inválida sin cambios parciales;
CRUD; las tres referencias inexistentes en registro y actualización; cambio
de referencias; las cuatro reglas de unicidad y conservación de los valores
propios; conversión dominio/JPA con todos los campos y fechas con distintos offsets.

Los repositorios de aplicación son en memoria. Estas pruebas no sustituyen
las consultas reales de Spring Data, los constraints ni la prueba HTTP. La
integración y validación JPA contra PostgreSQL quedan pendientes de conexión.
