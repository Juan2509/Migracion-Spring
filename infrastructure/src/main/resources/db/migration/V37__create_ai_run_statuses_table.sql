-- PostgreSQL: esquema según el diagrama MindConnect.

CREATE TABLE ai_run_statuses (
    id UUID PRIMARY KEY,
    name_status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
