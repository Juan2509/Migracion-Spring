package com.migracion.rangel.infrastructure.messagetype;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.messagetype.model.aggregate.MessageType;
import com.migracion.rangel.infrastructure.messagetype.adapters.out.persistence.mappers.MessageTypePersistenceMapper;

class MessageTypePersistenceMapperTest {
    @Test
    void roundTripPreservesEveryFieldWithoutRegisteringEvents() {
        var aggregate = MessageType.register("Psicólogo");
        var mapper = new MessageTypePersistenceMapper();
        var restored = mapper.toDomain(mapper.toJpa(aggregate));
        assertEquals(aggregate.id(), restored.id());
        assertEquals(aggregate.nameType(), restored.nameType());
        assertEquals(aggregate.createdAt(), restored.createdAt());
        assertEquals(aggregate.updatedAt(), restored.updatedAt());
        assertTrue(restored.domainEvents().isEmpty());
    }
}
