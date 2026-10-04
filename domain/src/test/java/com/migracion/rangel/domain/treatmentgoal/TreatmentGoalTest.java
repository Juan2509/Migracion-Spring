package com.migracion.rangel.domain.treatmentgoal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import com.migracion.rangel.domain.treatmentgoal.model.aggregate.TreatmentGoal;
import com.migracion.rangel.domain.treatmentplan.model.valueobject.TreatmentPlanId;
import com.migracion.rangel.domain.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
class TreatmentGoalTest {
    private final LocalDate target = LocalDate.of(2026, 10, 3);
    private final OffsetDateTime completed = OffsetDateTime.parse("2026-10-03T10:30:00-05:00");
    @Test void updateAndRestorePreserveCreationAndAcceptExtensiveTexts() {
        var item = TreatmentGoal.register(TreatmentPlanId.generate(), "Descripción", target, completed, "Notas", TreatmentGoalStatusId.generate());
        var id = item.id(); var created = item.createdAt(); item.clearDomainEvents(); var plan = TreatmentPlanId.generate(); var status = TreatmentGoalStatusId.generate(); var text = "x".repeat(10000);
        item.update(plan, text, target.plusDays(1), completed.plusDays(1), text, status);
        assertEquals(id, item.id()); assertEquals(created, item.createdAt()); assertEquals(plan, item.treatmentPlanId()); assertEquals(status, item.treatmentGoalId());
        assertEquals(text, item.description()); assertEquals(text, item.notes()); assertEquals(1, item.domainEvents().size());
        assertTrue(TreatmentGoal.restore(id, plan, text, item.targetDate(), item.completedAt(), text, status, created, item.updatedAt()).domainEvents().isEmpty());
    }
    @Test void allFieldsAreRequiredAndInvalidUpdateCannotPartiallyMutate() {
        var plan = TreatmentPlanId.generate(); var status = TreatmentGoalStatusId.generate();
        var item = TreatmentGoal.register(plan, "Descripción", target, completed, "Notas", status); var time = item.updatedAt();
        assertThrows(NullPointerException.class, () -> item.update(TreatmentPlanId.generate(), "Otra", target, null, "Otras", status));
        assertThrows(NullPointerException.class, () -> item.update(plan, "Otra", null, completed, "Otras", status));
        assertThrows(NullPointerException.class, () -> item.update(plan, null, target, completed, "Otras", status));
        assertThrows(NullPointerException.class, () -> item.update(plan, "Otra", target, completed, null, status));
        assertThrows(NullPointerException.class, () -> item.update(null, "Otra", target, completed, "Otras", status));
        assertThrows(NullPointerException.class, () -> item.update(plan, "Otra", target, completed, "Otras", null));
        assertEquals(plan, item.treatmentPlanId()); assertEquals("Descripción", item.description()); assertEquals("Notas", item.notes());
        assertEquals(time, item.updatedAt()); assertEquals(1, item.domainEvents().size());
    }
}
