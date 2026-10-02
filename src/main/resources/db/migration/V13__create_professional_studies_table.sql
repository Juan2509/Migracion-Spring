CREATE TABLE professional_studies (
    id UUID PRIMARY KEY,
    study_id UUID NOT NULL,
    professional_id UUID NOT NULL,
    title VARCHAR(100) NOT NULL,
    university VARCHAR(100) NOT NULL,
    is_valid BOOLEAN NOT NULL,
    resolution_number VARCHAR(60),
    country_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_professional_studies_study
        FOREIGN KEY (study_id) REFERENCES studies (id),
    CONSTRAINT fk_professional_studies_professional
        FOREIGN KEY (professional_id) REFERENCES professionals (id),
    CONSTRAINT fk_professional_studies_country
        FOREIGN KEY (country_id) REFERENCES countries (id)
);
