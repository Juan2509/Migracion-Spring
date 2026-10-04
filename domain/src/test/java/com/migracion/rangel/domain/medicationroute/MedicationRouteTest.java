package com.migracion.rangel.domain.medicationroute;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.medicationroute.model.aggregate.MedicationRoute;
import com.migracion.rangel.domain.medicationroute.event.MedicationRouteRegisteredEvent;
import com.migracion.rangel.domain.medicationroute.event.MedicationRouteUpdatedEvent;

class MedicationRouteTest {
    @Test
    void restoreAndUpdatePreserveIdentityAndCreationDate() {
        var original = MedicationRoute.register("CC", "Cédula", true);
        assertInstanceOf(MedicationRouteRegisteredEvent.class, original.domainEvents().getFirst());
        assertEquals(original.createdAt(), original.updatedAt());
        var created = LocalDateTime.of(2020, 1, 1, 0, 0);
        var restored = MedicationRoute.restore(original.id(), "CC", "Cédula", true, created, created);
        assertTrue(restored.domainEvents().isEmpty());
        restored.update("Otro code", "Otro name", false);
        assertEquals(original.id(), restored.id());
        assertEquals(created, restored.createdAt());
        assertTrue(restored.updatedAt().isAfter(created));
        assertInstanceOf(MedicationRouteUpdatedEvent.class, restored.domainEvents().getFirst());
        assertEquals("Otro code", restored.code());
        assertEquals("Otro name", restored.name());
        assertEquals(false, restored.active());
    }

    @Test
    void invalidUpdateLeavesStateAndEventsUnchanged() {
        var aggregate = MedicationRoute.register("CC", "Cédula", true);
        var updated = aggregate.updatedAt();
        assertThrows(IllegalArgumentException.class, () -> aggregate.update("x".repeat(21), "Cédula", true));
        assertEquals("CC", aggregate.code());
        assertEquals("Cédula", aggregate.name());
        assertEquals(true, aggregate.active());
        assertEquals(updated, aggregate.updatedAt());
        assertEquals(1, aggregate.domainEvents().size());
        assertThrows(NullPointerException.class, () -> MedicationRoute.register(null, "Cédula", true));
    }
}

