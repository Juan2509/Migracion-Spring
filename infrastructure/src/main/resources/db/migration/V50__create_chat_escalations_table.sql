-- PostgreSQL: esquema según el diagrama MindConnect.

CREATE TABLE chat_escalations (
    id UUID PRIMARY KEY,
    conversation_id UUID NOT NULL,
    status_id UUID NOT NULL,
    from_ai BOOLEAN NOT NULL,
    reason TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_chat_escalations_conversation_id FOREIGN KEY (conversation_id) REFERENCES chat_conversations (id),
    CONSTRAINT fk_chat_escalations_status_id FOREIGN KEY (status_id) REFERENCES escalations_statuses (id)
);
