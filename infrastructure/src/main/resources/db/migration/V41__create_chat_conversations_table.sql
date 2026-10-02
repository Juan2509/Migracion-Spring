-- PostgreSQL: esquema según el diagrama MindConnect.

CREATE TABLE chat_conversations (
    id UUID PRIMARY KEY,
    conversation_status_id UUID NOT NULL,
    priority_id UUID NOT NULL,
    last_message_at TIMESTAMP,
    closed BOOLEAN,
    closed_at TIMESTAMP,
    closed_by UUID,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_chat_conversations_conversation_status_id FOREIGN KEY (conversation_status_id) REFERENCES conversations_statuses (id),
    CONSTRAINT fk_chat_conversations_priority_id FOREIGN KEY (priority_id) REFERENCES priorities (id)
);
