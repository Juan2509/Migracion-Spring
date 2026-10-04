package com.migracion.rangel.infrastructure.consenttype;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.consenttype.model.aggregate.ConsentType;
import com.migracion.rangel.infrastructure.consenttype.adapters.out.persistence.mappers.ConsentTypePersistenceMapper;

class ConsentTypePersistenceMapperTest {
    @Test
    void roundTripPreservesEveryFieldWithoutRegisteringEvents() {
        var aggregate = ConsentType.register("CC", "Cédula", true, "Descripción");
        var mapper = new ConsentTypePersistenceMapper();
        var restored = mapper.toDomain(mapper.toJpa(aggregate));
        assertEquals(aggregate.id(), restored.id());
        assertEquals(aggregate.code(), restored.code());
        assertEquals(aggregate.name(), restored.name());
        assertEquals(aggregate.active(), restored.active());
        assertEquals(aggregate.description(), restored.description());
        assertEquals(aggregate.createdAt(), restored.createdAt());
        assertEquals(aggregate.updatedAt(), restored.updatedAt());
        assertTrue(restored.domainEvents().isEmpty());
    }
}
