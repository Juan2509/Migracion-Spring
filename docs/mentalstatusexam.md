# Exámenes del estado mental (V24)

El contexto mentalstatusexam mantiene las tres capas. En Java Projects, sus
archivos están en src/main/java bajo com/migracion/rangel de cada módulo:
domain incluye agregado, ID, eventos y puerto; application incluye comandos,
respuesta, excepciones y cinco casos de uso; infrastructure incluye solicitudes
REST, controlador, manejo de errores, beans, entidad JPA, mapper y adaptador.
El SQL permanece en infrastructure/src/main/resources/db/migration.

## Campos y restricciones

| Campo JSON | Columna | Tipo y restricción |
| --- | --- | --- |
| encounterId | encounter_id | UUID obligatorio; encounters.id |
| appearance | appearance | String obligatorio; TEXT |
| behavior | behavior | String obligatorio; TEXT |
| attitude | attitude | String obligatorio; TEXT |
| consciousness | consciousness | String obligatorio; TEXT |
| orientation | orientation | String obligatorio; TEXT |
| attention | attention | String obligatorio; TEXT |
| memory | memory | String obligatorio; TEXT |
| speech | speech | String obligatorio; TEXT |
| mood | mood | String obligatorio; TEXT |
| affect | affect | String obligatorio; TEXT |
| thoughtProcess | thought_process | String obligatorio; TEXT |
| thoughtContent | thought_content | String obligatorio; TEXT |
| perception | perception | String obligatorio; TEXT |
| judgment | judgment | String obligatorio; TEXT |
| insight | insight | String obligatorio; TEXT |
| psychomotorActivity | psychomotor_activity | String obligatorio; TEXT |
| observations | observations | String obligatorio; TEXT |
| createdBy | created_by | UUID obligatorio al registrar; professionals.id |

Los 17 textos son obligatorios y no llevan límites VARCHAR ni restricciones de
contenido no vacío añadidas. V24 no declara UNIQUE: se pueden registrar varios
exámenes para el mismo encuentro. No se introducen escalas, diagnósticos ni
valores clínicos automáticos que no estén definidos en el esquema.

Id se genera al registrar. CreatedAt usa OffsetDateTime en UTC con almacenamiento
nativo de zona en JPA, porque V24 declara TIMESTAMPTZ. PostgreSQL conserva el
instante, no necesariamente el offset original. CreatedAt y createdBy se
conservan al actualizar y no son campos editables de PUT. La tabla no tiene
updatedAt ni updatedBy; actualizar produce un evento en memoria sin añadir
columnas. Restaurar desde JPA no produce nuevos eventos de registro.

Al registrar se comprueban encuentro y creador; al actualizar se comprueba
el encuentro. Las referencias utilizan IDs tipados en el dominio y UUID en
JPA, sin @ManyToOne. Las FK mantienen la integridad frente a cambios concurrentes.

## API REST

| Método | Ruta | Resultado |
| --- | --- | --- |
| POST | /api/mental-status-exams | 201 y examen creado |
| GET | /api/mental-status-exams | 200 y lista |
| GET | /api/mental-status-exams/{id} | 200 y examen |
| PUT | /api/mental-status-exams/{id} | 200 y examen actualizado |
| DELETE | /api/mental-status-exams/{id} | 204 |

PUT reemplaza todos los campos editables y exige el encuentro y los 17 textos.
La respuesta incluye todos los datos, id, createdBy y createdAt. Una solicitud
inválida produce 400; un examen o referencia inexistente produce 404; una
violación de integridad de persistencia produce 409.

Crear antes un encuentro y un profesional con sus referencias. Ejemplo de
POST para Postman, sustituyendo los UUID por IDs existentes. Los textos son
marcadores de prueba y no representan una evaluación clínica:

```json
{
  "encounterId": "11111111-1111-1111-1111-111111111111",
  "appearance": "Información de ejemplo",
  "behavior": "Información de ejemplo",
  "attitude": "Información de ejemplo",
  "consciousness": "Información de ejemplo",
  "orientation": "Información de ejemplo",
  "attention": "Información de ejemplo",
  "memory": "Información de ejemplo",
  "speech": "Información de ejemplo",
  "mood": "Información de ejemplo",
  "affect": "Información de ejemplo",
  "thoughtProcess": "Información de ejemplo",
  "thoughtContent": "Información de ejemplo",
  "perception": "Información de ejemplo",
  "judgment": "Información de ejemplo",
  "insight": "Información de ejemplo",
  "psychomotorActivity": "Información de ejemplo",
  "observations": "Información de ejemplo",
  "createdBy": "22222222-2222-2222-2222-222222222222"
}
```

Para PUT, usar el ID del examen en la ruta y quitar createdBy del cuerpo.
Configurar conexión y arranque conforme al README.

## Verificación

Las seis pruebas nuevas cubren CRUD, cambios de encuentro y de los 17 textos,
validación de cada texto obligatorio, textos extensos, exámenes repetidos,
referencias inexistentes sin mutación parcial ni guardado, auditoría de creación
inmutable, eventos y conversión JPA de todas las columnas. Los eventos permanecen
en memoria, sin publicación ni persistencia independiente.

```powershell
.\mvnw.cmd -o '-Dtest=*Test,!RangelApplicationTests' '-Dsurefire.failIfNoSpecifiedTests=false' test
```

Las pruebas unitarias usan puertos en memoria. La integración HTTP y PostgreSQL
sigue pendiente de conexión; compilar no ejecuta Flyway. V24 no se modifica.
