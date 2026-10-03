package com.migracion.rangel.domain.phonecontact;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.domain.phonecontact.model.aggregate.PhoneContact;
import com.migracion.rangel.domain.phonecontact.event.PhoneContactRegisteredEvent;
import com.migracion.rangel.domain.phonecontact.event.PhoneContactUpdatedEvent;

class PhoneContactTest {
    @Test
    void restoreAndUpdatePreserveIdentityAndRespectAuditAndNotes() {
        var original = PhoneContact.register(ContactId.generate(), "+57 3001234567", "Notas");
        assertInstanceOf(PhoneContactRegisteredEvent.class, original.domainEvents().getFirst());

        var restored = PhoneContact.restore(original.id(), original.contactId(), original.phone(), original.notes());
        assertTrue(restored.domainEvents().isEmpty());
        var newContact = ContactId.generate();
        restored.update(newContact, "+57 3007654321", null);
        assertEquals(original.id(), restored.id());
        assertEquals(newContact, restored.contactId());
        assertEquals("+57 3007654321", restored.phone());
        assertNull(restored.notes());

        assertInstanceOf(PhoneContactUpdatedEvent.class, restored.domainEvents().getFirst());
    }
    @Test
    void invalidUpdateDoesNotPartiallyChangeAggregate() {
        var original = PhoneContact.register(ContactId.generate(), "+57 3001234567", "Notas");
        assertThrows(IllegalArgumentException.class,
                () -> original.update(ContactId.generate(), "x".repeat(31), "Nuevas"));
        assertEquals("+57 3001234567", original.phone());
        assertEquals("Notas", original.notes());
        assertEquals(1, original.domainEvents().size());
        assertThrows(NullPointerException.class, () -> PhoneContact.register(null, "+57 3001234567", "Notas"));
        assertNull(PhoneContact.register(ContactId.generate(), "+57 3001234567", null).notes());
    }
}
