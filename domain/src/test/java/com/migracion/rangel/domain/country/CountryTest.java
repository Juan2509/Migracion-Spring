package com.migracion.rangel.domain.country;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.country.model.aggregate.Country;
import com.migracion.rangel.domain.country.event.CountryRegisteredEvent;
import com.migracion.rangel.domain.country.event.CountryUpdatedEvent;

class CountryTest {
    @Test
    void registerInitializesAuditAndEvent() {
        var country = Country.register("Colombia", "CO", "País", true, "+57");
        assertNotNull(country.id().value());
        assertEquals(country.createdAt(), country.updatedAt());
        assertEquals(1, country.domainEvents().size());
        assertInstanceOf(CountryRegisteredEvent.class, country.domainEvents().getFirst());
        assertThrows(UnsupportedOperationException.class, () -> country.domainEvents().clear());
    }

    @Test
    void restoreDoesNotRegisterAndUpdatePreservesIdentityAndCreationDate() {
        var original = Country.register("Colombia", "CO", "País", true, "+57");
        var created = LocalDateTime.of(2020, 1, 1, 0, 0);
        var restored = Country.restore(original.id(), "Colombia", "CO", "País", true, "+57", created, created);
        assertTrue(restored.domainEvents().isEmpty());
        restored.update("Ecuador", "EC", "Actualizado", false, "+593");
        assertEquals(original.id(), restored.id());
        assertEquals(created, restored.createdAt());
        assertEquals("Ecuador", restored.nameCountry());
        assertFalse(restored.isActive());
        assertInstanceOf(CountryUpdatedEvent.class, restored.domainEvents().getFirst());
        assertTrue(restored.updatedAt().isAfter(created));
        restored.clearDomainEvents();
        assertTrue(restored.domainEvents().isEmpty());
    }

    @Test
    void invalidUpdateDoesNotPartiallyChangeCountry() {
        var country = Country.register("Colombia", "CO", "País", true, "+57");
        var updated = country.updatedAt();
        assertThrows(IllegalArgumentException.class,
                () -> country.update("Ecuador", "EC", "x".repeat(101), true, "+593"));
        assertEquals("Colombia", country.nameCountry());
        assertEquals("CO", country.codeCountry());
        assertEquals(updated, country.updatedAt());
        assertEquals(1, country.domainEvents().size());
        assertThrows(NullPointerException.class,
                () -> Country.register("Colombia", "CO", "País", null, "+57"));
    }
}
