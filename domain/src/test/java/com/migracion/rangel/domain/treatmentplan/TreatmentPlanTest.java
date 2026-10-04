package com.migracion.rangel.domain.treatmentplan;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import com.migracion.rangel.domain.treatmentplan.model.aggregate.TreatmentPlan;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.treatmentstatus.model.valueobject.TreatmentStatusId;
class TreatmentPlanTest {
    private final LocalDate start = LocalDate.of(2026, 10, 3);
    @Test void updateAndRestorePreserveIdAndCreationAndDescriptionHasNoVarcharLimit() {
        var item = TreatmentPlan.register(EncounterId.generate(), "Título", "Descripción", start, start.plusDays(1), TreatmentStatusId.generate(), ProfessionalId.generate());
        var id = item.id(); var created = item.createdAt(); item.clearDomainEvents();
        var encounter = EncounterId.generate(); var status = TreatmentStatusId.generate(); var professional = ProfessionalId.generate();
        item.update(encounter, "Otro", "x".repeat(10000), start.plusDays(2), start.plusDays(3), status, professional);
        assertEquals(id, item.id()); assertEquals(created, item.createdAt()); assertEquals(encounter, item.encounterId());
        assertEquals(status, item.treatmentStatusId()); assertEquals(professional, item.professionalId()); assertEquals(10000, item.description().length());
        assertEquals(1, item.domainEvents().size());
        assertTrue(TreatmentPlan.restore(id, encounter, item.title(), item.description(), item.startDate(), item.endDate(), status, professional, created, item.updatedAt()).domainEvents().isEmpty());
    }
    @Test void titleLimitAndRequiredDatesAndDescriptionAreCheckedWithoutPartialMutation() {
        var encounter = EncounterId.generate(); var status = TreatmentStatusId.generate(); var professional = ProfessionalId.generate();
        var item = TreatmentPlan.register(encounter, "Título", "Descripción", start, start, status, professional); var updated = item.updatedAt();
        assertThrows(IllegalArgumentException.class, () -> item.update(EncounterId.generate(), "x".repeat(201), "Otra", start, start, status, professional));
        assertThrows(NullPointerException.class, () -> item.update(encounter, "Otro", "Otra", null, start, status, professional));
        assertThrows(NullPointerException.class, () -> item.update(encounter, "Otro", "Otra", start, null, status, professional));
        assertThrows(NullPointerException.class, () -> item.update(encounter, "Otro", null, start, start, status, professional));
        assertEquals(encounter, item.encounterId()); assertEquals("Título", item.title()); assertEquals(updated, item.updatedAt()); assertEquals(1, item.domainEvents().size());
    }
}
