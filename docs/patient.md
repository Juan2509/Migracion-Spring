# Pacientes (V14)

El contexto `patient` mantiene las tres capas del proyecto. En Java Projects,
domain contiene el agregado Patient, PatientId, eventos y puerto de repositorio;
application contiene comandos, respuesta, excepciones y cinco casos de uso;
infrastructure contiene solicitudes REST, controlador, manejo de errores,
configuración de beans, entidad JPA, mapper y adaptador de persistencia.
Los archivos están bajo src/main/java de cada módulo, en com/migracion/rangel.
El SQL permanece en infrastructure/src/main/resources/db/migration.

## Campos y referencias

| Campo JSON | Tipo | Restricción de V14 |
| --- | --- | --- |
| documentTypeId | UUID | Obligatorio; document_types.id |
| documentNumber | String | Obligatorio; máximo 30; admite repetidos |
| firstName | String | Obligatorio; máximo 50 |
| middleName | String | Opcional; máximo 50 |
| lastName | String | Obligatorio; máximo 50 |
| secondLastName | String | Opcional; máximo 50 |
| birthDate | Fecha YYYY-MM-DD | Obligatoria; DATE |
| biologicalSexId | UUID | Obligatorio; genders.id |
| genderIdentity | UUID | Obligatorio; genders.id |
| email | String | Obligatorio; máximo 150; único |
| phone | String | Obligatorio; máximo 30 |
| address | String | Obligatorio; máximo 250 |
| active | Boolean | Obligatorio; admite false |
| cityId | UUID | Obligatorio; city_municipalities.id |
| createdBy | UUID | Opcional; professionals.id; solo al registrar |
| updatedBy | UUID | Opcional; professionals.id; solo al actualizar |

Los campos con FK usan IDs tipados en el dominio y UUID en JPA, sin @ManyToOne.
La aplicación comprueba su existencia antes de guardar; las FK del SQL protegen
la integridad ante cambios concurrentes. Un autor null no exige consultar un
profesional. Sexo e identidad se validan de forma independiente en el catálogo
genders; no se crea otro catálogo ni se renombra gender_identity.

La unicidad de email es exacta, sin conversión a minúsculas. Al actualizar se
excluye el propio ID, permitiendo conservar el correo. La base mantiene la
restricción UNIQUE ante registros concurrentes. No se añade unicidad al documento,
nombres, teléfono ni combinaciones de campos. No se añaden reglas sobre edad,
formato de correo o contenido no vacío que no estén en el esquema.

Id se genera al registrar. CreatedAt y updatedAt se generan como LocalDateTime
(TIMESTAMP). BirthDate usa LocalDate. CreatedBy y createdAt se conservan en PUT;
updatedBy puede cambiar o quedar null. Al registrar, updatedBy comienza en null.
Restaurar un agregado desde JPA conserva sus datos y no produce nuevos eventos.
Los eventos de registro y actualización permanecen en memoria.

## API REST

| Método | Ruta | Resultado |
| --- | --- | --- |
| POST | /api/patients | 201 y paciente creado |
| GET | /api/patients | 200 y lista |
| GET | /api/patients/{id} | 200 y paciente |
| PUT | /api/patients/{id} | 200 y paciente actualizado |
| DELETE | /api/patients/{id} | 204 |

La respuesta incluye todos los datos, id, createdAt, createdBy, updatedAt y
updatedBy. Un paciente o referencia inexistente produce 404; correo duplicado
o violación de integridad de persistencia produce 409. Una solicitud que incumple
los límites o valores obligatorios produce 400. PUT reemplaza los campos editables
y requiere todos los obligatorios; sus opcionales pueden enviarse como null.

Crear primero el documento, los géneros y una ciudad con su región y país.
Si se envía createdBy o updatedBy, crear también el profesional con sus referencias.
Ejemplo de POST para Postman: sustituir los UUID por IDs existentes.

```json
{
  "documentTypeId": "11111111-1111-1111-1111-111111111111",
  "documentNumber": "DOC-123",
  "firstName": "Ana",
  "middleName": null,
  "lastName": "Pérez",
  "secondLastName": null,
  "birthDate": "1990-01-02",
  "biologicalSexId": "22222222-2222-2222-2222-222222222222",
  "genderIdentity": "33333333-3333-3333-3333-333333333333",
  "email": "ana@example.com",
  "phone": "3001234567",
  "address": "Calle 1",
  "active": true,
  "cityId": "44444444-4444-4444-4444-444444444444",
  "createdBy": null
}
```

Para PUT, usar el ID del paciente en la ruta y sustituir createdBy por updatedBy
en el cuerpo. Configurar conexión y arranque conforme al README.

## Verificación

Las ocho pruebas nuevas cubren los límites de todos los textos, nacimiento
obligatorio, opcionales, auditoría, eventos, CRUD, correo único, conservación del
correo propio, cambio de referencias, referencias inexistentes sin mutación
parcial y conversión JPA completa con datos opcionales nulos o presentes.

```powershell
.\mvnw.cmd -o '-Dtest=*Test,!RangelApplicationTests' '-Dsurefire.failIfNoSpecifiedTests=false' test
```

Las pruebas unitarias usan puertos en memoria. La integración HTTP y PostgreSQL
sigue pendiente de conexión; compilar no ejecuta Flyway. V14 no se modifica.
