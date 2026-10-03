package com.migracion.rangel.domain.clinicalrecord;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.clinicalrecord.model.aggregate.ClinicalRecord;
import com.migracion.rangel.domain.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
class ClinicalRecordTest {
    private final LocalDateTime creation = LocalDateTime.parse("2026-10-03T08:00:00");
    private final OffsetDateTime opened = OffsetDateTime.parse("2026-10-03T09:30:00-05:00");
    private ClinicalRecord sample() { return ClinicalRecord.register(PatientId.generate(), creation, "HC-1", opened, opened.plusHours(1), ClinicalRecordStatusId.generate(), ProfessionalId.generate()); }
    @Test void updatePreservesCreationAuditAndCreatorWithoutInventingUpdatedAt() {
        var item = sample(); var id = item.id(); var created = item.createdAt(); var creator = item.createdBy();
        assertEquals(1, item.domainEvents().size()); item.clearDomainEvents();
        var patient = PatientId.generate(); var status = ClinicalRecordStatusId.generate();
        item.update(patient, creation.plusDays(1), "HC-2", opened.plusDays(1), opened.plusDays(1).plusHours(2), status);
        assertEquals(id, item.id()); assertEquals(created, item.createdAt()); assertEquals(creator, item.createdBy());
        assertEquals(patient, item.patientId()); assertEquals(status, item.statusId()); assertEquals("HC-2", item.recordNumber());
        assertEquals(1, item.domainEvents().size());
        var restored = ClinicalRecord.restore(id, item.patientId(), item.creationDate(), item.recordNumber(), item.openedAt(), item.closedAt(), item.statusId(), creator, created);
        assertEquals(created, restored.createdAt()); assertTrue(restored.domainEvents().isEmpty());
    }
    @Test void requiredClosingDateAndOtherFieldsAreValidatedWithoutPartialUpdate() {
        var item = sample(); var patient = item.patientId();
        assertThrows(NullPointerException.class, () -> item.update(PatientId.generate(), creation, "Nueva", opened, null, item.statusId()));
        assertThrows(IllegalArgumentException.class, () -> item.update(patient, creation, "x".repeat(51), opened, opened, item.statusId()));
        assertThrows(NullPointerException.class, () -> item.update(patient, null, "Nueva", opened, opened, item.statusId()));
        assertThrows(NullPointerException.class, () -> item.update(patient, creation, "Nueva", null, opened, item.statusId()));
        assertThrows(NullPointerException.class, () -> item.update(patient, creation, "Nueva", opened, opened, null));
        assertEquals(patient, item.patientId()); assertEquals("HC-1", item.recordNumber()); assertEquals(opened.plusHours(1), item.closedAt());
        assertEquals(1, item.domainEvents().size());
    }
}
