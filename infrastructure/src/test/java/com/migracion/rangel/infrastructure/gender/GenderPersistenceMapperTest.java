package com.migracion.rangel.infrastructure.gender;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.gender.model.aggregate.Gender;
import com.migracion.rangel.infrastructure.gender.adapters.out.persistence.mappers.GenderPersistenceMapper;

class GenderPersistenceMapperTest {
    @Test
    void roundTripPreservesEveryFieldWithoutRegisteringEvents() {
        var aggregate = Gender.register("Femenino");
        var mapper = new GenderPersistenceMapper();
        var restored = mapper.toDomain(mapper.toJpa(aggregate));
        assertEquals(aggregate.id(), restored.id());
        assertEquals(aggregate.description(), restored.description());
        assertEquals(aggregate.createdAt(), restored.createdAt());
        assertEquals(aggregate.updatedAt(), restored.updatedAt());
        assertTrue(restored.domainEvents().isEmpty());
    }
}
