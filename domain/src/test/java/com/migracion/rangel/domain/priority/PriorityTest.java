package com.migracion.rangel.domain.priority;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.priority.model.aggregate.Priority;
import com.migracion.rangel.domain.priority.event.PriorityRegisteredEvent;
import com.migracion.rangel.domain.priority.event.PriorityUpdatedEvent;

class PriorityTest {
    @Test
    void nameLimitCountsUnicodeCharacters() {
        var aggregate = Priority.register("😀".repeat(50));
        assertEquals("😀".repeat(50), aggregate.namePriority());
        assertThrows(IllegalArgumentException.class, () -> Priority.register("😀".repeat(51)));
    }

    @Test
    void restoreAndUpdatePreserveIdentityAndCreationDate() {
        var original = Priority.register("Psicólogo");
        assertInstanceOf(PriorityRegisteredEvent.class, original.domainEvents().getFirst());
        assertEquals(original.createdAt(), original.updatedAt());
        var created = LocalDateTime.of(2020, 1, 1, 0, 0);
        var restored = Priority.restore(original.id(), "Psicólogo", created, created);
        assertTrue(restored.domainEvents().isEmpty());
        restored.update("Otro namePriority");
        assertEquals(original.id(), restored.id());
        assertEquals(created, restored.createdAt());
        assertTrue(restored.updatedAt().isAfter(created));
        assertInstanceOf(PriorityUpdatedEvent.class, restored.domainEvents().getFirst());
        assertEquals("Otro namePriority", restored.namePriority());
    }

    @Test
    void invalidUpdateLeavesStateAndEventsUnchanged() {
        var aggregate = Priority.register("Psicólogo");
        var updated = aggregate.updatedAt();
        assertThrows(IllegalArgumentException.class, () -> aggregate.update("x".repeat(51)));
        assertEquals("Psicólogo", aggregate.namePriority());
        assertEquals(updated, aggregate.updatedAt());
        assertEquals(1, aggregate.domainEvents().size());
        assertThrows(NullPointerException.class, () -> Priority.register(null));
    }
}
