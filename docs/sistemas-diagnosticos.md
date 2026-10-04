# Sistemas diagnósticos: V34

DiagnosticSystem implementa diagnostic_systems en los paquetes diagnosticsystem
de domain, application e infrastructure. Las fuentes están en src/main/java
de cada módulo y se pueden editar desde Java Projects.

| Columna SQL | Campo Java | Regla |
| --- | --- | --- |
| id | DiagnosticSystemId / UUID | Clave primaria; generado al registrar |
| code | String | Obligatorio, máximo 20 caracteres, único |
| name | String | Obligatorio, máximo 50 caracteres; admite repetidos |
| active | Boolean | Obligatorio |
| version | String | Obligatorio, máximo 20 caracteres |
| created_at | LocalDateTime | TIMESTAMP obligatorio; conservado al actualizar |
| updated_at | LocalDateTime | TIMESTAMP obligatorio; actualizado por el dominio |

No se añaden referencias, datos iniciales ni reglas sobre el formato de version.
La migración V34 conserva su contenido.

## API

Ruta base: /api/diagnostic-systems.

| Método y ruta | Operación | Respuesta correcta |
| --- | --- | --- |
| POST /api/diagnostic-systems | Registrar | 201 |
| GET /api/diagnostic-systems | Listar | 200 |
| GET /api/diagnostic-systems/{id} | Consultar | 200 |
| PUT /api/diagnostic-systems/{id} | Actualizar | 200 |
| DELETE /api/diagnostic-systems/{id} | Eliminar | 204 |

POST y PUT reciben:

```json
{
  "code": "ICD",
  "name": "Clasificación internacional",
  "active": true,
  "version": "11"
}
```

La respuesta incluye id, code, name, active, version, createdAt y updatedAt.
Un ID inexistente devuelve 404; un código de otro registro devuelve 409.
Actualizar permite conservar el código propio. La restricción UNIQUE de
PostgreSQL protege ante escrituras concurrentes.

## Capas y pruebas

Domain contiene el agregado, ID, eventos y puerto del repositorio sin Spring/JPA.
Application contiene comandos, respuesta y cinco casos de uso CRUD.
Infrastructure configura los beans e implementa REST, validaciones y persistencia.
El mapper restaura el agregado sin generar eventos nuevos; los eventos de creación
y actualización permanecen en memoria y el caso de eliminación devuelve su evento.

Las pruebas cubren CRUD, códigos duplicados, nombres repetidos, auditoría,
campos obligatorios, límites de version, actualizaciones inválidas sin cambios
parciales y conversión JPA. Desde la raíz:

```powershell
.\mvnw.cmd -o -Dtest=*Test,!RangelApplicationTests -Dsurefire.failIfNoSpecifiedTests=false test
```

La validación HTTP y en PostgreSQL sigue pendiente de configurar la conexión.
