package com.migracion.rangel.domain.encounter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import com.migracion.rangel.domain.clinicalrecord.model.valueobject.ClinicalRecordId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.encountertype.model.valueobject.EncounterTypeId;
import com.migracion.rangel.domain.encountermodality.model.valueobject.EncounterModalityId;
import com.migracion.rangel.domain.encounterstatus.model.valueobject.EncounterStatusId;

import com.migracion.rangel.domain.encounter.model.aggregate.Encounter;
class EncounterTest {
    private final OffsetDateTime started = OffsetDateTime.parse("2026-10-03T09:30:00-05:00");
    private Encounter sample() {
        return Encounter.register(ClinicalRecordId.generate(), ProfessionalId.generate(), EncounterTypeId.generate(),
                started, started.plusHours(1), "Motivo", "Condición", EncounterModalityId.generate(),
                EncounterStatusId.generate(), ProfessionalId.generate(), ProfessionalId.generate());
    }
    @Test void updatePreservesCreatorIdentityAndCreationAuditAndRecordsEvent() {
        var item = sample(); var id = item.id(); var created = item.createdAt(); var creator = item.createdBy();
        assertEquals(1, item.domainEvents().size()); item.clearDomainEvents();
        var record = ClinicalRecordId.generate(); var professional = ProfessionalId.generate(); var type = EncounterTypeId.generate();
        var modality = EncounterModalityId.generate(); var status = EncounterStatusId.generate(); var updater = ProfessionalId.generate();
        item.update(record, professional, type, started.plusDays(1), started.plusDays(1).plusHours(2), "Nuevo", "Nueva", modality, status, updater);
        assertEquals(id, item.id()); assertEquals(created, item.createdAt()); assertEquals(creator, item.createdBy());
        assertEquals(record, item.clinicalRecordId()); assertEquals(professional, item.professionalId()); assertEquals(type, item.encounterTypeId());
        assertEquals(modality, item.modalityId()); assertEquals(status, item.statusId()); assertEquals(updater, item.updatedBy());
        assertEquals(1, item.domainEvents().size());
        var restored = Encounter.restore(id, record, professional, type, item.startedAt(), item.endedAt(), item.reasonForVisit(),
                item.currentCondition(), modality, status, created, creator, item.updatedAt(), updater);
        assertTrue(restored.domainEvents().isEmpty());
    }
    @Test void requiredDatesTextAndUpdaterCannotBeNullAndTextHasNoVarcharLimit() {
        var item = sample(); var updated = item.updatedAt(); var record = item.clinicalRecordId();
        assertThrows(NullPointerException.class, () -> item.update(ClinicalRecordId.generate(), item.professionalId(), item.encounterTypeId(), started, null,
                "Nuevo", "Nueva", item.modalityId(), item.statusId(), item.updatedBy()));
        assertThrows(NullPointerException.class, () -> item.update(record, item.professionalId(), item.encounterTypeId(), null, started,
                "Nuevo", "Nueva", item.modalityId(), item.statusId(), item.updatedBy()));
        assertThrows(NullPointerException.class, () -> item.update(record, item.professionalId(), item.encounterTypeId(), started, started,
                null, "Nueva", item.modalityId(), item.statusId(), item.updatedBy()));
        assertThrows(NullPointerException.class, () -> item.update(record, item.professionalId(), item.encounterTypeId(), started, started,
                "Nuevo", null, item.modalityId(), item.statusId(), item.updatedBy()));
        assertThrows(NullPointerException.class, () -> item.update(record, item.professionalId(), item.encounterTypeId(), started, started,
                "Nuevo", "Nueva", item.modalityId(), item.statusId(), null));
        assertEquals(record, item.clinicalRecordId()); assertEquals("Motivo", item.reasonForVisit()); assertEquals(updated, item.updatedAt()); assertEquals(1, item.domainEvents().size());
        item.update(record, item.professionalId(), item.encounterTypeId(), started, started, "x".repeat(10000), "y".repeat(10000),
                item.modalityId(), item.statusId(), item.updatedBy());
        assertEquals(10000, item.reasonForVisit().length()); assertEquals(10000, item.currentCondition().length());
    }
}
