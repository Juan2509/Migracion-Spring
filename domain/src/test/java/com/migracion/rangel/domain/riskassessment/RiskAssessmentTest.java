package com.migracion.rangel.domain.riskassessment;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.OffsetDateTime;
import com.migracion.rangel.domain.riskassessment.model.aggregate.RiskAssessment;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.risklevel.model.valueobject.RiskLevelId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
class RiskAssessmentTest {
    private final OffsetDateTime time = OffsetDateTime.parse("2026-10-03T09:30:00-05:00");
    @Test void updateAndRestorePreserveIdAndAcceptFalseAndExtensiveTextsWithoutAuditColumns() {
        var item = RiskAssessment.register(EncounterId.generate(), RiskLevelId.generate(), false, false, false, false, false, "R", "P", "A", "O", time, ProfessionalId.generate());
        var id = item.id(); item.clearDomainEvents(); var encounter = EncounterId.generate(); var level = RiskLevelId.generate(); var assessor = ProfessionalId.generate(); var text = "x".repeat(10000);
        item.update(encounter, level, true, true, true, true, true, text, text, text, text, time.plusDays(1), assessor);
        assertEquals(id, item.id()); assertEquals(encounter, item.encounterId()); assertEquals(level, item.riskLevelId()); assertEquals(assessor, item.assessedBy());
        assertEquals(text, item.riskFactors()); assertEquals(text, item.protectiveFactors()); assertEquals(text, item.clinicalActions()); assertEquals(text, item.observations());
        assertEquals(time.plusDays(1), item.assessedAt()); assertEquals(1, item.domainEvents().size());
        assertTrue(RiskAssessment.restore(id, encounter, level, true, true, true, true, true, text, text, text, text, item.assessedAt(), assessor).domainEvents().isEmpty());
    }
    @Test void allFiveIndicatorsAndFourTextsAndAssessmentAuditAreRequiredWithoutPartialMutation() {
        var encounter = EncounterId.generate(); var level = RiskLevelId.generate(); var assessor = ProfessionalId.generate();
        var item = RiskAssessment.register(encounter, level, false, false, false, false, false, "R", "P", "A", "O", time, assessor);
        for (int missing = 0; missing < 9; missing++) {
            Boolean[] b = {true, true, true, true, true}; String[] t = {"Nuevo R", "Nuevo P", "Nuevo A", "Nuevo O"};
            if (missing < 5) b[missing] = null; else t[missing - 5] = null;
            assertThrows(NullPointerException.class, () -> item.update(EncounterId.generate(), level, b[0], b[1], b[2], b[3], b[4], t[0], t[1], t[2], t[3], time, assessor));
            assertEquals(encounter, item.encounterId()); assertFalse(item.suicidalIdeation()); assertFalse(item.suicidePlan()); assertFalse(item.suicideIntent());
            assertFalse(item.selfHarm()); assertFalse(item.harmToOthers()); assertEquals("O", item.observations()); assertEquals(1, item.domainEvents().size());
        }
        assertThrows(NullPointerException.class, () -> item.update(encounter, level, false, false, false, false, false, "R", "P", "A", "O", null, assessor));
        assertThrows(NullPointerException.class, () -> item.update(encounter, level, false, false, false, false, false, "R", "P", "A", "O", time, null));
    }
}
