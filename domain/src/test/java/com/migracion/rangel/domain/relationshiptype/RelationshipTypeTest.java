package com.migracion.rangel.domain.relationshiptype;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.relationshiptype.model.aggregate.RelationshipType;
import com.migracion.rangel.domain.relationshiptype.event.RelationshipTypeRegisteredEvent;
import com.migracion.rangel.domain.relationshiptype.event.RelationshipTypeUpdatedEvent;

class RelationshipTypeTest {
    @Test
    void restoreHasNoEventsAndUpdatePreservesIdentity() {
        var original = RelationshipType.register("Madre");
        assertInstanceOf(RelationshipTypeRegisteredEvent.class, original.domainEvents().getFirst());
        var restored = RelationshipType.restore(original.id(), original.description());
        assertTrue(restored.domainEvents().isEmpty());
        restored.update("Padre");
        assertEquals(original.id(), restored.id());
        assertEquals("Padre", restored.description());
        assertInstanceOf(RelationshipTypeUpdatedEvent.class, restored.domainEvents().getFirst());
    }
    @Test
    void invalidUpdateLeavesDescriptionAndEventsUnchanged() {
        var aggregate = RelationshipType.register("Madre");
        assertThrows(IllegalArgumentException.class, () -> aggregate.update("x".repeat(51)));
        assertEquals("Madre", aggregate.description());
        assertEquals(1, aggregate.domainEvents().size());
        assertThrows(NullPointerException.class, () -> RelationshipType.register(null));
    }
}
