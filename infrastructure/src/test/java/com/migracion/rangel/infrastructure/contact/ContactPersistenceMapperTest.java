package com.migracion.rangel.infrastructure.contact;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.contact.model.aggregate.Contact;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.infrastructure.contact.adapters.out.persistence.mappers.ContactPersistenceMapper;
import com.migracion.rangel.application.contact.dto.ContactResponse;

class ContactPersistenceMapperTest {
    @Test
    void roundTripPreservesEveryColumnWithUpdater() {
        var created = OffsetDateTime.parse("2020-01-01T10:00:00-05:00");
        var updated = OffsetDateTime.parse("2021-02-03T11:00:00+02:00");
        var contact = Contact.restore(ContactId.generate(), "Ana", "a@example.com", "Notas".repeat(200),
                CityMunicipalityId.generate(), created, ProfessionalId.generate(), updated, ProfessionalId.generate());
        var mapper = new ContactPersistenceMapper();
        var restored = mapper.toDomain(mapper.toJpa(contact));
        assertEquals(ContactResponse.from(contact), ContactResponse.from(restored));
        assertTrue(restored.domainEvents().isEmpty());
    }
    @Test
    void nullUpdaterRemainsNullInBothDirectionsAndResponse() {
        var contact = Contact.register("Ana", "a@example.com", "Notas", CityMunicipalityId.generate(), ProfessionalId.generate());
        var mapper = new ContactPersistenceMapper();
        var entity = mapper.toJpa(contact);
        assertNull(entity.getUpdatedBy());
        var restored = mapper.toDomain(entity);
        assertNull(restored.updatedBy());
        assertNull(ContactResponse.from(restored).updatedBy());
        assertEquals(ContactResponse.from(contact), ContactResponse.from(restored));
    }
}
