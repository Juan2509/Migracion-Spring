package com.migracion.rangel.domain.study;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.study.model.aggregate.Study;
import com.migracion.rangel.domain.study.event.StudyRegisteredEvent;
import com.migracion.rangel.domain.study.event.StudyUpdatedEvent;

class StudyTest {
    @Test
    void restoreAndUpdatePreserveIdentityAndCreationDate() {
        var original = Study.register("Psicólogo");
        assertInstanceOf(StudyRegisteredEvent.class, original.domainEvents().getFirst());
        assertEquals(original.createdAt(), original.updatedAt());
        var created = LocalDateTime.of(2020, 1, 1, 0, 0);
        var restored = Study.restore(original.id(), "Psicólogo", created, created);
        assertTrue(restored.domainEvents().isEmpty());
        restored.update("Otro name");
        assertEquals(original.id(), restored.id());
        assertEquals(created, restored.createdAt());
        assertTrue(restored.updatedAt().isAfter(created));
        assertInstanceOf(StudyUpdatedEvent.class, restored.domainEvents().getFirst());
        assertEquals("Otro name", restored.name());
    }

    @Test
    void invalidUpdateLeavesStateAndEventsUnchanged() {
        var aggregate = Study.register("Psicólogo");
        var updated = aggregate.updatedAt();
        assertThrows(IllegalArgumentException.class, () -> aggregate.update("x".repeat(41)));
        assertEquals("Psicólogo", aggregate.name());
        assertEquals(updated, aggregate.updatedAt());
        assertEquals(1, aggregate.domainEvents().size());
        assertThrows(NullPointerException.class, () -> Study.register(null));
    }
}
