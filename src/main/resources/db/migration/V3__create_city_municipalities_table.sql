CREATE TABLE city_municipalities (
    id UUID PRIMARY KEY,
    name_city VARCHAR(50) NOT NULL,
    code_citi VARCHAR(10) NOT NULL,
    description VARCHAR(100) NOT NULL,
    is_active BOOLEAN NOT NULL,
    region_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_city_municipalities_region
        FOREIGN KEY (region_id) REFERENCES state_regions (id)
);
