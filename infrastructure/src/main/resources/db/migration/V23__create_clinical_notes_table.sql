CREATE TABLE clinical_notes (
    id UUID PRIMARY KEY,
    encounter_id UUID NOT NULL,
    professional_id UUID NOT NULL,
    subjective TEXT NOT NULL,
    objective TEXT NOT NULL,
    assessment TEXT NOT NULL,
    plan TEXT NOT NULL,
    additional_notes TEXT NOT NULL,
    signed_at TIMESTAMPTZ NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT fk_clinical_notes_encounter
        FOREIGN KEY (encounter_id) REFERENCES encounters (id),
    CONSTRAINT fk_clinical_notes_professional
        FOREIGN KEY (professional_id) REFERENCES professionals (id)
);
