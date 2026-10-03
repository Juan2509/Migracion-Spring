package com.migracion.rangel.infrastructure.patient;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.domain.gender.model.valueobject.GenderId;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;

import com.migracion.rangel.domain.patient.model.aggregate.Patient;
import com.migracion.rangel.application.patient.dto.PatientResponse;
import com.migracion.rangel.infrastructure.patient.adapters.out.persistence.mappers.PatientPersistenceMapper;
class PatientPersistenceMapperTest {
    @Test void roundTripPreservesEveryFieldIncludingNullableAuditAndDateWithoutEvents() {
        var mapper = new PatientPersistenceMapper();
        for (boolean populated : new boolean[]{false, true}) {
            var original = Patient.register(DocumentTypeId.generate(), "DOC1", "Ana", populated ? "María" : null,
                    "Pérez", populated ? "Rojas" : null, LocalDate.of(1990, 1, 2), GenderId.generate(), GenderId.generate(),
                    "ana@example.com", "300123", "Calle 1", false, CityMunicipalityId.generate(), populated ? ProfessionalId.generate() : null);
            original.update(original.documentTypeId(), original.documentNumber(), original.firstName(), original.middleName(),
                    original.lastName(), original.secondLastName(), original.birthDate(), original.biologicalSexId(),
                    original.genderIdentity(), original.email(), original.phone(), original.address(), original.active(),
                    original.cityId(), populated ? ProfessionalId.generate() : null);
            var restored = mapper.toDomain(mapper.toJpa(original));
            assertEquals(PatientResponse.from(original), PatientResponse.from(restored));
            assertTrue(restored.domainEvents().isEmpty());
        }
    }
}

