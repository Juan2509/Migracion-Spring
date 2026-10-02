CREATE TABLE phone_contacts (
    id UUID PRIMARY KEY,
    contact_id UUID NOT NULL,
    phone VARCHAR(30) NOT NULL,
    notes TEXT,
    CONSTRAINT fk_phone_contacts_contact
        FOREIGN KEY (contact_id) REFERENCES contacts (id)
);
