-- PostgreSQL: esquema según el diagrama MindConnect.

CREATE TABLE message_types (
    id UUID PRIMARY KEY,
    name_type VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
