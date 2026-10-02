-- PostgreSQL: esquema según el diagrama MindConnect.

CREATE TABLE chat_ai_runs (
    id UUID PRIMARY KEY,
    conversation_id UUID NOT NULL,
    message_id UUID NOT NULL,
    model_id UUID NOT NULL,
    ai_run_status_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_chat_ai_runs_conversation_id FOREIGN KEY (conversation_id) REFERENCES chat_conversations (id),
    CONSTRAINT fk_chat_ai_runs_message_id FOREIGN KEY (message_id) REFERENCES chat_messages (id),
    CONSTRAINT fk_chat_ai_runs_model_id FOREIGN KEY (model_id) REFERENCES ai_models (id),
    CONSTRAINT fk_chat_ai_runs_ai_run_status_id FOREIGN KEY (ai_run_status_id) REFERENCES ai_run_statuses (id)
);
