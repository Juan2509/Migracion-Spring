package com.migracion.rangel.infrastructure.treatmentgoal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import com.migracion.rangel.domain.treatmentgoal.model.aggregate.TreatmentGoal;
import com.migracion.rangel.domain.treatmentplan.model.valueobject.TreatmentPlanId;
import com.migracion.rangel.domain.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
import com.migracion.rangel.application.treatmentgoal.dto.TreatmentGoalResponse;
import com.migracion.rangel.infrastructure.treatmentgoal.adapters.out.persistence.mappers.TreatmentGoalPersistenceMapper;
class TreatmentGoalPersistenceMapperTest {
    @Test void allFieldsAndMixedTemporalTypesRoundTripWithoutEvents() {
        var original = TreatmentGoal.register(TreatmentPlanId.generate(), "Descripción", LocalDate.of(2026, 10, 3), OffsetDateTime.parse("2026-10-03T10:30:00-05:00"), "Notas", TreatmentGoalStatusId.generate());
        var mapper = new TreatmentGoalPersistenceMapper(); var restored = mapper.toDomain(mapper.toJpa(original));
        assertEquals(TreatmentGoalResponse.from(original), TreatmentGoalResponse.from(restored)); assertTrue(restored.domainEvents().isEmpty());
    }
}
