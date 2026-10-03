package com.migracion.rangel.infrastructure.professionaltype;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.professionaltype.model.aggregate.ProfessionalType;
import com.migracion.rangel.infrastructure.professionaltype.adapters.out.persistence.mappers.ProfessionalTypePersistenceMapper;

class ProfessionalTypePersistenceMapperTest {
    @Test
    void roundTripPreservesEveryFieldWithoutRegisteringEvents() {
        var aggregate = ProfessionalType.register("Psicólogo");
        var mapper = new ProfessionalTypePersistenceMapper();
        var restored = mapper.toDomain(mapper.toJpa(aggregate));
        assertEquals(aggregate.id(), restored.id());
        assertEquals(aggregate.name(), restored.name());
        assertEquals(aggregate.createdAt(), restored.createdAt());
        assertEquals(aggregate.updatedAt(), restored.updatedAt());
        assertTrue(restored.domainEvents().isEmpty());
    }
}
