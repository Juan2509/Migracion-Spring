CREATE TABLE contacts (
    id UUID PRIMARY KEY,
    full_name VARCHAR(200) NOT NULL,
    email VARCHAR(150) NOT NULL,
    notes TEXT NOT NULL,
    city_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    created_by UUID NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    updated_by UUID,
    CONSTRAINT fk_contacts_city
        FOREIGN KEY (city_id) REFERENCES city_municipalities (id),
    CONSTRAINT fk_contacts_creator
        FOREIGN KEY (created_by) REFERENCES professionals (id),
    CONSTRAINT fk_contacts_updater
        FOREIGN KEY (updated_by) REFERENCES professionals (id)
);
