package com.migracion.rangel.domain.emailcontact;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.domain.emailcontact.model.aggregate.EmailContact;
import com.migracion.rangel.domain.emailcontact.event.EmailContactRegisteredEvent;
import com.migracion.rangel.domain.emailcontact.event.EmailContactUpdatedEvent;

class EmailContactTest {
    @Test
    void restoreAndUpdatePreserveIdentityAndRespectAuditAndNotes() {
        var original = EmailContact.register(ContactId.generate(), "ana@example.com", "Notas");
        assertInstanceOf(EmailContactRegisteredEvent.class, original.domainEvents().getFirst());
        var created = LocalDateTime.of(2020, 1, 1, 0, 0);
        var restored = EmailContact.restore(original.id(), original.contactId(), original.email(), original.notes(), created, created);
        assertTrue(restored.domainEvents().isEmpty());
        var newContact = ContactId.generate();
        restored.update(newContact, "otro@example.com", "Nuevas");
        assertEquals(original.id(), restored.id());
        assertEquals(newContact, restored.contactId());
        assertEquals("otro@example.com", restored.email());
        assertEquals("Nuevas", restored.notes());
        assertEquals(created, restored.createdAt());
        assertTrue(restored.updatedAt().isAfter(created));
        assertInstanceOf(EmailContactUpdatedEvent.class, restored.domainEvents().getFirst());
    }
    @Test
    void invalidUpdateDoesNotPartiallyChangeAggregate() {
        var original = EmailContact.register(ContactId.generate(), "ana@example.com", "Notas");
        assertThrows(IllegalArgumentException.class,
                () -> original.update(ContactId.generate(), "x".repeat(151), "Nuevas"));
        assertEquals("ana@example.com", original.email());
        assertEquals("Notas", original.notes());
        assertEquals(1, original.domainEvents().size());
        assertThrows(NullPointerException.class, () -> EmailContact.register(null, "ana@example.com", "Notas"));
        assertThrows(NullPointerException.class, () -> EmailContact.register(ContactId.generate(), "ana@example.com", null));
    }
}
