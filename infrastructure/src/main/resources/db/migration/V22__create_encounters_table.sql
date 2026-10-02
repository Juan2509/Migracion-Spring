CREATE TABLE encounters (
    id UUID PRIMARY KEY,
    clinical_record_id UUID NOT NULL,
    professional_id UUID NOT NULL,
    encounter_type_id UUID NOT NULL,
    started_at TIMESTAMPTZ NOT NULL,
    ended_at TIMESTAMPTZ NOT NULL,
    reason_for_visit TEXT NOT NULL,
    current_condition TEXT NOT NULL,
    modality_id UUID NOT NULL,
    status_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    created_by UUID NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    updated_by UUID NOT NULL,
    CONSTRAINT fk_encounters_clinical_record
        FOREIGN KEY (clinical_record_id) REFERENCES clinical_records (id),
    CONSTRAINT fk_encounters_professional
        FOREIGN KEY (professional_id) REFERENCES professionals (id),
    CONSTRAINT fk_encounters_type
        FOREIGN KEY (encounter_type_id) REFERENCES encounter_types (id),
    CONSTRAINT fk_encounters_modality
        FOREIGN KEY (modality_id) REFERENCES encounter_modalities (id),
    CONSTRAINT fk_encounters_status
        FOREIGN KEY (status_id) REFERENCES encounter_statuses (id),
    CONSTRAINT fk_encounters_creator
        FOREIGN KEY (created_by) REFERENCES professionals (id),
    CONSTRAINT fk_encounters_updater
        FOREIGN KEY (updated_by) REFERENCES professionals (id)
);
