package com.migracion.rangel.infrastructure.mentalstatusexam;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.mentalstatusexam.model.aggregate.MentalStatusExam;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.application.mentalstatusexam.dto.MentalStatusExamResponse;
import com.migracion.rangel.infrastructure.mentalstatusexam.adapters.out.persistence.mappers.MentalStatusExamPersistenceMapper;
class MentalStatusExamPersistenceMapperTest {
    @Test void roundTripPreservesAllSeventeenTextsReferencesAndCreationAuditWithoutEvents() {
        String[] t = {"appearance", "behavior", "attitude", "consciousness", "orientation", "attention", "memory", "speech", "mood", "affect", "thoughtProcess", "thoughtContent", "perception", "judgment", "insight", "psychomotorActivity", "observations"};
        var original = MentalStatusExam.register(EncounterId.generate(), t[0], t[1], t[2], t[3], t[4], t[5], t[6], t[7], t[8], t[9], t[10], t[11], t[12], t[13], t[14], t[15], t[16], ProfessionalId.generate());
        var mapper = new MentalStatusExamPersistenceMapper(); var restored = mapper.toDomain(mapper.toJpa(original));
        assertEquals(MentalStatusExamResponse.from(original), MentalStatusExamResponse.from(restored)); assertTrue(restored.domainEvents().isEmpty());
    }
}
