-- PostgreSQL: esquema según el diagrama MindConnect.

CREATE TABLE priorities (
    id UUID PRIMARY KEY,
    name_priority VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
