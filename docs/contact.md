# Contexto contact

Implementación de contacts según V10, con Java 25 y com.migracion.rangel.
Desde Java Projects, abrir contact en src/main/java del módulo correspondiente.

## Estructura y referencias

Domain contiene Contact, ContactId, tres eventos, la excepción y el puerto.
Application contiene commands, response, excepción y cinco casos de uso CRUD.
Infrastructure contiene controller y requests, manejo de errores, entidad,
mapper, repositorio JPA, adaptador y configuración de beans.

CityMunicipalityId y ProfessionalId representan referencias a otros agregados.
JPA guarda sus UUID sin ManyToOne. Registro comprueba ciudad y creador;
actualización comprueba ciudad y, si se proporciona, actualizador. Las FK
de PostgreSQL garantizan la integridad final ante cambios concurrentes.

## Campos y auditoría

| JSON / Java | SQL | Comportamiento |
| --- | --- | --- |
| id | id UUID | Generado al registrar, recibido en la URL para otras operaciones |
| fullName | full_name VARCHAR(200) | Obligatorio, máximo 200 caracteres |
| email | email VARCHAR(150) | Obligatorio, máximo 150 caracteres; permite repetidos |
| notes | notes TEXT | Obligatorio, sin límite de VARCHAR |
| cityId | city_id UUID | Obligatorio, ciudad existente |
| createdAt | created_at TIMESTAMPTZ | Generado al registrar y conservado al actualizar |
| createdBy | created_by UUID | Profesional existente; recibido solo en POST |
| updatedAt | updated_at TIMESTAMPTZ | Generado al registrar y renovado al actualizar |
| updatedBy | updated_by UUID | Opcional; recibido en PUT; si se indica debe existir |

POST inicializa updatedBy como null. PUT reemplaza fullName, email, notes,
cityId y updatedBy; omitir updatedBy lo deja en null. No permite reemplazar
createdBy, createdAt ni el ID. Los IDs de autoría se proporcionan en los
requests; actualmente no se obtienen de una sesión de autenticación.

Ambas fechas usan OffsetDateTime y almacenamiento JPA nativo con zona;
se generan en UTC. PostgreSQL conserva el instante, aunque puede devolver
otro offset. Los eventos usan el contrato LocalDateTime occurredOn en UTC.
Register y Update registran eventos; Restore no genera eventos; Delete
devuelve su evento después de eliminar. No existe publicación externa.

No se añaden reglas de unicidad, formato de correo ni prohibición de cadenas
vacías, porque V10 no las declara. Tampoco se añaden cascadas.

## Endpoints

| Método | Ruta | Resultado |
| --- | --- | --- |
| POST | /api/contacts | 201 con el contacto |
| GET | /api/contacts | 200 con la lista |
| GET | /api/contacts/{id} | 200 con el contacto o 404 |
| PUT | /api/contacts/{id} | 200 con el contacto actualizado o 404 |
| DELETE | /api/contacts/{id} | 204 sin cuerpo o 404 |

Una ciudad o profesional referenciado inexistente produce 404. Requests
inválidos, UUID inválidos o longitudes excedidas producen 400. Una restricción
SQL que impida la operación produce 409, por ejemplo, eliminar un contacto
referenciado por teléfonos, correos o pacientes.

## Prueba manual en Postman

Arrancar con PostgreSQL según el README. Crear primero una ciudad y un
profesional con sus dependencias. Reemplazar los UUID de ejemplo por sus IDs
reales y enviar POST /api/contacts con Content-Type: application/json:

```json
{
  "fullName": "Ana Pérez",
  "email": "ana@example.com",
  "notes": "Contacto de prueba",
  "cityId": "00000000-0000-0000-0000-000000000001",
  "createdBy": "00000000-0000-0000-0000-000000000002"
}
```

Guardar el id y consultar la lista y el registro. Enviar PUT /api/contacts/{id}:

```json
{
  "fullName": "Ana Pérez",
  "email": "ana@example.com",
  "notes": "Notas actualizadas",
  "cityId": "00000000-0000-0000-0000-000000000001",
  "updatedBy": null
}
```

Probar también con updatedBy igual al ID de un profesional existente.
CreatedBy y createdAt deben conservarse. Intentar actualizar con una ciudad
o actualizador inexistente: debe devolver 404 sin modificar el contacto.
Repetir POST con el mismo email debe permitirse. Eliminar y repetir GET para
comprobar el 404.

## Pruebas automatizadas

Desde la raíz, en PowerShell:

```powershell
.\mvnw.cmd '-Dtest=*Test,!RangelApplicationTests' '-Dsurefire.failIfNoSpecifiedTests=false' test
```

Las pruebas de contact cubren CRUD, identidad y auditoría, creador inmutable,
actualizador opcional, límites de texto, notas obligatorias, actualización
inválida sin cambios parciales, referencias inexistentes sin guardar o modificar,
cambio de ciudad, correos repetidos y mapeo con actualizador presente o nulo.
Los repositorios son en memoria; HTTP y la validación JPA contra PostgreSQL
siguen pendientes de conexión.
