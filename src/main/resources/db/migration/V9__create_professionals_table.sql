CREATE TABLE professionals (
    id UUID PRIMARY KEY,
    document_type_id UUID NOT NULL,
    document_number VARCHAR(30) NOT NULL,
    first_name VARCHAR(60) NOT NULL,
    last_name VARCHAR(60) NOT NULL,
    professional_type UUID NOT NULL,
    license_number VARCHAR(100) NOT NULL,
    active BOOLEAN NOT NULL,
    city_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT uq_professionals_document_number UNIQUE (document_number),
    CONSTRAINT uq_professionals_first_name UNIQUE (first_name),
    CONSTRAINT uq_professionals_last_name UNIQUE (last_name),
    CONSTRAINT uq_professionals_license_number UNIQUE (license_number),
    CONSTRAINT fk_professionals_document_type
        FOREIGN KEY (document_type_id) REFERENCES document_types (id),
    CONSTRAINT fk_professionals_type
        FOREIGN KEY (professional_type) REFERENCES professional_types (id),
    CONSTRAINT fk_professionals_city
        FOREIGN KEY (city_id) REFERENCES city_municipalities (id)
);
