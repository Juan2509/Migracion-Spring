package com.migracion.rangel.domain.emailcontact.model.aggregate;

import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.domain.emailcontact.model.valueobject.EmailContactId;
import com.migracion.rangel.domain.emailcontact.event.EmailContactRegisteredEvent;
import com.migracion.rangel.domain.emailcontact.event.EmailContactUpdatedEvent;

public final class EmailContact extends AggregateRoot {
    private final EmailContactId id;
    private ContactId contactId;
    private String email;
    private String notes;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private EmailContact(EmailContactId id, ContactId contactId, String email, String notes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(contactId, email, notes);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt es obligatorio");
    }
    public static EmailContact register(ContactId contactId, String email, String notes) {
        var now = LocalDateTime.now();
        var aggregate = new EmailContact(EmailContactId.generate(), contactId, email, notes, now, now);
        aggregate.recordEvent(new EmailContactRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static EmailContact restore(EmailContactId id, ContactId contactId, String email, String notes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new EmailContact(id, contactId, email, notes, createdAt, updatedAt);
    }
    public void update(ContactId contactId, String email, String notes) {
        setDetails(contactId, email, notes);
        var now = LocalDateTime.now();
        updatedAt = now;
        recordEvent(new EmailContactUpdatedEvent(id, now));
    }
    private void setDetails(ContactId contactId, String email, String notes) {
        Objects.requireNonNull(contactId, "contactId es obligatorio");
        Objects.requireNonNull(email, "email es obligatorio");
        if (email.codePointCount(0, email.length()) > 150) {
            throw new IllegalArgumentException("email supera 150 caracteres");
        }
        Objects.requireNonNull(notes, "notes es obligatorio");
        this.contactId = contactId;
        this.email = email;
        this.notes = notes;
    }
    public EmailContactId id() { return id; }
    public ContactId contactId() { return contactId; }
    public String email() { return email; }
    public String notes() { return notes; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}
