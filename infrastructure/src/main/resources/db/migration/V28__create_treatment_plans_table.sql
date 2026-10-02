CREATE TABLE treatment_plans (
    id UUID PRIMARY KEY,
    encounter_id UUID NOT NULL,
    professional_id UUID NOT NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    treatment_status_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT fk_treatment_plans_encounter
        FOREIGN KEY (encounter_id) REFERENCES encounters (id),
    CONSTRAINT fk_treatment_plans_professional
        FOREIGN KEY (professional_id) REFERENCES professionals (id),
    CONSTRAINT fk_treatment_plans_status
        FOREIGN KEY (treatment_status_id) REFERENCES treatment_statuses (id)
);
