package com.migracion.rangel.infrastructure.clinicalrecordstatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.migracion.rangel.domain.clinicalrecordstatus.model.aggregate.ClinicalRecordStatus;
import com.migracion.rangel.application.clinicalrecordstatus.dto.ClinicalRecordStatusResponse;
import com.migracion.rangel.infrastructure.clinicalrecordstatus.adapters.out.persistence.mappers.ClinicalRecordStatusPersistenceMapper;
class ClinicalRecordStatusPersistenceMapperTest {
    @Test void allFieldsAndTemporalTypesRoundTripWithoutRegistrationEvent() {
        var original = ClinicalRecordStatus.register("OPEN", "Abierta");
        var mapper = new ClinicalRecordStatusPersistenceMapper();
        var restored = mapper.toDomain(mapper.toJpa(original));
        assertEquals(ClinicalRecordStatusResponse.from(original), ClinicalRecordStatusResponse.from(restored));
        assertTrue(restored.domainEvents().isEmpty());
    }
}
