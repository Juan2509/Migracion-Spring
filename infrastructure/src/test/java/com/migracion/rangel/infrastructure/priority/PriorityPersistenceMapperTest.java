package com.migracion.rangel.infrastructure.priority;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.priority.model.aggregate.Priority;
import com.migracion.rangel.infrastructure.priority.adapters.out.persistence.mappers.PriorityPersistenceMapper;

class PriorityPersistenceMapperTest {
    @Test
    void roundTripPreservesEveryFieldWithoutRegisteringEvents() {
        var aggregate = Priority.register("Psicólogo");
        var mapper = new PriorityPersistenceMapper();
        var restored = mapper.toDomain(mapper.toJpa(aggregate));
        assertEquals(aggregate.id(), restored.id());
        assertEquals(aggregate.namePriority(), restored.namePriority());
        assertEquals(aggregate.createdAt(), restored.createdAt());
        assertEquals(aggregate.updatedAt(), restored.updatedAt());
        assertTrue(restored.domainEvents().isEmpty());
    }
}
