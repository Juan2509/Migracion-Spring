-- PostgreSQL: esquema según el diagrama MindConnect.

CREATE TABLE chat_escalation_status_history (
    id UUID PRIMARY KEY,
    escalation_id UUID NOT NULL,
    escalation_status_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    changed_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_chat_escalation_status_history_escalation_id FOREIGN KEY (escalation_id) REFERENCES chat_escalations (id),
    CONSTRAINT fk_chat_escalation_status_history_escalation_status_id FOREIGN KEY (escalation_status_id) REFERENCES escalations_statuses (id)
);
