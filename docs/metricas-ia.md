# Métricas de IA: V48

ChatAiRunMetric implementa chat_ai_run_metrics en los paquetes chatairunmetric
de domain, application e infrastructure, desde src/main/java de cada módulo
en Java Projects. V48 conserva su contenido original.

| Columna SQL | Campo JSON | Regla |
| --- | --- | --- |
| id | id | UUID generado al registrar |
| ai_run_id | aiRunId | UUID obligatorio, referencia a chat_ai_runs |
| prompt_tokens | promptTokens | Integer obligatorio |
| completion_tokens | completionTokens | Integer obligatorio |
| total_tokens | totalTokens | Integer obligatorio |
| cost | cost | BigDecimal obligatorio, DECIMAL(10,6) |
| created_at | createdAt | TIMESTAMP / LocalDateTime, conservado al actualizar |

El dominio usa ChatAiRunId y JPA guarda UUID sin asociaciones entre agregados.
Registro y actualización consultan el puerto de ejecuciones antes de guardar;
la FK de PostgreSQL también protege la integridad.

No se añade updatedAt, unicidad ni valores iniciales. Se permiten varias métricas
por ejecución. TotalTokens se recibe y almacena, sin calcularlo desde los otros
conteos. No se añaden restricciones de tokens o costo positivos ausentes de V48.
El costo se valida sin double ni redondeo silencioso: debe ser representable
exactamente en DECIMAL(10,6), hasta cuatro dígitos enteros y seis decimales.

## API

Ruta base: /api/chat-ai-run-metrics. POST registra (201), GET lista (200),
GET /{id} consulta (200), PUT /{id} actualiza (200) y DELETE /{id} elimina (204).
Un ID o ejecución inexistente devuelve 404; los conflictos de integridad devuelven 409.

POST y PUT reciben:

```json
{
  "aiRunId": "00000000-0000-0000-0000-000000000001",
  "promptTokens": 120,
  "completionTokens": 80,
  "totalTokens": 200,
  "cost": 0.001234
}
```

Reemplazar aiRunId por una ejecución existente. La respuesta añade id y createdAt.
PUT sustituye los campos editables y conserva id y createdAt.

## Capas y pruebas

Domain contiene agregado, ID, eventos y puerto sin Spring/JPA. Application
contiene comandos, respuesta y cinco casos de uso. Infrastructure aporta REST,
validación, configuración y persistencia. Restaurar desde JPA no produce eventos;
creación y actualización los registran en memoria y eliminación devuelve su evento.

Las pruebas cubren CRUD, auditoría, métricas repetidas, campos obligatorios,
precisión decimal, referencia inexistente, actualización inválida sin cambios
parciales y conversión JPA. La ejecución HTTP y la validación en PostgreSQL siguen
pendientes de configurar la conexión.
