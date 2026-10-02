CREATE TABLE clinical_record_statuses (
    id UUID PRIMARY KEY,
    code VARCHAR(20) NOT NULL,
    name VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT uq_clinical_record_statuses_code UNIQUE (code),
    CONSTRAINT uq_clinical_record_statuses_name UNIQUE (name)
);
