-- PostgreSQL: esquema según el diagrama MindConnect.

CREATE TABLE chat_participants (
    id UUID PRIMARY KEY,
    conversation_id UUID NOT NULL,
    participant_type_id UUID NOT NULL,
    patient_id UUID,
    professional_id UUID,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_chat_participants_conversation_id FOREIGN KEY (conversation_id) REFERENCES chat_conversations (id),
    CONSTRAINT fk_chat_participants_participant_type_id FOREIGN KEY (participant_type_id) REFERENCES sender_types (id),
    CONSTRAINT fk_chat_participants_patient_id FOREIGN KEY (patient_id) REFERENCES patients (id),
    CONSTRAINT fk_chat_participants_professional_id FOREIGN KEY (professional_id) REFERENCES professionals (id)
);
