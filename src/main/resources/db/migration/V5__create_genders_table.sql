CREATE TABLE genders (
    id UUID PRIMARY KEY,
    description VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT uq_genders_description UNIQUE (description)
);
