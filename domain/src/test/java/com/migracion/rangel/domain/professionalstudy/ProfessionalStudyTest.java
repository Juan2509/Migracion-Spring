package com.migracion.rangel.domain.professionalstudy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.professionalstudy.model.aggregate.ProfessionalStudy;
import com.migracion.rangel.domain.study.model.valueobject.StudyId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;
class ProfessionalStudyTest {
    private ProfessionalStudy sample() {
        return ProfessionalStudy.register(StudyId.generate(), ProfessionalId.generate(), "Psicología", "Universidad", false, null, CountryId.generate());
    }
    @Test void registrationAndUpdatePreserveIdentityAndCreationDate() {
        var item = sample();
        var id = item.id(); var created = item.createdAt();
        assertFalse(item.isValid()); assertNull(item.resolutionNumber());
        assertEquals(1, item.domainEvents().size());
        item.clearDomainEvents();
        var study = StudyId.generate(); var professional = ProfessionalId.generate(); var country = CountryId.generate();
        item.update(study, professional, "Título", "Otra", true, "RES-1", country);
        assertEquals(id, item.id()); assertEquals(created, item.createdAt());
        assertEquals(study, item.studyId()); assertEquals(professional, item.professionalId()); assertEquals(country, item.countryId());
        assertTrue(item.isValid()); assertEquals("RES-1", item.resolutionNumber());
        assertEquals(1, item.domainEvents().size());
    }
    @Test void failedValidationDoesNotPartiallyModifyAggregate() {
        var item = sample(); var study = item.studyId(); var updated = item.updatedAt();
        assertThrows(IllegalArgumentException.class, () -> item.update(StudyId.generate(), item.professionalId(), "Nuevo", "Otra", true, "x".repeat(61), item.countryId()));
        assertEquals(study, item.studyId()); assertEquals("Psicología", item.title()); assertEquals(updated, item.updatedAt());
        assertEquals(1, item.domainEvents().size());
        assertThrows(IllegalArgumentException.class, () -> item.update(study, item.professionalId(), "x".repeat(101), "Otra", true, null, item.countryId()));
        assertThrows(IllegalArgumentException.class, () -> item.update(study, item.professionalId(), "Título", "x".repeat(101), true, null, item.countryId()));
        assertThrows(NullPointerException.class, () -> item.update(study, item.professionalId(), "Título", "Otra", null, null, item.countryId()));
    }
}
