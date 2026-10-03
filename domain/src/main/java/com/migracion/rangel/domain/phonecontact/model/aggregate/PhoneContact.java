package com.migracion.rangel.domain.phonecontact.model.aggregate;

import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.domain.phonecontact.model.valueobject.PhoneContactId;
import com.migracion.rangel.domain.phonecontact.event.PhoneContactRegisteredEvent;
import com.migracion.rangel.domain.phonecontact.event.PhoneContactUpdatedEvent;

public final class PhoneContact extends AggregateRoot {
    private final PhoneContactId id;
    private ContactId contactId;
    private String phone;
    private String notes;

    private PhoneContact(PhoneContactId id, ContactId contactId, String phone, String notes) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(contactId, phone, notes);

    }
    public static PhoneContact register(ContactId contactId, String phone, String notes) {
        var now = LocalDateTime.now();
        var aggregate = new PhoneContact(PhoneContactId.generate(), contactId, phone, notes);
        aggregate.recordEvent(new PhoneContactRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static PhoneContact restore(PhoneContactId id, ContactId contactId, String phone, String notes) {
        return new PhoneContact(id, contactId, phone, notes);
    }
    public void update(ContactId contactId, String phone, String notes) {
        setDetails(contactId, phone, notes);
        var now = LocalDateTime.now();

        recordEvent(new PhoneContactUpdatedEvent(id, now));
    }
    private void setDetails(ContactId contactId, String phone, String notes) {
        Objects.requireNonNull(contactId, "contactId es obligatorio");
        Objects.requireNonNull(phone, "phone es obligatorio");
        if (phone.codePointCount(0, phone.length()) > 30) {
            throw new IllegalArgumentException("phone supera 30 caracteres");
        }

        this.contactId = contactId;
        this.phone = phone;
        this.notes = notes;
    }
    public PhoneContactId id() { return id; }
    public ContactId contactId() { return contactId; }
    public String phone() { return phone; }
    public String notes() { return notes; }

}
