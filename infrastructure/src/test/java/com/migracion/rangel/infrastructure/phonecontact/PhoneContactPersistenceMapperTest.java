package com.migracion.rangel.infrastructure.phonecontact;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.phonecontact.model.aggregate.PhoneContact;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.application.phonecontact.dto.PhoneContactResponse;
import com.migracion.rangel.infrastructure.phonecontact.adapters.out.persistence.mappers.PhoneContactPersistenceMapper;

class PhoneContactPersistenceMapperTest {
    @Test
    void roundTripPreservesAllFieldsAndDoesNotGenerateEvents() {
        var mapper = new PhoneContactPersistenceMapper();
        for (String notes : new String[] {null, "Notas".repeat(200)}) {
        var original = PhoneContact.register(ContactId.generate(), "+57 3001234567", notes);
        var restored = mapper.toDomain(mapper.toJpa(original));
        assertEquals(PhoneContactResponse.from(original), PhoneContactResponse.from(restored));
        assertTrue(restored.domainEvents().isEmpty());
        }
    }
}
