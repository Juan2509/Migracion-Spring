package com.migracion.rangel.infrastructure.providermodelai;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.providermodelai.model.aggregate.ProviderModelAi;
import com.migracion.rangel.infrastructure.providermodelai.adapters.out.persistence.mappers.ProviderModelAiPersistenceMapper;

class ProviderModelAiPersistenceMapperTest {
    @Test
    void physicalNamingPreservesQuotedIsActiveColumn() throws NoSuchFieldException {
        var column = com.migracion.rangel.infrastructure.providermodelai.adapters.out.persistence.entity.ProviderModelAiJpaEntity.class
                .getDeclaredField("isActive").getAnnotation(jakarta.persistence.Column.class);
        var logical = org.hibernate.boot.model.naming.Identifier.toIdentifier(column.name());
        var physical = new org.hibernate.boot.model.naming.PhysicalNamingStrategySnakeCaseImpl()
                .toPhysicalColumnName(logical, null);
        assertTrue(physical.isQuoted());
        assertEquals("isActive", physical.getText());
    }

    @Test
    void roundTripPreservesEveryFieldWithoutRegisteringEvents() {
        var aggregate = ProviderModelAi.register("CC", "Cédula", true, "Descripción");
        var mapper = new ProviderModelAiPersistenceMapper();
        var restored = mapper.toDomain(mapper.toJpa(aggregate));
        assertEquals(aggregate.id(), restored.id());
        assertEquals(aggregate.nameProviderAi(), restored.nameProviderAi());
        assertEquals(aggregate.razonSocial(), restored.razonSocial());
        assertEquals(aggregate.isActive(), restored.isActive());
        assertEquals(aggregate.sitioWeb(), restored.sitioWeb());
        assertEquals(aggregate.createdAt(), restored.createdAt());
        assertEquals(aggregate.updatedAt(), restored.updatedAt());
        assertTrue(restored.domainEvents().isEmpty());
    }
}
