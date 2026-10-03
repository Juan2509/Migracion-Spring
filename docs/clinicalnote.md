# Notas clínicas (V23)

El contexto clinicalnote sigue la estructura de los tres módulos. En Java
Projects, sus archivos están en src/main/java bajo com/migracion/rangel:
domain contiene agregado, ID, eventos y puerto; application contiene comandos,
respuesta, excepciones y cinco casos de uso; infrastructure contiene solicitudes
REST, controlador, manejo de errores, beans, entidad JPA, mapper y adaptador.
La migración permanece en infrastructure/src/main/resources/db/migration.

## Campos y restricciones

| Campo JSON | Tipo | Restricción de V23 |
| --- | --- | --- |
| encounterId | UUID | Obligatorio; encounters.id |
| professionalId | UUID | Obligatorio; professionals.id |
| subjective | String | Obligatorio; TEXT |
| objective | String | Obligatorio; TEXT |
| assessment | String | Obligatorio; TEXT |
| plan | String | Obligatorio; TEXT |
| additionalNotes | String | Obligatorio; TEXT |
| signedAt | Fecha y hora con offset | Obligatoria; TIMESTAMPTZ |

Id se genera al registrar. CreatedAt y updatedAt se generan en UTC y usan
OffsetDateTime, al igual que signedAt. JPA utiliza almacenamiento nativo de zona.
PostgreSQL conserva el instante, no necesariamente el offset original.
Al actualizar se conserva createdAt y se renueva updatedAt. SignedAt lo
proporciona la solicitud y es editable junto con los textos y las referencias.

La aplicación comprueba la existencia del encuentro y profesional antes de
registrar o actualizar. Se usan IDs tipados en el dominio y UUID en JPA,
sin @ManyToOne. Las FK protegen la integridad frente a cambios concurrentes.

Los cinco textos son obligatorios, incluido additionalNotes. No se añaden
límites VARCHAR, obligatoriedad de contenido no vacío ni reglas de edición
después de firmar. V23 no declara UNIQUE: se admiten notas repetidas para el
mismo encuentro y profesional. SignedAt registra una fecha; esta implementación
no realiza firma electrónica ni autentica al profesional.

## API REST

| Método | Ruta | Resultado |
| --- | --- | --- |
| POST | /api/clinical-notes | 201 y nota creada |
| GET | /api/clinical-notes | 200 y lista |
| GET | /api/clinical-notes/{id} | 200 y nota |
| PUT | /api/clinical-notes/{id} | 200 y nota actualizada |
| DELETE | /api/clinical-notes/{id} | 204 |

POST y PUT reciben los ocho campos obligatorios. PUT reemplaza todos los
campos editables. La respuesta añade id, createdAt y updatedAt. Una solicitud
inválida produce 400; una nota o referencia inexistente produce 404; una
violación de integridad de persistencia produce 409.

Crear antes un encuentro y profesional con sus referencias. Ejemplo de POST
para Postman, sustituyendo los UUID por IDs existentes:

```json
{
  "encounterId": "11111111-1111-1111-1111-111111111111",
  "professionalId": "22222222-2222-2222-2222-222222222222",
  "subjective": "Información subjetiva de ejemplo",
  "objective": "Información objetiva de ejemplo",
  "assessment": "Evaluación de ejemplo",
  "plan": "Plan de seguimiento de ejemplo",
  "additionalNotes": "Observaciones adicionales",
  "signedAt": "2026-10-03T10:30:00-05:00"
}
```

Para PUT, usar el ID de la nota en la ruta y enviar el cuerpo completo.
Configurar conexión y arranque conforme al README.

## Verificación

Las seis pruebas nuevas cubren CRUD, cambio de referencias y de todos los
campos clínicos, notas repetidas, firma obligatoria, obligatoriedad de los cinco
textos, textos extensos, rechazo sin mutación parcial ni guardado, auditoría,
eventos y conversión JPA de todas las columnas y fechas. Restaurar desde JPA
no genera eventos; estos permanecen en memoria.

```powershell
.\mvnw.cmd -o '-Dtest=*Test,!RangelApplicationTests' '-Dsurefire.failIfNoSpecifiedTests=false' test
```

Las pruebas unitarias usan puertos en memoria. La integración HTTP y PostgreSQL
sigue pendiente de conexión; la compilación no ejecuta Flyway. V23 no se modifica.
