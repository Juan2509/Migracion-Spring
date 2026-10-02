-- PostgreSQL: esquema según el diagrama MindConnect.

CREATE TABLE chat_conversation_ai_settings (
    id UUID PRIMARY KEY,
    conversation_id UUID NOT NULL,
    ai_enabled BOOLEAN NOT NULL,
    default_model_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_chat_conversation_ai_settings_conversation_id FOREIGN KEY (conversation_id) REFERENCES chat_conversations (id),
    CONSTRAINT fk_chat_conversation_ai_settings_default_model_id FOREIGN KEY (default_model_id) REFERENCES ai_models (id)
);
