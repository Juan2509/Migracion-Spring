package com.migracion.rangel.domain.sendertype;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.sendertype.model.aggregate.SenderType;
import com.migracion.rangel.domain.sendertype.event.SenderTypeRegisteredEvent;
import com.migracion.rangel.domain.sendertype.event.SenderTypeUpdatedEvent;

class SenderTypeTest {
    @Test
    void nameLimitCountsUnicodeCharacters() {
        var aggregate = SenderType.register("😀".repeat(50));
        assertEquals("😀".repeat(50), aggregate.nameType());
        assertThrows(IllegalArgumentException.class, () -> SenderType.register("😀".repeat(51)));
    }

    @Test
    void restoreAndUpdatePreserveIdentityAndCreationDate() {
        var original = SenderType.register("Psicólogo");
        assertInstanceOf(SenderTypeRegisteredEvent.class, original.domainEvents().getFirst());
        assertEquals(original.createdAt(), original.updatedAt());
        var created = LocalDateTime.of(2020, 1, 1, 0, 0);
        var restored = SenderType.restore(original.id(), "Psicólogo", created, created);
        assertTrue(restored.domainEvents().isEmpty());
        restored.update("Otro nameType");
        assertEquals(original.id(), restored.id());
        assertEquals(created, restored.createdAt());
        assertTrue(restored.updatedAt().isAfter(created));
        assertInstanceOf(SenderTypeUpdatedEvent.class, restored.domainEvents().getFirst());
        assertEquals("Otro nameType", restored.nameType());
    }

    @Test
    void invalidUpdateLeavesStateAndEventsUnchanged() {
        var aggregate = SenderType.register("Psicólogo");
        var updated = aggregate.updatedAt();
        assertThrows(IllegalArgumentException.class, () -> aggregate.update("x".repeat(51)));
        assertEquals("Psicólogo", aggregate.nameType());
        assertEquals(updated, aggregate.updatedAt());
        assertEquals(1, aggregate.domainEvents().size());
        assertThrows(NullPointerException.class, () -> SenderType.register(null));
    }
}
