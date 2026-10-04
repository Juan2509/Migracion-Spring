# Catálogos clínicos: V31–V33

Los paquetes medicationroute, assessmenttype y consenttype aparecen en
src/main/java de domain, application e infrastructure desde Java Projects.
Cada contexto tiene agregado, ID, eventos, puerto, cinco casos de uso CRUD,
controlador REST, configuración y adaptador de persistencia JPA.

| Contexto | Tabla | Ruta | Restricción UNIQUE |
| --- | --- | --- | --- |
| MedicationRoute | medication_routes | /api/medication-routes | code |
| AssessmentType | assessment_types | /api/assessment-types | code |
| ConsentType | consent_types | /api/consent-types | Ninguna |

Todos contienen id UUID, code VARCHAR(20), name VARCHAR(50), active BOOLEAN,
created_at y updated_at TIMESTAMP. Todos los campos son obligatorios.
AssessmentType y ConsentType añaden description TEXT obligatorio, sin límite
artificial de longitud. Los nombres pueden repetirse en los tres catálogos.
No hay relaciones a otros agregados en estas migraciones.

## Operaciones

POST en la ruta base crea (201); GET lista (200); GET /{id} consulta (200);
PUT /{id} actualiza (200); DELETE /{id} elimina (204). Un ID inexistente devuelve
404. Los códigos duplicados de MedicationRoute y AssessmentType devuelven 409,
excluyendo el propio ID al actualizar. Las restricciones SQL protegen también
frente a escrituras concurrentes. ConsentType permite repetir el código.

Ejemplo para POST o PUT /api/assessment-types:

```json
{
  "code": "INITIAL",
  "name": "Evaluación inicial",
  "active": true,
  "description": "Evaluación al iniciar la atención"
}
```

ConsentType recibe los mismos campos; MedicationRoute recibe code, name y active.
El dominio genera el ID y las fechas al registrar; conserva id y createdAt
al actualizar. TIMESTAMP se representa como LocalDateTime. La respuesta incluye
todos los campos, con createdAt y updatedAt.

Registrar y actualizar producen eventos en memoria; la restauración desde JPA
no produce eventos. El caso de uso de eliminación devuelve su evento.
No se cargan datos iniciales ni se modifican los scripts V31–V33.

## Verificación

Las pruebas cubren CRUD, identidad, auditoría, actualización inválida sin cambios
parciales, unicidad y códigos repetidos en ConsentType, además de conversión JPA
sin eventos nuevos. Desde la raíz:

```powershell
.\mvnw.cmd -o -Dtest=*Test,!RangelApplicationTests -Dsurefire.failIfNoSpecifiedTests=false test
```

Estas pruebas no requieren una base. La ejecución HTTP y la validación del esquema
en PostgreSQL siguen pendientes de configurar DB_URL, DB_USERNAME y DB_PASSWORD.
