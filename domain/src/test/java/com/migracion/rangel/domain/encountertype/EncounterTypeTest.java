package com.migracion.rangel.domain.encountertype;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.encountertype.model.aggregate.EncounterType;
import com.migracion.rangel.domain.encountertype.event.EncounterTypeRegisteredEvent;
import com.migracion.rangel.domain.encountertype.event.EncounterTypeUpdatedEvent;

class EncounterTypeTest {
    @Test
    void restoreAndUpdatePreserveIdentityAndCreationDate() {
        var original = EncounterType.register("INITIAL", "Inicial", true);
        assertInstanceOf(EncounterTypeRegisteredEvent.class, original.domainEvents().getFirst());
        assertEquals(original.createdAt(), original.updatedAt());
        var created = LocalDateTime.of(2020, 1, 1, 0, 0);
        var restored = EncounterType.restore(original.id(), "INITIAL", "Inicial", true, created, created);
        assertTrue(restored.domainEvents().isEmpty());
        restored.update("Otro code", "Otro name", false);
        assertEquals(original.id(), restored.id());
        assertEquals(created, restored.createdAt());
        assertTrue(restored.updatedAt().isAfter(created));
        assertInstanceOf(EncounterTypeUpdatedEvent.class, restored.domainEvents().getFirst());
        assertEquals("Otro code", restored.code());
        assertEquals("Otro name", restored.name());
        assertEquals(false, restored.active());
    }

    @Test
    void invalidUpdateLeavesStateAndEventsUnchanged() {
        var aggregate = EncounterType.register("INITIAL", "Inicial", true);
        var updated = aggregate.updatedAt();
        assertThrows(IllegalArgumentException.class, () -> aggregate.update("x".repeat(21), "Inicial", true));
        assertThrows(IllegalArgumentException.class, () -> aggregate.update("NEW", "x".repeat(51), true));
        assertThrows(NullPointerException.class, () -> aggregate.update("NEW", "Nueva", null));
        assertThrows(NullPointerException.class, () -> aggregate.update("NEW", null, true));
        assertEquals("INITIAL", aggregate.code());
        assertEquals("Inicial", aggregate.name());
        assertEquals(true, aggregate.active());
        assertEquals(updated, aggregate.updatedAt());
        assertEquals(1, aggregate.domainEvents().size());
        assertThrows(NullPointerException.class, () -> EncounterType.register(null, "Inicial", true));
    }
}

