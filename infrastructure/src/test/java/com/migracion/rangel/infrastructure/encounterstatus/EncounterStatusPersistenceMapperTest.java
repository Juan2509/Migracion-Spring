package com.migracion.rangel.infrastructure.encounterstatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.encounterstatus.model.aggregate.EncounterStatus;
import com.migracion.rangel.infrastructure.encounterstatus.adapters.out.persistence.mappers.EncounterStatusPersistenceMapper;

class EncounterStatusPersistenceMapperTest {
    @Test
    void roundTripPreservesEveryFieldWithoutRegisteringEvents() {
        var aggregate = EncounterStatus.register("INITIAL", "Inicial", true);
        var mapper = new EncounterStatusPersistenceMapper();
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

