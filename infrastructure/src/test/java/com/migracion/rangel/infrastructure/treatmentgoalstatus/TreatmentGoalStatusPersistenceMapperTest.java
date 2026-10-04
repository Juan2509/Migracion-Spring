package com.migracion.rangel.infrastructure.treatmentgoalstatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.treatmentgoalstatus.model.aggregate.TreatmentGoalStatus;
import com.migracion.rangel.infrastructure.treatmentgoalstatus.adapters.out.persistence.mappers.TreatmentGoalStatusPersistenceMapper;

class TreatmentGoalStatusPersistenceMapperTest {
    @Test
    void roundTripPreservesEveryFieldWithoutRegisteringEvents() {
        var aggregate = TreatmentGoalStatus.register("INITIAL", "Inicial", true);
        var mapper = new TreatmentGoalStatusPersistenceMapper();
        var restored = mapper.toDomain(mapper.toJpa(aggregate));
        assertEquals(aggregate.id(), restored.id());
        assertEquals(aggregate.code(), restored.code());
        assertEquals(aggregate.name(), restored.name());
        assertEquals(aggregate.active(), restored.active());
        assertEquals(aggregate.createdAt(), restored.createdAt());
        assertEquals(aggregate.updatedAt(), restored.updatedAt());
        assertTrue(restored.domainEvents().isEmpty());
    }
}



