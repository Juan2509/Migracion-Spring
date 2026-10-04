package com.migracion.rangel.domain.consenttype;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.consenttype.model.aggregate.ConsentType;
import com.migracion.rangel.domain.consenttype.event.ConsentTypeRegisteredEvent;
import com.migracion.rangel.domain.consenttype.event.ConsentTypeUpdatedEvent;

class ConsentTypeTest {
    @Test
    void missingDescriptionDoesNotPartiallyUpdateAggregate() {
        var aggregate = ConsentType.register("ORIGINAL", "Nombre", true, "Descripción");
        var updatedAt = aggregate.updatedAt();
        assertThrows(NullPointerException.class,
                () -> aggregate.update("CHANGED", "Otro nombre", false, null));
        assertEquals("ORIGINAL", aggregate.code());
        assertEquals("Nombre", aggregate.name());
        assertEquals(true, aggregate.active());
        assertEquals("Descripción", aggregate.description());
        assertEquals(updatedAt, aggregate.updatedAt());
        assertEquals(1, aggregate.domainEvents().size());
        assertThrows(NullPointerException.class,
                () -> ConsentType.register("CODE", "Nombre", true, null));
    }

    @Test
    void restoreAndUpdatePreserveIdentityAndCreationDate() {
        var original = ConsentType.register("CC", "Cédula", true, "Descripción");
        assertInstanceOf(ConsentTypeRegisteredEvent.class, original.domainEvents().getFirst());
        assertEquals(original.createdAt(), original.updatedAt());
        var created = LocalDateTime.of(2020, 1, 1, 0, 0);
        var restored = ConsentType.restore(original.id(), "CC", "Cédula", true, "Descripción", created, created);
        assertTrue(restored.domainEvents().isEmpty());
        restored.update("Otro code", "Otro name", false, "Otra descripción");
        assertEquals(original.id(), restored.id());
        assertEquals(created, restored.createdAt());
        assertTrue(restored.updatedAt().isAfter(created));
        assertInstanceOf(ConsentTypeUpdatedEvent.class, restored.domainEvents().getFirst());
        assertEquals("Otro code", restored.code());
        assertEquals("Otro name", restored.name());
        assertEquals(false, restored.active());
    }

    @Test
    void invalidUpdateLeavesStateAndEventsUnchanged() {
        var aggregate = ConsentType.register("CC", "Cédula", true, "Descripción");
        var updated = aggregate.updatedAt();
        assertThrows(IllegalArgumentException.class, () -> aggregate.update("x".repeat(21), "Cédula", true, "Descripción"));
        assertEquals("CC", aggregate.code());
        assertEquals("Cédula", aggregate.name());
        assertEquals(true, aggregate.active());
        assertEquals(updated, aggregate.updatedAt());
        assertEquals(1, aggregate.domainEvents().size());
        assertThrows(NullPointerException.class, () -> ConsentType.register(null, "Cédula", true, "Descripción"));
    }
}
