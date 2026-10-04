package com.migracion.rangel.domain.conversationstatus;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.conversationstatus.model.aggregate.ConversationStatus;
import com.migracion.rangel.domain.conversationstatus.event.ConversationStatusRegisteredEvent;
import com.migracion.rangel.domain.conversationstatus.event.ConversationStatusUpdatedEvent;

class ConversationStatusTest {
    @Test
    void nameLimitCountsUnicodeCharacters() {
        var aggregate = ConversationStatus.register("😀".repeat(50));
        assertEquals("😀".repeat(50), aggregate.nameStatus());
        assertThrows(IllegalArgumentException.class, () -> ConversationStatus.register("😀".repeat(51)));
    }

    @Test
    void restoreAndUpdatePreserveIdentityAndCreationDate() {
        var original = ConversationStatus.register("Psicólogo");
        assertInstanceOf(ConversationStatusRegisteredEvent.class, original.domainEvents().getFirst());
        assertEquals(original.createdAt(), original.updatedAt());
        var created = LocalDateTime.of(2020, 1, 1, 0, 0);
        var restored = ConversationStatus.restore(original.id(), "Psicólogo", created, created);
        assertTrue(restored.domainEvents().isEmpty());
        restored.update("Otro nameStatus");
        assertEquals(original.id(), restored.id());
        assertEquals(created, restored.createdAt());
        assertTrue(restored.updatedAt().isAfter(created));
        assertInstanceOf(ConversationStatusUpdatedEvent.class, restored.domainEvents().getFirst());
        assertEquals("Otro nameStatus", restored.nameStatus());
    }

    @Test
    void invalidUpdateLeavesStateAndEventsUnchanged() {
        var aggregate = ConversationStatus.register("Psicólogo");
        var updated = aggregate.updatedAt();
        assertThrows(IllegalArgumentException.class, () -> aggregate.update("x".repeat(51)));
        assertEquals("Psicólogo", aggregate.nameStatus());
        assertEquals(updated, aggregate.updatedAt());
        assertEquals(1, aggregate.domainEvents().size());
        assertThrows(NullPointerException.class, () -> ConversationStatus.register(null));
    }
}
