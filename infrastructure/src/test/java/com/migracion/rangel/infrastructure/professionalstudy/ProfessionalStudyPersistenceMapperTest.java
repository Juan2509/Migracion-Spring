package com.migracion.rangel.infrastructure.professionalstudy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.professionalstudy.model.aggregate.ProfessionalStudy;
import com.migracion.rangel.domain.study.model.valueobject.StudyId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;
import com.migracion.rangel.infrastructure.professionalstudy.adapters.out.persistence.mappers.ProfessionalStudyPersistenceMapper;
class ProfessionalStudyPersistenceMapperTest {
    @Test void roundTripPreservesReferencesAuditAndNullableResolutionWithoutEvents() {
        var mapper = new ProfessionalStudyPersistenceMapper();
        for (String resolution : new String[]{null, "RES-1"}) {
            var original = ProfessionalStudy.register(StudyId.generate(), ProfessionalId.generate(), "Título", "Universidad", false, resolution, CountryId.generate());
            var restored = mapper.toDomain(mapper.toJpa(original));
            assertEquals(original.id(), restored.id()); assertEquals(original.studyId(), restored.studyId());
            assertEquals(original.professionalId(), restored.professionalId()); assertEquals(original.countryId(), restored.countryId());
            assertEquals(original.title(), restored.title()); assertEquals(original.university(), restored.university());
            assertEquals(original.isValid(), restored.isValid()); assertEquals(resolution, restored.resolutionNumber());
            assertEquals(original.createdAt(), restored.createdAt()); assertEquals(original.updatedAt(), restored.updatedAt());
            assertTrue(restored.domainEvents().isEmpty());
        }
    }
}
