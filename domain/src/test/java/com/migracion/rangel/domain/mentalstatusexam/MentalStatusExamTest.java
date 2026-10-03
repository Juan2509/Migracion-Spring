package com.migracion.rangel.domain.mentalstatusexam;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.mentalstatusexam.model.aggregate.MentalStatusExam;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
class MentalStatusExamTest {
    private final String[] fields = {"appearance", "behavior", "attitude", "consciousness", "orientation", "attention", "memory", "speech", "mood", "affect", "thoughtProcess", "thoughtContent", "perception", "judgment", "insight", "psychomotorActivity", "observations"};
    private String[] texts() { return fields.clone(); }
    @Test void updateAndRestorePreserveCreatorCreationAndAllTextFields() throws ReflectiveOperationException {
        var t = texts(); var encounter = EncounterId.generate(); var creator = ProfessionalId.generate();
        var item = MentalStatusExam.register(encounter, t[0], t[1], t[2], t[3], t[4], t[5], t[6], t[7], t[8], t[9], t[10], t[11], t[12], t[13], t[14], t[15], t[16], creator);
        var id = item.id(); var created = item.createdAt(); item.clearDomainEvents();
        for (int i = 0; i < t.length; i++) t[i] = fields[i] + "x".repeat(10000);
        var other = EncounterId.generate(); item.update(other, t[0], t[1], t[2], t[3], t[4], t[5], t[6], t[7], t[8], t[9], t[10], t[11], t[12], t[13], t[14], t[15], t[16]);
        assertEquals(id, item.id()); assertEquals(created, item.createdAt()); assertEquals(creator, item.createdBy()); assertEquals(other, item.encounterId());
        for (int i = 0; i < fields.length; i++) assertEquals(t[i], MentalStatusExam.class.getMethod(fields[i]).invoke(item));
        assertEquals(1, item.domainEvents().size());
        var restored = MentalStatusExam.restore(id, other, t[0], t[1], t[2], t[3], t[4], t[5], t[6], t[7], t[8], t[9], t[10], t[11], t[12], t[13], t[14], t[15], t[16], creator, created);
        assertTrue(restored.domainEvents().isEmpty()); assertEquals(created, restored.createdAt()); assertEquals(creator, restored.createdBy());
    }
    @Test void everyRequiredTextAndReferenceIsValidatedBeforeMutation() throws ReflectiveOperationException {
        var original = texts(); var encounter = EncounterId.generate(); var creator = ProfessionalId.generate(); var t = original.clone();
        var item = MentalStatusExam.register(encounter, t[0], t[1], t[2], t[3], t[4], t[5], t[6], t[7], t[8], t[9], t[10], t[11], t[12], t[13], t[14], t[15], t[16], creator);
        for (int missing = 0; missing < fields.length; missing++) {
            String[] invalid = original.clone(); Arrays.fill(invalid, "Nuevo"); invalid[missing] = null;
            assertThrows(NullPointerException.class, () -> update(item, EncounterId.generate(), invalid));
            assertEquals(encounter, item.encounterId());
            for (int i = 0; i < fields.length; i++) assertEquals(original[i], MentalStatusExam.class.getMethod(fields[i]).invoke(item));
            assertEquals(1, item.domainEvents().size());
        }
        assertThrows(NullPointerException.class, () -> update(item, null, original));
        assertThrows(NullPointerException.class, () -> MentalStatusExam.register(encounter, t[0], t[1], t[2], t[3], t[4], t[5], t[6], t[7], t[8], t[9], t[10], t[11], t[12], t[13], t[14], t[15], t[16], null));
    }
    private void update(MentalStatusExam item, EncounterId encounter, String[] t) { item.update(encounter, t[0], t[1], t[2], t[3], t[4], t[5], t[6], t[7], t[8], t[9], t[10], t[11], t[12], t[13], t[14], t[15], t[16]); }
}
