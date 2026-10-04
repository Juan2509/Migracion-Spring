package com.migracion.rangel.infrastructure.conversationstatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.conversationstatus.model.aggregate.ConversationStatus;
import com.migracion.rangel.infrastructure.conversationstatus.adapters.out.persistence.mappers.ConversationStatusPersistenceMapper;

class ConversationStatusPersistenceMapperTest {
    @Test
    void roundTripPreservesEveryFieldWithoutRegisteringEvents() {
        var aggregate = ConversationStatus.register("Psicólogo");
        var mapper = new ConversationStatusPersistenceMapper();
        var restored = mapper.toDomain(mapper.toJpa(aggregate));
        assertEquals(aggregate.id(), restored.id());
        assertEquals(aggregate.nameStatus(), restored.nameStatus());
        assertEquals(aggregate.createdAt(), restored.createdAt());
        assertEquals(aggregate.updatedAt(), restored.updatedAt());
        assertTrue(restored.domainEvents().isEmpty());
    }
}
