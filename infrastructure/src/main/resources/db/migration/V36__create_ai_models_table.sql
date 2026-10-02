-- PostgreSQL: esquema según el diagrama MindConnect.

CREATE TABLE ai_models (
    id UUID PRIMARY KEY,
    provider_model_id VARCHAR(50) NOT NULL,
    name_model VARCHAR(100) NOT NULL,
    model_key VARCHAR(120) NOT NULL,
    input_token_price DECIMAL(12,8) NOT NULL,
    output_token_price DECIMAL(12,8) NOT NULL,
    max_tokens INTEGER NOT NULL,
    context_window INTEGER NOT NULL,
    is_active BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- El diagrama declara provider_model_id VARCHAR(50), pero el proveedor tiene id UUID.
-- Se conserva el tipo; la FK queda pendiente de resolver esta incompatibilidad.
