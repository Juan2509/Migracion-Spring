package com.migracion.rangel.infrastructure.treatmentplan;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import com.migracion.rangel.domain.treatmentplan.model.aggregate.TreatmentPlan;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.treatmentstatus.model.valueobject.TreatmentStatusId;
import com.migracion.rangel.application.treatmentplan.dto.TreatmentPlanResponse;
import com.migracion.rangel.infrastructure.treatmentplan.adapters.out.persistence.mappers.TreatmentPlanPersistenceMapper;
class TreatmentPlanPersistenceMapperTest {
    @Test void allFieldsAndDatesRoundTripWithoutEvents() {
        var original = TreatmentPlan.register(EncounterId.generate(), "Título", "Descripción", LocalDate.of(2026, 10, 3), LocalDate.of(2026, 10, 4), TreatmentStatusId.generate(), ProfessionalId.generate());
        var mapper = new TreatmentPlanPersistenceMapper(); var restored = mapper.toDomain(mapper.toJpa(original));
        assertEquals(TreatmentPlanResponse.from(original), TreatmentPlanResponse.from(restored)); assertTrue(restored.domainEvents().isEmpty());
    }
}
