package com.migracion.rangel.domain.documenttype;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.documenttype.model.aggregate.DocumentType;
import com.migracion.rangel.domain.documenttype.event.DocumentTypeRegisteredEvent;
import com.migracion.rangel.domain.documenttype.event.DocumentTypeUpdatedEvent;

class DocumentTypeTest {
    @Test
    void restoreAndUpdatePreserveIdentityAndCreationDate() {
        var original = DocumentType.register("CC", "Cédula", true);
        assertInstanceOf(DocumentTypeRegisteredEvent.class, original.domainEvents().getFirst());
        assertEquals(original.createdAt(), original.updatedAt());
        var created = LocalDateTime.of(2020, 1, 1, 0, 0);
        var restored = DocumentType.restore(original.id(), "CC", "Cédula", true, created, created);
        assertTrue(restored.domainEvents().isEmpty());
        restored.update("Otro code", "Otro name", false);
        assertEquals(original.id(), restored.id());
        assertEquals(created, restored.createdAt());
        assertTrue(restored.updatedAt().isAfter(created));
        assertInstanceOf(DocumentTypeUpdatedEvent.class, restored.domainEvents().getFirst());
        assertEquals("Otro code", restored.code());
        assertEquals("Otro name", restored.name());
        assertEquals(false, restored.active());
    }

    @Test
    void invalidUpdateLeavesStateAndEventsUnchanged() {
        var aggregate = DocumentType.register("CC", "Cédula", true);
        var updated = aggregate.updatedAt();
        assertThrows(IllegalArgumentException.class, () -> aggregate.update("x".repeat(21), "Cédula", true));
        assertEquals("CC", aggregate.code());
        assertEquals("Cédula", aggregate.name());
        assertEquals(true, aggregate.active());
        assertEquals(updated, aggregate.updatedAt());
        assertEquals(1, aggregate.domainEvents().size());
        assertThrows(NullPointerException.class, () -> DocumentType.register(null, "Cédula", true));
    }
}
