package com.migracion.rangel.domain.contact;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.contact.model.aggregate.Contact;
import com.migracion.rangel.domain.contact.event.ContactRegisteredEvent;
import com.migracion.rangel.domain.contact.event.ContactUpdatedEvent;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;

class ContactTest {
    @Test
    void updatePreservesCreatorAndCreatedAtAndSupportsOptionalUpdater() {
        var creator = ProfessionalId.generate();
        var original = Contact.register("Ana", "ana@example.com", "Notas", CityMunicipalityId.generate(), creator);
        assertNull(original.updatedBy());
        assertEquals(ZoneOffset.UTC, original.createdAt().getOffset());
        assertEquals(original.createdAt(), original.updatedAt());
        assertInstanceOf(ContactRegisteredEvent.class, original.domainEvents().getFirst());
        var created = OffsetDateTime.parse("2020-01-01T10:00:00-05:00");
        var contact = Contact.restore(original.id(), original.fullName(), original.email(), original.notes(),
                original.cityId(), created, creator, created, null);
        assertTrue(contact.domainEvents().isEmpty());
        var updater = ProfessionalId.generate();
        contact.update("María", "maria@example.com", "Nuevas", CityMunicipalityId.generate(), updater);
        assertEquals(original.id(), contact.id());
        assertEquals(creator, contact.createdBy());
        assertEquals(created, contact.createdAt());
        assertTrue(contact.updatedAt().isAfter(created));
        assertEquals(updater, contact.updatedBy());
        assertInstanceOf(ContactUpdatedEvent.class, contact.domainEvents().getFirst());
        contact.update("María", "maria@example.com", "Nuevas", contact.cityId(), null);
        assertNull(contact.updatedBy());
    }
    @Test
    void invalidUpdateLeavesAllDetailsAndAuditUnchanged() {
        var contact = Contact.register("Ana", "ana@example.com", "Notas", CityMunicipalityId.generate(), ProfessionalId.generate());
        var updated = contact.updatedAt();
        assertThrows(NullPointerException.class, () -> contact.update("Otra", "otro@example.com", "Nuevas", null, ProfessionalId.generate()));
        assertEquals("Ana", contact.fullName());
        assertEquals("ana@example.com", contact.email());
        assertEquals("Notas", contact.notes());
        assertEquals(updated, contact.updatedAt());
        assertNull(contact.updatedBy());
        assertEquals(1, contact.domainEvents().size());
        assertThrows(IllegalArgumentException.class, () -> contact.update("x".repeat(201), "a", "Notas", contact.cityId(), null));
        assertThrows(IllegalArgumentException.class, () -> contact.update("Ana", "x".repeat(151), "Notas", contact.cityId(), null));
        assertThrows(NullPointerException.class, () -> contact.update("Ana", "a", null, contact.cityId(), null));
    }
}
