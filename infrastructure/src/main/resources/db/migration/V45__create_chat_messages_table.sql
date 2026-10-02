-- PostgreSQL: esquema según el diagrama MindConnect.

CREATE TABLE chat_messages (
    id UUID PRIMARY KEY,
    conversation_id UUID NOT NULL,
    message_type_id UUID NOT NULL,
    participant_id UUID NOT NULL,
    content JSONB NOT NULL,
    metadata JSONB NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_chat_messages_conversation_id FOREIGN KEY (conversation_id) REFERENCES chat_conversations (id),
    CONSTRAINT fk_chat_messages_message_type_id FOREIGN KEY (message_type_id) REFERENCES message_types (id),
    CONSTRAINT fk_chat_messages_participant_id FOREIGN KEY (participant_id) REFERENCES chat_participants (id)
);
