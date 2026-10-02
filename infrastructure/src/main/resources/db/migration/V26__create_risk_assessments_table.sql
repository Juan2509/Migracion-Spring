CREATE TABLE risk_assessments (
    id UUID PRIMARY KEY,
    encounter_id UUID NOT NULL,
    risk_level_id UUID NOT NULL,
    suicidal_ideation BOOLEAN NOT NULL,
    suicide_plan BOOLEAN NOT NULL,
    suicide_intent BOOLEAN NOT NULL,
    self_harm BOOLEAN NOT NULL,
    harm_to_others BOOLEAN NOT NULL,
    risk_factors TEXT NOT NULL,
    protective_factors TEXT NOT NULL,
    clinical_actions TEXT NOT NULL,
    observations TEXT NOT NULL,
    assessed_at TIMESTAMPTZ NOT NULL,
    assessed_by UUID NOT NULL,
    CONSTRAINT fk_risk_assessments_encounter
        FOREIGN KEY (encounter_id) REFERENCES encounters (id),
    CONSTRAINT fk_risk_assessments_level
        FOREIGN KEY (risk_level_id) REFERENCES risk_levels (id),
    CONSTRAINT fk_risk_assessments_assessor
        FOREIGN KEY (assessed_by) REFERENCES professionals (id)
);
