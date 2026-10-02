-- PostgreSQL: esquema según el diagrama MindConnect.

CREATE TABLE diagnostic_systems (
    id UUID PRIMARY KEY,
    code VARCHAR(20) NOT NULL,
    name VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL,
    version VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT uq_diagnostic_systems_code UNIQUE (code)
);
