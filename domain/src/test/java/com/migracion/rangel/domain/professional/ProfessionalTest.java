package com.migracion.rangel.domain.professional;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.professional.model.aggregate.Professional;
import com.migracion.rangel.domain.professional.event.ProfessionalRegisteredEvent;
import com.migracion.rangel.domain.professional.event.ProfessionalUpdatedEvent;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
class ProfessionalTest {
    @Test
    void restoreAndUpdatePreserveIdentityAndZonedCreationDate() {
        var original = Professional.register(DocumentTypeId.generate(), "DOC-1", "Ana", "Pérez", ProfessionalTypeId.generate(), "LIC-1", false, CityMunicipalityId.generate());
        assertEquals(ZoneOffset.UTC, original.createdAt().getOffset());
        assertEquals(original.createdAt(), original.updatedAt());
        assertInstanceOf(ProfessionalRegisteredEvent.class, original.domainEvents().getFirst());
        var created = OffsetDateTime.parse("2020-01-01T10:00:00-05:00");
        var restored = Professional.restore(original.id(), original.documentTypeId(), original.documentNumber(), original.firstName(), original.lastName(), original.professionalType(), original.licenseNumber(), original.active(), original.cityId(), created, created);
        assertTrue(restored.domainEvents().isEmpty());
        restored.update(DocumentTypeId.generate(), "NuevoDocumentNumber", "NuevoFirstName", "NuevoLastName", ProfessionalTypeId.generate(), "NuevoLicenseNumber", true, CityMunicipalityId.generate());
        assertEquals(original.id(), restored.id());
        assertEquals(created, restored.createdAt());
        assertTrue(restored.updatedAt().isAfter(created));
        assertTrue(restored.active());
        assertInstanceOf(ProfessionalUpdatedEvent.class, restored.domainEvents().getFirst());
    }
    @Test
    void invalidLateFieldDoesNotPartiallyChangeProfessional() {
        var professional = Professional.register(DocumentTypeId.generate(), "DOC-1", "Ana", "Pérez", ProfessionalTypeId.generate(), "LIC-1", false, CityMunicipalityId.generate());
        var updated = professional.updatedAt();
        assertThrows(NullPointerException.class, () -> professional.update(
                DocumentTypeId.generate(), "NuevoDocumentNumber", "NuevoFirstName", "NuevoLastName", ProfessionalTypeId.generate(), "NuevoLicenseNumber", false, null));
        assertEquals("DOC-1", professional.documentNumber());
        assertEquals("Ana", professional.firstName());
        assertEquals("Pérez", professional.lastName());
        assertEquals("LIC-1", professional.licenseNumber());
        assertEquals(updated, professional.updatedAt());
        assertEquals(1, professional.domainEvents().size());
    }
    @Test
    void enforcesAllVarcharLengths() {
        assertThrows(IllegalArgumentException.class, () -> Professional.register(DocumentTypeId.generate(), "x".repeat(31), "Ana", "Pérez", ProfessionalTypeId.generate(), "LIC-1", false, CityMunicipalityId.generate()));
        assertThrows(IllegalArgumentException.class, () -> Professional.register(DocumentTypeId.generate(), "DOC-1", "x".repeat(61), "Pérez", ProfessionalTypeId.generate(), "LIC-1", false, CityMunicipalityId.generate()));
        assertThrows(IllegalArgumentException.class, () -> Professional.register(DocumentTypeId.generate(), "DOC-1", "Ana", "x".repeat(61), ProfessionalTypeId.generate(), "LIC-1", false, CityMunicipalityId.generate()));
        assertThrows(IllegalArgumentException.class, () -> Professional.register(DocumentTypeId.generate(), "DOC-1", "Ana", "Pérez", ProfessionalTypeId.generate(), "x".repeat(101), false, CityMunicipalityId.generate()));
    }
}
