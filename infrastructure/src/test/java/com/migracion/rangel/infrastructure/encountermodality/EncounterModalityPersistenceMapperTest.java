package com.migracion.rangel.infrastructure.encountermodality;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.encountermodality.model.aggregate.EncounterModality;
import com.migracion.rangel.infrastructure.encountermodality.adapters.out.persistence.mappers.EncounterModalityPersistenceMapper;

class EncounterModalityPersistenceMapperTest {
    @Test
    void roundTripPreservesEveryFieldWithoutRegisteringEvents() {
        var aggregate = EncounterModality.register("INITIAL", "Inicial", true);
        var mapper = new EncounterModalityPersistenceMapper();
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

