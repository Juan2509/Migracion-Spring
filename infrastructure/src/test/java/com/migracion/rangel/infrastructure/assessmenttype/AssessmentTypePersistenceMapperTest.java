package com.migracion.rangel.infrastructure.assessmenttype;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.assessmenttype.model.aggregate.AssessmentType;
import com.migracion.rangel.infrastructure.assessmenttype.adapters.out.persistence.mappers.AssessmentTypePersistenceMapper;

class AssessmentTypePersistenceMapperTest {
    @Test
    void roundTripPreservesEveryFieldWithoutRegisteringEvents() {
        var aggregate = AssessmentType.register("CC", "Cédula", true, "Descripción");
        var mapper = new AssessmentTypePersistenceMapper();
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
