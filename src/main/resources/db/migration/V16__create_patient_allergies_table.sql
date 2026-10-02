CREATE TABLE patient_allergies (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    substance VARCHAR(200) NOT NULL,
    reaction TEXT,
    severity VARCHAR(20) NOT NULL,
    active BOOLEAN NOT NULL,
    recorded_at TIMESTAMPTZ NOT NULL,
    recorded_by UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_patient_allergies_patient
        FOREIGN KEY (patient_id) REFERENCES patients (id),
    CONSTRAINT fk_patient_allergies_recorder
        FOREIGN KEY (recorded_by) REFERENCES professionals (id)
);
