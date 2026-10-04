package com.migracion.rangel.domain.diagnosticsystem;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.diagnosticsystem.model.aggregate.DiagnosticSystem;
import com.migracion.rangel.domain.diagnosticsystem.event.DiagnosticSystemRegisteredEvent;
import com.migracion.rangel.domain.diagnosticsystem.event.DiagnosticSystemUpdatedEvent;

class DiagnosticSystemTest {
    @Test
    void versionLengthBoundaryAndInvalidUpdatePreserveState() {
        var aggregate = DiagnosticSystem.register("ICD", "Clasificación", true, "v".repeat(20));
        var updatedAt = aggregate.updatedAt();
        assertThrows(IllegalArgumentException.class,
                () -> aggregate.update("CHANGED", "Otro nombre", false, "v".repeat(21)));
        assertEquals("ICD", aggregate.code());
        assertEquals("Clasificación", aggregate.name());
        assertEquals(true, aggregate.active());
        assertEquals("v".repeat(20), aggregate.version());
        assertEquals(updatedAt, aggregate.updatedAt());
        assertEquals(1, aggregate.domainEvents().size());
    }

    @Test
    void missingVersionDoesNotPartiallyUpdateAggregate() {
        var aggregate = DiagnosticSystem.register("ORIGINAL", "Nombre", true, "Descripción");
        var updatedAt = aggregate.updatedAt();
        assertThrows(NullPointerException.class,
                () -> aggregate.update("CHANGED", "Otro nombre", false, null));
        assertEquals("ORIGINAL", aggregate.code());
        assertEquals("Nombre", aggregate.name());
        assertEquals(true, aggregate.active());
        assertEquals("Descripción", aggregate.version());
        assertEquals(updatedAt, aggregate.updatedAt());
        assertEquals(1, aggregate.domainEvents().size());
        assertThrows(NullPointerException.class,
                () -> DiagnosticSystem.register("CODE", "Nombre", true, null));
    }

    @Test
    void restoreAndUpdatePreserveIdentityAndCreationDate() {
        var original = DiagnosticSystem.register("CC", "Cédula", true, "Descripción");
        assertInstanceOf(DiagnosticSystemRegisteredEvent.class, original.domainEvents().getFirst());
        assertEquals(original.createdAt(), original.updatedAt());
        var created = LocalDateTime.of(2020, 1, 1, 0, 0);
        var restored = DiagnosticSystem.restore(original.id(), "CC", "Cédula", true, "Descripción", created, created);
        assertTrue(restored.domainEvents().isEmpty());
        restored.update("Otro code", "Otro name", false, "Otra descripción");
        assertEquals(original.id(), restored.id());
        assertEquals(created, restored.createdAt());
        assertTrue(restored.updatedAt().isAfter(created));
        assertInstanceOf(DiagnosticSystemUpdatedEvent.class, restored.domainEvents().getFirst());
        assertEquals("Otro code", restored.code());
        assertEquals("Otro name", restored.name());
        assertEquals(false, restored.active());
    }

    @Test
    void invalidUpdateLeavesStateAndEventsUnchanged() {
        var aggregate = DiagnosticSystem.register("CC", "Cédula", true, "Descripción");
        var updated = aggregate.updatedAt();
        assertThrows(IllegalArgumentException.class, () -> aggregate.update("x".repeat(21), "Cédula", true, "Descripción"));
        assertEquals("CC", aggregate.code());
        assertEquals("Cédula", aggregate.name());
        assertEquals(true, aggregate.active());
        assertEquals(updated, aggregate.updatedAt());
        assertEquals(1, aggregate.domainEvents().size());
        assertThrows(NullPointerException.class, () -> DiagnosticSystem.register(null, "Cédula", true, "Descripción"));
    }
}
