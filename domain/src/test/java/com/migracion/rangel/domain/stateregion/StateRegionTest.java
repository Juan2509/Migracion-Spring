package com.migracion.rangel.domain.stateregion;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;
import com.migracion.rangel.domain.stateregion.model.aggregate.StateRegion;
import com.migracion.rangel.domain.stateregion.event.StateRegionRegisteredEvent;
import com.migracion.rangel.domain.stateregion.event.StateRegionUpdatedEvent;

class StateRegionTest {
    @Test
    void restoreAndUpdatePreserveIdentityAndCreationDate() {
        var country = CountryId.generate();
        var original = StateRegion.register("Antioquia", "ANT", "Región", true, country);
        assertInstanceOf(StateRegionRegisteredEvent.class, original.domainEvents().getFirst());
        assertEquals(original.createdAt(), original.updatedAt());
        var created = LocalDateTime.of(2020, 1, 1, 0, 0);
        var region = StateRegion.restore(original.id(), "Antioquia", "ANT", "Región", true, country, created, created);
        assertTrue(region.domainEvents().isEmpty());
        var newCountry = CountryId.generate();
        region.update("Cundinamarca", "CUN", "Nueva", false, newCountry);
        assertEquals(original.id(), region.id());
        assertEquals(created, region.createdAt());
        assertTrue(region.updatedAt().isAfter(created));
        assertEquals(newCountry, region.countryId());
        assertFalse(region.isActive());
        assertInstanceOf(StateRegionUpdatedEvent.class, region.domainEvents().getFirst());
    }

    @Test
    void invalidUpdateLeavesStateUnchanged() {
        var country = CountryId.generate();
        var region = StateRegion.register("Antioquia", "ANT", "Región", true, country);
        var updated = region.updatedAt();
        assertThrows(IllegalArgumentException.class,
                () -> region.update("Otra", "x".repeat(11), "Nueva", false, CountryId.generate()));
        assertEquals("Antioquia", region.nameRegion());
        assertEquals(country, region.countryId());
        assertEquals(updated, region.updatedAt());
        assertEquals(1, region.domainEvents().size());
        assertThrows(NullPointerException.class,
                () -> StateRegion.register("Antioquia", "ANT", "Región", true, null));
    }
}
