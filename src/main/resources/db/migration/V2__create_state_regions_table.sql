CREATE TABLE state_regions (
    id UUID PRIMARY KEY,
    name_region VARCHAR(50) NOT NULL,
    code_region VARCHAR(10) NOT NULL,
    description VARCHAR(100) NOT NULL,
    is_active BOOLEAN NOT NULL,
    country_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_state_regions_country
        FOREIGN KEY (country_id) REFERENCES countries (id)
);
