package com.migracion.rangel.domain.gender;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.gender.model.aggregate.Gender;
import com.migracion.rangel.domain.gender.event.GenderRegisteredEvent;
import com.migracion.rangel.domain.gender.event.GenderUpdatedEvent;

class GenderTest {
    @Test
    void restoreAndUpdatePreserveIdentityAndCreationDate() {
        var original = Gender.register("Femenino");
        assertInstanceOf(GenderRegisteredEvent.class, original.domainEvents().getFirst());
        assertEquals(original.createdAt(), original.updatedAt());
        var created = LocalDateTime.of(2020, 1, 1, 0, 0);
        var restored = Gender.restore(original.id(), "Femenino", created, created);
        assertTrue(restored.domainEvents().isEmpty());
        restored.update("Otro description");
        assertEquals(original.id(), restored.id());
        assertEquals(created, restored.createdAt());
        assertTrue(restored.updatedAt().isAfter(created));
        assertInstanceOf(GenderUpdatedEvent.class, restored.domainEvents().getFirst());
        assertEquals("Otro description", restored.description());
    }

    @Test
    void invalidUpdateLeavesStateAndEventsUnchanged() {
        var aggregate = Gender.register("Femenino");
        var updated = aggregate.updatedAt();
        assertThrows(IllegalArgumentException.class, () -> aggregate.update("x".repeat(51)));
        assertEquals("Femenino", aggregate.description());
        assertEquals(updated, aggregate.updatedAt());
        assertEquals(1, aggregate.domainEvents().size());
        assertThrows(NullPointerException.class, () -> Gender.register(null));
    }
}
