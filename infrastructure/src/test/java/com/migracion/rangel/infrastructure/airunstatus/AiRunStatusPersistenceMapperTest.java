package com.migracion.rangel.infrastructure.airunstatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.airunstatus.model.aggregate.AiRunStatus;
import com.migracion.rangel.infrastructure.airunstatus.adapters.out.persistence.mappers.AiRunStatusPersistenceMapper;

class AiRunStatusPersistenceMapperTest {
    @Test
    void roundTripPreservesEveryFieldWithoutRegisteringEvents() {
        var aggregate = AiRunStatus.register("Psicólogo");
        var mapper = new AiRunStatusPersistenceMapper();
        var restored = mapper.toDomain(mapper.toJpa(aggregate));
        assertEquals(aggregate.id(), restored.id());
        assertEquals(aggregate.nameStatus(), restored.nameStatus());
        assertEquals(aggregate.createdAt(), restored.createdAt());
        assertEquals(aggregate.updatedAt(), restored.updatedAt());
        assertTrue(restored.domainEvents().isEmpty());
    }
}
