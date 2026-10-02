-- PostgreSQL: esquema según el diagrama MindConnect.

CREATE TABLE medication_routes (
    id UUID PRIMARY KEY,
    code VARCHAR(20) NOT NULL,
    name VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT uq_medication_routes_code UNIQUE (code)
);
