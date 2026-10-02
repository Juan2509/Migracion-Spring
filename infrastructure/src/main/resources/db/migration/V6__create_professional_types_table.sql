CREATE TABLE professional_types (
    id UUID PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT uq_professional_types_name UNIQUE (name)
);
