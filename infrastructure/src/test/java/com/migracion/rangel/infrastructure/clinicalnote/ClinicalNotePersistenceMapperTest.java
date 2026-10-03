package com.migracion.rangel.infrastructure.clinicalnote;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.clinicalnote.model.aggregate.ClinicalNote;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.application.clinicalnote.dto.ClinicalNoteResponse;
import com.migracion.rangel.infrastructure.clinicalnote.adapters.out.persistence.mappers.ClinicalNotePersistenceMapper;
class ClinicalNotePersistenceMapperTest {
    @Test void allFieldsAndOffsetsRoundTripWithoutEvents() {
        var original = ClinicalNote.register(EncounterId.generate(), "S", "O", "A", "P", "N",
                OffsetDateTime.parse("2026-10-03T09:30:00-05:00"), ProfessionalId.generate());
        var mapper = new ClinicalNotePersistenceMapper(); var restored = mapper.toDomain(mapper.toJpa(original));
        assertEquals(ClinicalNoteResponse.from(original), ClinicalNoteResponse.from(restored)); assertTrue(restored.domainEvents().isEmpty());
    }
}
