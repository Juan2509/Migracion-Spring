package com.migracion.rangel.infrastructure.citymunicipality;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.stateregion.model.valueobject.StateRegionId;
import com.migracion.rangel.domain.citymunicipality.model.aggregate.CityMunicipality;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.infrastructure.citymunicipality.adapters.out.persistence.mappers.CityMunicipalityPersistenceMapper;

class CityMunicipalityPersistenceMapperTest {
    @Test
    void roundTripPreservesRegionAndBothTemporalTypesWithoutEvents() {
        var created = OffsetDateTime.parse("2020-01-01T10:30:00-05:00");
        var updated = LocalDateTime.of(2021, 2, 3, 11, 45);
        var city = CityMunicipality.restore(CityMunicipalityId.generate(), "Medellín", "MED", "Ciudad",
                false, StateRegionId.generate(), created, updated);
        var mapper = new CityMunicipalityPersistenceMapper();
        var entity = mapper.toJpa(city);
        assertEquals(city.regionId().value(), entity.getRegionId());
        assertEquals(created, entity.getCreatedAt());
        var restored = mapper.toDomain(entity);
        assertEquals(city.id(), restored.id());
        assertEquals(city.nameCity(), restored.nameCity());
        assertEquals(city.codeCiti(), restored.codeCiti());
        assertEquals(city.description(), restored.description());
        assertEquals(city.isActive(), restored.isActive());
        assertEquals(city.regionId(), restored.regionId());
        assertEquals(created, restored.createdAt());
        assertEquals(updated, restored.updatedAt());
        assertTrue(restored.domainEvents().isEmpty());
    }
}
