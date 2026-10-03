package com.migracion.rangel.infrastructure.professional.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.professional.model.aggregate.Professional;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.infrastructure.professional.adapters.out.persistence.entity.ProfessionalJpaEntity;

public class ProfessionalPersistenceMapper {
    public ProfessionalJpaEntity toJpa(Professional professional) {
        if (professional == null) { return null; }
        var entity = new ProfessionalJpaEntity();
        entity.setId(professional.id().value());
        entity.setDocumentTypeId(professional.documentTypeId().value());
        entity.setDocumentNumber(professional.documentNumber());
        entity.setFirstName(professional.firstName());
        entity.setLastName(professional.lastName());
        entity.setProfessionalType(professional.professionalType().value());
        entity.setLicenseNumber(professional.licenseNumber());
        entity.setActive(professional.active());
        entity.setCityId(professional.cityId().value());
        entity.setCreatedAt(professional.createdAt());
        entity.setUpdatedAt(professional.updatedAt());
        return entity;
    }
    public Professional toDomain(ProfessionalJpaEntity entity) {
        if (entity == null) { return null; }
        return Professional.restore(new ProfessionalId(entity.getId()),
                new DocumentTypeId(entity.getDocumentTypeId()), entity.getDocumentNumber(), entity.getFirstName(), entity.getLastName(), new ProfessionalTypeId(entity.getProfessionalType()), entity.getLicenseNumber(), entity.getActive(), new CityMunicipalityId(entity.getCityId()),
                entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
