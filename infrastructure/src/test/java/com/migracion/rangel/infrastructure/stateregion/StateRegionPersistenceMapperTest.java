package com.migracion.rangel.infrastructure.stateregion;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;
import com.migracion.rangel.domain.stateregion.model.aggregate.StateRegion;
import com.migracion.rangel.infrastructure.stateregion.adapters.out.persistence.mappers.StateRegionPersistenceMapper;

class StateRegionPersistenceMapperTest {
    @Test
    void roundTripPreservesFieldsAndCountryReferenceWithoutNewEvents() {
        var region = StateRegion.register("Antioquia", "ANT", "Región", false, CountryId.generate());
        var mapper = new StateRegionPersistenceMapper();
        var entity = mapper.toJpa(region);
        assertEquals(region.countryId().value(), entity.getCountryId());
        var restored = mapper.toDomain(entity);
        assertEquals(region.id(), restored.id());
        assertEquals(region.nameRegion(), restored.nameRegion());
        assertEquals(region.codeRegion(), restored.codeRegion());
        assertEquals(region.description(), restored.description());
        assertEquals(region.isActive(), restored.isActive());
        assertEquals(region.countryId(), restored.countryId());
        assertEquals(region.createdAt(), restored.createdAt());
        assertEquals(region.updatedAt(), restored.updatedAt());
        assertTrue(restored.domainEvents().isEmpty());
    }
}
