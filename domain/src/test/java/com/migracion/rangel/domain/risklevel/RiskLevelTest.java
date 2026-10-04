package com.migracion.rangel.domain.risklevel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.risklevel.model.aggregate.RiskLevel;
class RiskLevelTest {
    @Test void updateAndRestorePreserveCreationAndAllowAnyIntegerSeverity() {
        var item = RiskLevel.register("LOW", "Nivel", false, Integer.MIN_VALUE); var created = item.createdAt(); var id = item.id(); item.clearDomainEvents();
        item.update("HIGH", "Otro", true, Integer.MAX_VALUE);
        assertEquals(id, item.id()); assertEquals(created, item.createdAt()); assertEquals(Integer.MAX_VALUE, item.severity());
        assertEquals(1, item.domainEvents().size());
        assertTrue(RiskLevel.restore(id, item.code(), item.name(), item.active(), item.severity(), created, item.updatedAt()).domainEvents().isEmpty());
    }
    @Test void limitsAndRequiredSeverityAreCheckedBeforeMutation() {
        var item = RiskLevel.register("LOW", "Nivel", false, 0); var time = item.updatedAt();
        assertThrows(IllegalArgumentException.class, () -> item.update("x".repeat(21), "Otro", true, 1));
        assertThrows(IllegalArgumentException.class, () -> item.update("NEW", "x".repeat(51), true, 1));
        assertThrows(NullPointerException.class, () -> item.update("NEW", "Otro", true, null));
        assertThrows(NullPointerException.class, () -> item.update("NEW", "Otro", null, 1));
        assertEquals("LOW", item.code()); assertEquals("Nivel", item.name()); assertEquals(0, item.severity()); assertFalse(item.active());
        assertEquals(time, item.updatedAt()); assertEquals(1, item.domainEvents().size());
    }
}
