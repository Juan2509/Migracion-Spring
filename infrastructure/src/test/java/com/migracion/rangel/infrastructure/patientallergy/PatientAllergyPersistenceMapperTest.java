package com.migracion.rangel.infrastructure.patientallergy;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.patientallergy.model.aggregate.PatientAllergy;
import com.migracion.rangel.application.patientallergy.dto.PatientAllergyResponse;
import com.migracion.rangel.infrastructure.patientallergy.adapters.out.persistence.mappers.PatientAllergyPersistenceMapper;
class PatientAllergyPersistenceMapperTest {
    @Test void roundTripPreservesAllFieldsAndDoesNotEmitEvents() {
        var mapper = new PatientAllergyPersistenceMapper();
        for (String reaction : new String[]{null, "Reacción"}) {
        var original = PatientAllergy.register(PatientId.generate(), "Penicilina", reaction, "Alta", false, OffsetDateTime.parse("2026-10-03T09:30:00-05:00"), ProfessionalId.generate());
        var restored = mapper.toDomain(mapper.toJpa(original));
        assertEquals(PatientAllergyResponse.from(original), PatientAllergyResponse.from(restored));
        assertTrue(restored.domainEvents().isEmpty());
        }
    }
}
