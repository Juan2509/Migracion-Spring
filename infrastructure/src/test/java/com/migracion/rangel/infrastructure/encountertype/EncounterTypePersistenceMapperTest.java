package com.migracion.rangel.infrastructure.encountertype;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.encountertype.model.aggregate.EncounterType;
import com.migracion.rangel.infrastructure.encountertype.adapters.out.persistence.mappers.EncounterTypePersistenceMapper;

class EncounterTypePersistenceMapperTest {
    @Test
    void roundTripPreservesEveryFieldWithoutRegisteringEvents() {
        var aggregate = EncounterType.register("INITIAL", "Inicial", true);
        var mapper = new EncounterTypePersistenceMapper();
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

