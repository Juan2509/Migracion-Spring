CREATE TABLE patients (
    id UUID PRIMARY KEY,
    document_type_id UUID NOT NULL,
    document_number VARCHAR(30) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    middle_name VARCHAR(50),
    last_name VARCHAR(50) NOT NULL,
    second_last_name VARCHAR(50),
    birth_date DATE NOT NULL,
    biological_sex_id UUID NOT NULL,
    gender_identity UUID NOT NULL,
    email VARCHAR(150) NOT NULL,
    phone VARCHAR(30) NOT NULL,
    address VARCHAR(250) NOT NULL,
    active BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL,
    created_by UUID,
    updated_at TIMESTAMP NOT NULL,
    updated_by UUID,
    city_id UUID NOT NULL,
    CONSTRAINT uq_patients_email UNIQUE (email),
    CONSTRAINT fk_patients_document_type
        FOREIGN KEY (document_type_id) REFERENCES document_types (id),
    CONSTRAINT fk_patients_biological_sex
        FOREIGN KEY (biological_sex_id) REFERENCES genders (id),
    CONSTRAINT fk_patients_gender_identity
        FOREIGN KEY (gender_identity) REFERENCES genders (id),
    CONSTRAINT fk_patients_creator
        FOREIGN KEY (created_by) REFERENCES professionals (id),
    CONSTRAINT fk_patients_updater
        FOREIGN KEY (updated_by) REFERENCES professionals (id),
    CONSTRAINT fk_patients_city
        FOREIGN KEY (city_id) REFERENCES city_municipalities (id)
);
