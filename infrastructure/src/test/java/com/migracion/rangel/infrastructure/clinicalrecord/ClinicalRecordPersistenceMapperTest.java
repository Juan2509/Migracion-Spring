package com.migracion.rangel.infrastructure.clinicalrecord;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
import com.migracion.rangel.domain.clinicalrecord.model.aggregate.ClinicalRecord;
import com.migracion.rangel.application.clinicalrecord.dto.ClinicalRecordResponse;
import com.migracion.rangel.infrastructure.clinicalrecord.adapters.out.persistence.mappers.ClinicalRecordPersistenceMapper;
class ClinicalRecordPersistenceMapperTest {
    @Test void allFieldsAndTemporalTypesRoundTripWithoutRegistrationEvent() {
        var original = ClinicalRecord.register(PatientId.generate(), LocalDateTime.parse("2026-10-03T08:00:00"), "HC-1", OffsetDateTime.parse("2026-10-03T09:30:00-05:00"), OffsetDateTime.parse("2026-10-03T12:30:00+02:00"), ClinicalRecordStatusId.generate(), ProfessionalId.generate());
        var mapper = new ClinicalRecordPersistenceMapper();
        var restored = mapper.toDomain(mapper.toJpa(original));
        assertEquals(ClinicalRecordResponse.from(original), ClinicalRecordResponse.from(restored));
        assertTrue(restored.domainEvents().isEmpty());
    }
}
