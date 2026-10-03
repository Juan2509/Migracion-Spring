package com.migracion.rangel.domain.citymunicipality;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.stateregion.model.valueobject.StateRegionId;
import com.migracion.rangel.domain.citymunicipality.model.aggregate.CityMunicipality;
import com.migracion.rangel.domain.citymunicipality.event.CityMunicipalityRegisteredEvent;
import com.migracion.rangel.domain.citymunicipality.event.CityMunicipalityUpdatedEvent;

class CityMunicipalityTest {
    @Test
    void restoreAndUpdatePreserveIdentityAndZonedCreationDate() {
        var region = StateRegionId.generate();
        var original = CityMunicipality.register("Medellín", "MED", "Ciudad", true, region);
        assertInstanceOf(CityMunicipalityRegisteredEvent.class, original.domainEvents().getFirst());
        assertEquals(ZoneOffset.UTC, original.createdAt().getOffset());
        var created = OffsetDateTime.parse("2020-01-01T00:00:00-05:00");
        var updated = LocalDateTime.of(2020, 1, 1, 0, 0);
        var city = CityMunicipality.restore(original.id(), "Medellín", "MED", "Ciudad", true, region, created, updated);
        assertTrue(city.domainEvents().isEmpty());
        var nextRegion = StateRegionId.generate();
        city.update("Bogotá", "BOG", "Nueva", false, nextRegion);
        assertEquals(original.id(), city.id());
        assertEquals(created, city.createdAt());
        assertTrue(city.updatedAt().isAfter(updated));
        assertEquals(nextRegion, city.regionId());
        assertFalse(city.isActive());
        assertInstanceOf(CityMunicipalityUpdatedEvent.class, city.domainEvents().getFirst());
    }

    @Test
    void invalidUpdateDoesNotPartiallyChangeCity() {
        var region = StateRegionId.generate();
        var city = CityMunicipality.register("Medellín", "MED", "Ciudad", true, region);
        var updated = city.updatedAt();
        assertThrows(IllegalArgumentException.class,
                () -> city.update("Otra", "x".repeat(11), "Nueva", false, StateRegionId.generate()));
        assertEquals("Medellín", city.nameCity());
        assertEquals(region, city.regionId());
        assertEquals(updated, city.updatedAt());
        assertEquals(1, city.domainEvents().size());
        assertThrows(NullPointerException.class,
                () -> CityMunicipality.register("Medellín", "MED", "Ciudad", true, null));
    }
}
