package com.migracion.rangel.infrastructure.sendertype;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.sendertype.model.aggregate.SenderType;
import com.migracion.rangel.infrastructure.sendertype.adapters.out.persistence.mappers.SenderTypePersistenceMapper;

class SenderTypePersistenceMapperTest {
    @Test
    void roundTripPreservesEveryFieldWithoutRegisteringEvents() {
        var aggregate = SenderType.register("Psicólogo");
        var mapper = new SenderTypePersistenceMapper();
        var restored = mapper.toDomain(mapper.toJpa(aggregate));
        assertEquals(aggregate.id(), restored.id());
        assertEquals(aggregate.nameType(), restored.nameType());
        assertEquals(aggregate.createdAt(), restored.createdAt());
        assertEquals(aggregate.updatedAt(), restored.updatedAt());
        assertTrue(restored.domainEvents().isEmpty());
    }
}
