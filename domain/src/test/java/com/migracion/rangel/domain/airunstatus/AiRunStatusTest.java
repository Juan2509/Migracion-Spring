package com.migracion.rangel.domain.airunstatus;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.airunstatus.model.aggregate.AiRunStatus;
import com.migracion.rangel.domain.airunstatus.event.AiRunStatusRegisteredEvent;
import com.migracion.rangel.domain.airunstatus.event.AiRunStatusUpdatedEvent;

class AiRunStatusTest {
    @Test
    void nameLimitCountsUnicodeCharacters() {
        var aggregate = AiRunStatus.register("😀".repeat(50));
        assertEquals("😀".repeat(50), aggregate.nameStatus());
        assertThrows(IllegalArgumentException.class, () -> AiRunStatus.register("😀".repeat(51)));
    }

    @Test
    void restoreAndUpdatePreserveIdentityAndCreationDate() {
        var original = AiRunStatus.register("Psicólogo");
        assertInstanceOf(AiRunStatusRegisteredEvent.class, original.domainEvents().getFirst());
        assertEquals(original.createdAt(), original.updatedAt());
        var created = LocalDateTime.of(2020, 1, 1, 0, 0);
        var restored = AiRunStatus.restore(original.id(), "Psicólogo", created, created);
        assertTrue(restored.domainEvents().isEmpty());
        restored.update("Otro nameStatus");
        assertEquals(original.id(), restored.id());
        assertEquals(created, restored.createdAt());
        assertTrue(restored.updatedAt().isAfter(created));
        assertInstanceOf(AiRunStatusUpdatedEvent.class, restored.domainEvents().getFirst());
        assertEquals("Otro nameStatus", restored.nameStatus());
    }

    @Test
    void invalidUpdateLeavesStateAndEventsUnchanged() {
        var aggregate = AiRunStatus.register("Psicólogo");
        var updated = aggregate.updatedAt();
        assertThrows(IllegalArgumentException.class, () -> aggregate.update("x".repeat(51)));
        assertEquals("Psicólogo", aggregate.nameStatus());
        assertEquals(updated, aggregate.updatedAt());
        assertEquals(1, aggregate.domainEvents().size());
        assertThrows(NullPointerException.class, () -> AiRunStatus.register(null));
    }
}
