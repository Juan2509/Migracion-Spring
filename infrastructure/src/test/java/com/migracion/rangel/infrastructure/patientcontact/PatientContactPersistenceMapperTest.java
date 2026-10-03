package com.migracion.rangel.infrastructure.patientcontact;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.relationshiptype.model.valueobject.RelationshipTypeId;
import com.migracion.rangel.domain.patientcontact.model.aggregate.PatientContact;
import com.migracion.rangel.application.patientcontact.dto.PatientContactResponse;
import com.migracion.rangel.infrastructure.patientcontact.adapters.out.persistence.mappers.PatientContactPersistenceMapper;
class PatientContactPersistenceMapperTest {
    @Test void roundTripPreservesAllFieldsAndDoesNotEmitEvents() {
        var mapper = new PatientContactPersistenceMapper();

        var original = PatientContact.register(ContactId.generate(), PatientId.generate(), false, false, RelationshipTypeId.generate());
        var restored = mapper.toDomain(mapper.toJpa(original));
        assertEquals(PatientContactResponse.from(original), PatientContactResponse.from(restored));
        assertTrue(restored.domainEvents().isEmpty());

    }
}
