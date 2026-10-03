package com.migracion.rangel.domain.clinicalnote;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.clinicalnote.model.aggregate.ClinicalNote;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
class ClinicalNoteTest {
    private final OffsetDateTime signed = OffsetDateTime.parse("2026-10-03T09:30:00-05:00");
    @Test void updateAndRestorePreserveIdentityAndCreationAndTextHasNoVarcharLimit() {
        var item = ClinicalNote.register(EncounterId.generate(), "S", "O", "A", "P", "N", signed, ProfessionalId.generate());
        var id = item.id(); var created = item.createdAt(); item.clearDomainEvents();
        var encounter = EncounterId.generate(); var professional = ProfessionalId.generate(); var text = "x".repeat(10000);
        item.update(encounter, text, text, text, text, text, signed.plusDays(1), professional);
        assertEquals(id, item.id()); assertEquals(created, item.createdAt()); assertEquals(encounter, item.encounterId());
        assertEquals(professional, item.professionalId()); assertEquals(text, item.subjective()); assertEquals(text, item.objective());
        assertEquals(text, item.assessment()); assertEquals(text, item.plan()); assertEquals(text, item.additionalNotes());
        assertEquals(signed.plusDays(1), item.signedAt()); assertEquals(1, item.domainEvents().size());
        var restored = ClinicalNote.restore(id, encounter, text, text, text, text, text, item.signedAt(), professional, created, item.updatedAt());
        assertTrue(restored.domainEvents().isEmpty());
    }
    @Test void everyRequiredFieldIsCheckedWithoutPartialMutation() {
        var encounter = EncounterId.generate(); var professional = ProfessionalId.generate();
        var item = ClinicalNote.register(encounter, "S", "O", "A", "P", "N", signed, professional);
        var updated = item.updatedAt();
        for (int i = 0; i < 5; i++) {
            String[] texts = {"Nueva S", "Nueva O", "Nueva A", "Nueva P", "Nueva N"}; texts[i] = null;
            assertThrows(NullPointerException.class, () -> item.update(EncounterId.generate(), texts[0], texts[1], texts[2], texts[3], texts[4], signed, professional));
            assertEquals(encounter, item.encounterId()); assertEquals("S", item.subjective()); assertEquals("N", item.additionalNotes());
        }
        assertThrows(NullPointerException.class, () -> item.update(null, "S", "O", "A", "P", "N", signed, professional));
        assertThrows(NullPointerException.class, () -> item.update(encounter, "S", "O", "A", "P", "N", null, professional));
        assertThrows(NullPointerException.class, () -> item.update(encounter, "S", "O", "A", "P", "N", signed, null));
        assertEquals(updated, item.updatedAt()); assertEquals(1, item.domainEvents().size());
    }
}
