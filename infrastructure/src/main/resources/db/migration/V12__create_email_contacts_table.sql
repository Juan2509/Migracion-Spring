CREATE TABLE email_contacts (
    id UUID PRIMARY KEY,
    contact_id UUID NOT NULL,
    email VARCHAR(150) NOT NULL,
    notes TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT uq_email_contacts_email UNIQUE (email),
    CONSTRAINT fk_email_contacts_contact
        FOREIGN KEY (contact_id) REFERENCES contacts (id)
);
