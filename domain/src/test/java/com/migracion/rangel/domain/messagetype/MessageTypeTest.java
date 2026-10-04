package com.migracion.rangel.domain.messagetype;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.messagetype.model.aggregate.MessageType;
import com.migracion.rangel.domain.messagetype.event.MessageTypeRegisteredEvent;
import com.migracion.rangel.domain.messagetype.event.MessageTypeUpdatedEvent;

class MessageTypeTest {
    @Test
    void nameLimitCountsUnicodeCharacters() {
        var aggregate = MessageType.register("😀".repeat(50));
        assertEquals("😀".repeat(50), aggregate.nameType());
        assertThrows(IllegalArgumentException.class, () -> MessageType.register("😀".repeat(51)));
    }

    @Test
    void restoreAndUpdatePreserveIdentityAndCreationDate() {
        var original = MessageType.register("Psicólogo");
        assertInstanceOf(MessageTypeRegisteredEvent.class, original.domainEvents().getFirst());
        assertEquals(original.createdAt(), original.updatedAt());
        var created = LocalDateTime.of(2020, 1, 1, 0, 0);
        var restored = MessageType.restore(original.id(), "Psicólogo", created, created);
        assertTrue(restored.domainEvents().isEmpty());
        restored.update("Otro nameType");
        assertEquals(original.id(), restored.id());
        assertEquals(created, restored.createdAt());
        assertTrue(restored.updatedAt().isAfter(created));
        assertInstanceOf(MessageTypeUpdatedEvent.class, restored.domainEvents().getFirst());
        assertEquals("Otro nameType", restored.nameType());
    }

    @Test
    void invalidUpdateLeavesStateAndEventsUnchanged() {
        var aggregate = MessageType.register("Psicólogo");
        var updated = aggregate.updatedAt();
        assertThrows(IllegalArgumentException.class, () -> aggregate.update("x".repeat(51)));
        assertEquals("Psicólogo", aggregate.nameType());
        assertEquals(updated, aggregate.updatedAt());
        assertEquals(1, aggregate.domainEvents().size());
        assertThrows(NullPointerException.class, () -> MessageType.register(null));
    }
}
