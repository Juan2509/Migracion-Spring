package com.migracion.rangel.infrastructure.professional;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.professional.model.aggregate.Professional;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.infrastructure.professional.adapters.out.persistence.mappers.ProfessionalPersistenceMapper;

class ProfessionalPersistenceMapperTest {
    @Test
    void roundTripPreservesAllFieldsReferencesAndZonedTimestampsWithoutEvents() {
        var created = OffsetDateTime.parse("2020-01-01T10:00:00-05:00");
        var updated = OffsetDateTime.parse("2021-02-03T11:00:00+02:00");
        var professional = Professional.restore(ProfessionalId.generate(), DocumentTypeId.generate(), "DOC-1", "Ana", "Pérez", ProfessionalTypeId.generate(), "LIC-1", false, CityMunicipalityId.generate(), created, updated);
        var mapper = new ProfessionalPersistenceMapper();
        var entity = mapper.toJpa(professional);
        assertEquals(professional.documentTypeId().value(), entity.getDocumentTypeId());
        assertEquals(professional.professionalType().value(), entity.getProfessionalType());
        assertEquals(professional.cityId().value(), entity.getCityId());
        var restored = mapper.toDomain(entity);
        assertEquals(professional.id(), restored.id());
        assertEquals(professional.documentTypeId(), restored.documentTypeId());
        assertEquals(professional.documentNumber(), restored.documentNumber());
        assertEquals(professional.firstName(), restored.firstName());
        assertEquals(professional.lastName(), restored.lastName());
        assertEquals(professional.professionalType(), restored.professionalType());
        assertEquals(professional.licenseNumber(), restored.licenseNumber());
        assertEquals(professional.active(), restored.active());
        assertEquals(professional.cityId(), restored.cityId());
        assertEquals(created, restored.createdAt());
        assertEquals(updated, restored.updatedAt());
        assertTrue(restored.domainEvents().isEmpty());
    }
}
