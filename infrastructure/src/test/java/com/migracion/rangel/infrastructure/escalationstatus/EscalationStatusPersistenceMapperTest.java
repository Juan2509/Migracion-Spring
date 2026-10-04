package com.migracion.rangel.infrastructure.escalationstatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.escalationstatus.model.aggregate.EscalationStatus;
import com.migracion.rangel.infrastructure.escalationstatus.adapters.out.persistence.mappers.EscalationStatusPersistenceMapper;

class EscalationStatusPersistenceMapperTest {
    @Test
    void roundTripPreservesEveryFieldWithoutRegisteringEvents() {
        var aggregate = EscalationStatus.register("Psicólogo");
        var mapper = new EscalationStatusPersistenceMapper();
        var restored = mapper.toDomain(mapper.toJpa(aggregate));
        assertEquals(aggregate.id(), restored.id());
        assertEquals(aggregate.nameStatus(), restored.nameStatus());
        assertEquals(aggregate.createdAt(), restored.createdAt());
        assertEquals(aggregate.updatedAt(), restored.updatedAt());
        assertTrue(restored.domainEvents().isEmpty());
    }
}
