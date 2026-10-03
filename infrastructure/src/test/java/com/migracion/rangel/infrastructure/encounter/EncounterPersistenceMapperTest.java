package com.migracion.rangel.infrastructure.encounter;
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
import com.migracion.rangel.application.encounter.dto.EncounterResponse;
import com.migracion.rangel.infrastructure.encounter.adapters.out.persistence.mappers.EncounterPersistenceMapper;
class EncounterPersistenceMapperTest {
    @Test void roundTripPreservesEveryReferenceTextAndTemporalFieldWithoutEvents() {
        var started = OffsetDateTime.parse("2026-10-03T09:30:00-05:00");
        var original = Encounter.register(ClinicalRecordId.generate(), ProfessionalId.generate(), EncounterTypeId.generate(),
                started, started.plusHours(1), "Motivo", "Condición", EncounterModalityId.generate(),
                EncounterStatusId.generate(), ProfessionalId.generate(), ProfessionalId.generate());
        var mapper = new EncounterPersistenceMapper();
        var restored = mapper.toDomain(mapper.toJpa(original));
        assertEquals(EncounterResponse.from(original), EncounterResponse.from(restored)); assertTrue(restored.domainEvents().isEmpty());
    }
}
