package com.migracion.rangel.infrastructure.riskassessment;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.OffsetDateTime;
import com.migracion.rangel.domain.riskassessment.model.aggregate.RiskAssessment;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.risklevel.model.valueobject.RiskLevelId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.application.riskassessment.dto.RiskAssessmentResponse;
import com.migracion.rangel.infrastructure.riskassessment.adapters.out.persistence.mappers.RiskAssessmentPersistenceMapper;
class RiskAssessmentPersistenceMapperTest {
    @Test void roundTripPreservesAllFieldsIncludingFalseFlagsAndOffsetWithoutEvents() {
        var original = RiskAssessment.register(EncounterId.generate(), RiskLevelId.generate(), false, true, false, true, false, "R", "P", "A", "O",
                OffsetDateTime.parse("2026-10-03T09:30:00-05:00"), ProfessionalId.generate());
        var mapper = new RiskAssessmentPersistenceMapper(); var restored = mapper.toDomain(mapper.toJpa(original));
        assertEquals(RiskAssessmentResponse.from(original), RiskAssessmentResponse.from(restored)); assertTrue(restored.domainEvents().isEmpty());
    }
}
