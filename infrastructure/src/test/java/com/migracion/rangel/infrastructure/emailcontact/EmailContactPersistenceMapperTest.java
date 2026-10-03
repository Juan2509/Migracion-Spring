package com.migracion.rangel.infrastructure.emailcontact;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.emailcontact.model.aggregate.EmailContact;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.application.emailcontact.dto.EmailContactResponse;
import com.migracion.rangel.infrastructure.emailcontact.adapters.out.persistence.mappers.EmailContactPersistenceMapper;

class EmailContactPersistenceMapperTest {
    @Test
    void roundTripPreservesAllFieldsAndDoesNotGenerateEvents() {
        var mapper = new EmailContactPersistenceMapper();

        var original = EmailContact.register(ContactId.generate(), "ana@example.com", "Notas".repeat(200));
        var restored = mapper.toDomain(mapper.toJpa(original));
        assertEquals(EmailContactResponse.from(original), EmailContactResponse.from(restored));
        assertTrue(restored.domainEvents().isEmpty());

    }
}
