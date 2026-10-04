# Proveedores de IA: V35

ProviderModelAi implementa provider_models_ai en los paquetes providermodelai
de domain, application e infrastructure. Se edita desde src/main/java de cada
módulo en Java Projects. V35 mantiene su contenido original.

| Columna SQL | Campo Java / JSON | Regla |
| --- | --- | --- |
| id | id | UUID generado al registrar |
| name_provider_ai | nameProviderAi | Obligatorio, máximo 100 caracteres |
| razon_social | razonSocial | VARCHAR obligatorio sin longitud declarada |
| sitio_web | sitioWeb | TEXT obligatorio |
| "isActive" | isActive | Boolean obligatorio; nombre SQL entre comillas |
| created_at | createdAt | LocalDateTime, conservado al actualizar |
| updated_at | updatedAt | LocalDateTime, actualizado por el dominio |

Todos los campos son obligatorios. No hay UNIQUE ni referencias en V35.
Se admiten proveedores con los mismos datos; no se añaden validaciones de URL,
límites a razonSocial/sitioWeb ni datos iniciales.

## API

Ruta base: /api/provider-models-ai.
POST registra (201), GET lista (200), GET /{id} consulta (200), PUT /{id}
actualiza (200) y DELETE /{id} elimina (204). Un ID inexistente devuelve 404;
los conflictos de integridad de PostgreSQL se traducen a 409.

POST y PUT reciben:

```json
{
  "nameProviderAi": "Proveedor de ejemplo",
  "razonSocial": "Empresa de ejemplo",
  "isActive": true,
  "sitioWeb": "https://example.com"
}
```

La respuesta añade id, createdAt y updatedAt. Los casos de uso conservan ID
y createdAt al actualizar. Las fechas TIMESTAMP se representan con LocalDateTime.
El adaptador JPA cita explícitamente isActive para conservar su grafía en SQL.

## Capas y verificación

Domain contiene agregado, ID, eventos y puerto sin Spring/JPA; application
contiene comandos, respuesta y cinco casos de uso CRUD. Infrastructure aporta
REST, validaciones, configuración y persistencia. Restaurar desde JPA no emite
eventos; creación y actualización los registran en memoria. El caso de uso
de eliminación devuelve su evento.

Las pruebas cubren CRUD, valores repetidos, auditoría, campos obligatorios,
límite del nombre, textos largos, actualización inválida sin cambios parciales,
conversión JPA y conservación del nombre entre comillas.

```powershell
.\mvnw.cmd -o -Dtest=*Test,!RangelApplicationTests -Dsurefire.failIfNoSpecifiedTests=false test
```

La validación HTTP y en PostgreSQL sigue pendiente de conexión. La incompatibilidad
entre ai_models.provider_model_id (VARCHAR) y provider_models_ai.id (UUID)
continúa documentada; este contexto no modifica esa relación ni los SQL existentes.
