# Contextos phonecontact y emailcontact

Implementación según V11 y V12, con Java 25 y com.migracion.rangel. Desde
Java Projects, abrir phonecontact o emailcontact en src/main/java de cada módulo.

Domain contiene el agregado, ID, eventos, excepción y puerto por contexto.
Application contiene commands, response y cinco casos de uso. Infrastructure
contiene controller, requests, manejo de errores, entidad, mapper, repositorios,
adaptador y beans. Dominio y aplicación utilizan Java puro.

## Campos y reglas

| Campo | PhoneContact | EmailContact |
| --- | --- | --- |
| id | UUID generado por el servidor | UUID generado por el servidor |
| contactId | ContactId obligatorio; contacto existente | ContactId obligatorio; contacto existente |
| phone / email | phone: VARCHAR(30) obligatorio | email: VARCHAR(150) obligatorio y único |
| notes | TEXT opcional; admite null | TEXT obligatorio |
| createdAt | No existe en V11 | LocalDateTime para TIMESTAMP, generado al registrar |
| updatedAt | No existe en V11 | LocalDateTime para TIMESTAMP, generado al registrar y renovado al actualizar |

Las referencias se representan como ContactId en dominio y UUID en JPA, sin
ManyToOne. Registro y actualización comprueban el contacto mediante su puerto.
PUT reemplaza los campos editables y permite cambiar de contacto. El ID y,
en EmailContact, createdAt se conservan. PhoneContact no incorpora Column1,
Column2 ni auditoría ausentes de la migración acordada.

Los números de teléfono permiten duplicados. Los correos de email_contacts
son únicos entre todos los contactos; esto es independiente de contacts.email,
que sí permite duplicados. Actualizar conservando el propio correo está
permitido. No se impone un formato adicional a teléfonos/correos ni se prohíben
cadenas vacías, porque esos requisitos no figuran en el SQL.

Register y Update registran eventos en memoria; Restore no genera eventos;
Delete devuelve su evento tras eliminar. La fecha occurredOn de eventos de
PhoneContact no representa una columna persistida. No hay publicación externa
de eventos. Las FK y UNIQUE de PostgreSQL garantizan la integridad final ante
operaciones concurrentes.

## Endpoints

| Contexto | Ruta base |
| --- | --- |
| PhoneContact | /api/phone-contacts |
| EmailContact | /api/email-contacts |

Cada ruta ofrece POST (201), GET de lista (200), GET /{id} (200), PUT /{id}
(200) y DELETE /{id} (204). IDs inexistentes o contactos referenciados que no
existen producen 404. Requests inválidos, UUID inválidos o longitudes excedidas
producen 400. Correo duplicado en email_contacts o conflictos de restricciones
SQL producen 409.

## Prueba manual en Postman

Configurar PostgreSQL y arrancar según el README. Crear primero un contacto
con sus referencias. Sustituir el UUID de ejemplo por su ID real y enviar JSON
con Content-Type: application/json a la ruta correspondiente.

POST /api/phone-contacts:

```json
{
  "contactId": "00000000-0000-0000-0000-000000000001",
  "phone": "+57 3001234567",
  "notes": null
}
```

POST /api/email-contacts:

```json
{
  "contactId": "00000000-0000-0000-0000-000000000001",
  "email": "ana@example.com",
  "notes": "Correo principal"
}
```

Consultar, actualizar con PUT usando el mismo formato y eliminar. Repetir GET
después de eliminar debe producir 404. Probar un contacto inexistente en POST
y PUT: debe producir 404 sin modificar registros existentes. Repetir el teléfono
se permite; repetir el correo produce 409, incluso para otro contacto. En PUT
de correo, conservar el propio valor debe permitirse. Notes nulo se acepta solo
en PhoneContact.

## Pruebas automatizadas

Desde la raíz, en PowerShell:

```powershell
.\mvnw.cmd '-Dtest=*Test,!RangelApplicationTests' '-Dsurefire.failIfNoSpecifiedTests=false' test
```

Se prueban CRUD, restauración y eventos, longitudes, notas opcionales/obligatorias,
actualización inválida sin cambios parciales, contactos inexistentes sin guardar
ni modificar, cambio de contacto, teléfonos repetidos, correo único y actualización
con el propio correo, auditoría de EmailContact y conversión de todos los campos.
Los repositorios son en memoria. HTTP, consultas JPA y restricciones reales en
PostgreSQL siguen pendientes de conexión.
