package com.migracion.rangel.domain.patient;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.domain.gender.model.valueobject.GenderId;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;

import com.migracion.rangel.domain.patient.model.aggregate.Patient;
class PatientTest {
    private final DocumentTypeId document = DocumentTypeId.generate();
    private final GenderId sex = GenderId.generate();
    private final GenderId gender = GenderId.generate();
    private final CityMunicipalityId city = CityMunicipalityId.generate();
    private Patient sample(ProfessionalId creator) {
        return Patient.register(document, "DOC1", "Ana", null, "Pérez", null,
                LocalDate.of(1990, 1, 2), sex, gender, "ana@example.com", "300123", "Calle 1", false, city, creator);
    }
    @Test void optionalFieldsAndFalseAreAcceptedAndCreationAuditIsImmutable() {
        var creator = ProfessionalId.generate();
        var item = sample(creator); var id = item.id(); var created = item.createdAt();
        assertNull(item.middleName()); assertNull(item.secondLastName()); assertNull(item.updatedBy()); assertFalse(item.active());
        assertEquals(1, item.domainEvents().size()); item.clearDomainEvents();
        var updater = ProfessionalId.generate();
        item.update(document, "DOC2", "María", "Luisa", "Gómez", "Rojas",
                LocalDate.of(1989, 5, 6), gender, sex, "maria@example.com", "300456", "Calle 2", true, city, updater);
        assertEquals(id, item.id()); assertEquals(created, item.createdAt()); assertEquals(creator, item.createdBy());
        assertEquals(updater, item.updatedBy()); assertEquals("Luisa", item.middleName()); assertEquals("Rojas", item.secondLastName());
        assertEquals(gender, item.biologicalSexId()); assertEquals(sex, item.genderIdentity());
        assertEquals(1, item.domainEvents().size());
        var automatic = sample(null); assertNull(automatic.createdBy());
    }
    @Test void everyTextLimitAndRequiredValueIsValidatedWithoutPartialChanges() {
        var item = sample(null); var time = item.updatedAt();
        int[] limits = {30, 50, 50, 50, 50, 150, 30, 250};
        for (int i = 0; i < limits.length; i++) {
            final int index = i;
            String[] values = {"DOC1", "Ana", null, "Pérez", null, "ana@example.com", "300123", "Calle 1"};
            values[index] = "x".repeat(limits[index] + 1);
            assertThrows(IllegalArgumentException.class, () -> item.update(document, values[0], values[1], values[2], values[3], values[4],
                    LocalDate.of(1990, 1, 2), sex, gender, values[5], values[6], values[7], true, city, null));
            assertEquals("Ana", item.firstName()); assertNull(item.middleName()); assertEquals(time, item.updatedAt());
            assertEquals(1, item.domainEvents().size());
        }
        assertThrows(NullPointerException.class, () -> item.update(document, "DOC1", "Ana", null, "Pérez", null,
                null, sex, gender, "ana@example.com", "300123", "Calle 1", true, city, null));
        assertThrows(NullPointerException.class, () -> item.update(document, "DOC1", "Ana", null, "Pérez", null,
                LocalDate.of(1990, 1, 2), sex, gender, "ana@example.com", "300123", "Calle 1", null, city, null));
        assertEquals(time, item.updatedAt());
    }
    @Test void restoreRetainsBothAuditDatesAndDoesNotEmitRegistrationEvent() {
        var original = sample(null);
        var restored = Patient.restore(original.id(), document, original.documentNumber(), original.firstName(),
                original.middleName(), original.lastName(), original.secondLastName(), original.birthDate(), sex, gender,
                original.email(), original.phone(), original.address(), original.active(), city,
                original.createdAt(), null, original.updatedAt(), null);
        assertEquals(original.id(), restored.id()); assertEquals(original.createdAt(), restored.createdAt());
        assertEquals(original.updatedAt(), restored.updatedAt()); assertTrue(restored.domainEvents().isEmpty());
    }
}

