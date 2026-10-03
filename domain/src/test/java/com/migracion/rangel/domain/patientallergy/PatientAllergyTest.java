package com.migracion.rangel.domain.patientallergy;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.patientallergy.model.aggregate.PatientAllergy;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
class PatientAllergyTest {
    private final OffsetDateTime recorded = OffsetDateTime.parse("2026-10-03T09:30:00-05:00");
    private PatientAllergy sample() {
        return PatientAllergy.register(PatientId.generate(), "Penicilina", null, "Alta", false, recorded, ProfessionalId.generate());
    }
    @Test void recordedTimeUsesProvidedOffsetAndAuditIsPreservedOnUpdate() {
        var item = sample(); var created = item.createdAt(); var id = item.id();
        assertEquals(recorded, item.recordedAt()); assertNull(item.reaction()); assertFalse(item.active());
        item.clearDomainEvents();
        var patient = PatientId.generate(); var recorder = ProfessionalId.generate();
        var otherTime = recorded.plusDays(1);
        item.update(patient, "Polen", "Estornudos", "Leve", true, otherTime, recorder);
        assertEquals(id, item.id()); assertEquals(created, item.createdAt()); assertEquals(otherTime, item.recordedAt());
        assertEquals(recorder, item.recordedBy()); assertEquals(patient, item.patientId()); assertEquals(1, item.domainEvents().size());
        var restored = PatientAllergy.restore(id, patient, "Polen", "Estornudos", "Leve", true, otherTime, recorder, created, item.updatedAt());
        assertTrue(restored.domainEvents().isEmpty());
    }
    @Test void reactionHasNoVarcharLimitAndInvalidDetailsCannotPartiallyMutate() {
        var item = sample(); var patient = item.patientId(); var updated = item.updatedAt();
        assertThrows(IllegalArgumentException.class, () -> item.update(PatientId.generate(), "Nueva", null, "x".repeat(21), true, recorded, item.recordedBy()));
        assertThrows(IllegalArgumentException.class, () -> item.update(patient, "x".repeat(201), null, "Alta", true, recorded, item.recordedBy()));
        assertThrows(NullPointerException.class, () -> item.update(patient, "Nueva", null, "Alta", true, null, item.recordedBy()));
        assertThrows(NullPointerException.class, () -> item.update(patient, "Nueva", null, "Alta", true, recorded, null));
        assertThrows(NullPointerException.class, () -> item.update(patient, "Nueva", null, "Alta", null, recorded, item.recordedBy()));
        assertEquals(patient, item.patientId()); assertEquals("Penicilina", item.substance()); assertEquals(updated, item.updatedAt());
        assertEquals(1, item.domainEvents().size());
        item.update(patient, "Polen", "x".repeat(10000), "Leve", true, recorded, item.recordedBy());
        assertEquals(10000, item.reaction().length());
        item.update(patient, "Polen", null, "Leve", true, recorded, item.recordedBy());
        assertNull(item.reaction());
    }
}
