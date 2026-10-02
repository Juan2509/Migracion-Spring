CREATE TABLE clinical_records (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    record_number VARCHAR(50) NOT NULL,
    opened_at TIMESTAMPTZ NOT NULL,
    closed_at TIMESTAMPTZ NOT NULL,
    status_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    created_by UUID NOT NULL,
    CONSTRAINT fk_clinical_records_patient
        FOREIGN KEY (patient_id) REFERENCES patients (id),
    CONSTRAINT fk_clinical_records_status
        FOREIGN KEY (status_id) REFERENCES clinical_record_statuses (id),
    CONSTRAINT fk_clinical_records_creator
        FOREIGN KEY (created_by) REFERENCES professionals (id)
);
