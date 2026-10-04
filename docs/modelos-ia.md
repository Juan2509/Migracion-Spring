# Modelos de IA: V36

AiModel implementa ai_models en domain, application e infrastructure, dentro
de los paquetes aimodel accesibles desde Java Projects. V36 conserva su contenido.

| Campo JSON | Tipo / regla |
| --- | --- |
| providerModelId | String obligatorio, máximo 50 caracteres |
| nameModel | String obligatorio, máximo 100 caracteres |
| modelKey | String obligatorio, máximo 120 caracteres |
| inputTokenPrice, outputTokenPrice | BigDecimal obligatorio, DECIMAL(12,8) |
| maxTokens, contextWindow | Integer obligatorio |
| isActive | Boolean obligatorio |

ID se genera como UUID. CreatedAt y updatedAt usan LocalDateTime para TIMESTAMP;
createdAt se conserva al actualizar. La respuesta incluye todos estos campos.
Los nombres SQL mantienen snake_case, por ejemplo provider_model_id.

## Relación pendiente

provider_model_id sigue siendo VARCHAR(50), mientras provider_models_ai.id es
UUID. V36 no crea la FK. La implementación conserva String y no exige UUID ni
consulta al proveedor; resolver la relación requiere una decisión sobre el esquema
y una nueva migración cuando corresponda. No hay UNIQUE en V36.

## API y validación

Ruta base: /api/ai-models. POST crea (201), GET lista (200), GET /{id} consulta
(200), PUT /{id} actualiza (200), DELETE /{id} elimina (204). IDs inexistentes
devuelven 404; conflictos de integridad se traducen a 409.

POST y PUT reciben, por ejemplo:

```json
{
  "providerModelId": "referencia textual",
  "nameModel": "Modelo de ejemplo",
  "modelKey": "modelo-v1",
  "inputTokenPrice": 0.00000123,
  "outputTokenPrice": 0.00000456,
  "maxTokens": 4096,
  "contextWindow": 8192,
  "isActive": true
}
```

Los precios se validan sin usar double ni redondear silenciosamente. El dominio
acepta valores representables exactamente en DECIMAL(12,8), hasta cuatro dígitos
enteros y ocho decimales. No se añaden reglas de precios o tokens positivos ni
relaciones entre maxTokens y contextWindow ausentes del SQL.

Domain contiene agregado, ID, eventos y puerto sin Spring/JPA. Application tiene
cinco casos de uso; infrastructure aporta REST, configuración y persistencia JPA.
Restaurar desde JPA no produce eventos; creación y actualización los registran
en memoria y eliminación devuelve su evento.

Las pruebas cubren CRUD, valores repetidos, referencia textual, auditoría,
límites numéricos y de texto, actualización inválida sin cambios parciales y
conversión JPA conservando BigDecimal. La ejecución HTTP/PostgreSQL sigue pendiente
de configurar la conexión.
