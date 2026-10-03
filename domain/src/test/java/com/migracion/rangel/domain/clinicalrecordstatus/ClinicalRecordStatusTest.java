package com.migracion.rangel.domain.clinicalrecordstatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.clinicalrecordstatus.model.aggregate.ClinicalRecordStatus;
class ClinicalRecordStatusTest {
    @Test void updateAndRestorePreserveIdentityCreationAndEvents() {
        var item = ClinicalRecordStatus.register("OPEN", "Abierta"); var id = item.id(); var created = item.createdAt();
        assertEquals(1, item.domainEvents().size()); item.clearDomainEvents();
        item.update("CLOSED", "Cerrada");
        assertEquals(id, item.id()); assertEquals(created, item.createdAt()); assertEquals("CLOSED", item.code()); assertEquals("Cerrada", item.name());
        assertEquals(1, item.domainEvents().size());
        var restored = ClinicalRecordStatus.restore(id, item.code(), item.name(), created, item.updatedAt());
        assertTrue(restored.domainEvents().isEmpty());
    }
    @Test void textLimitsAndNullsAreValidatedBeforeAnyMutation() {
        var item = ClinicalRecordStatus.register("OPEN", "Abierta"); var updated = item.updatedAt();
        assertThrows(IllegalArgumentException.class, () -> item.update("NEW", "x".repeat(51)));
        assertThrows(IllegalArgumentException.class, () -> item.update("x".repeat(21), "Nueva"));
        assertThrows(NullPointerException.class, () -> item.update(null, "Nueva"));
        assertThrows(NullPointerException.class, () -> item.update("NEW", null));
        assertEquals("OPEN", item.code()); assertEquals("Abierta", item.name()); assertEquals(updated, item.updatedAt());
        assertEquals(1, item.domainEvents().size());
    }
}
