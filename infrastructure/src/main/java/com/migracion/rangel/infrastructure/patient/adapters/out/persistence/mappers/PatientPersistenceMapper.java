package com.migracion.rangel.infrastructure.patient.adapters.out.persistence.mappers;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.domain.gender.model.valueobject.GenderId;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;

import com.migracion.rangel.domain.patient.model.aggregate.Patient;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.infrastructure.patient.adapters.out.persistence.entity.PatientJpaEntity;
public class PatientPersistenceMapper {
    public PatientJpaEntity toJpa(Patient patient) {
        if (patient == null) return null;
        var entity = new PatientJpaEntity();
        entity.setId(patient.id().value());
        entity.setDocumentTypeId(patient.documentTypeId().value());
        entity.setDocumentNumber(patient.documentNumber());
        entity.setFirstName(patient.firstName());
        entity.setMiddleName(patient.middleName());
        entity.setLastName(patient.lastName());
        entity.setSecondLastName(patient.secondLastName());
        entity.setBirthDate(patient.birthDate());
        entity.setBiologicalSexId(patient.biologicalSexId().value());
        entity.setGenderIdentity(patient.genderIdentity().value());
        entity.setEmail(patient.email());
        entity.setPhone(patient.phone());
        entity.setAddress(patient.address());
        entity.setActive(patient.active());
        entity.setCityId(patient.cityId().value());
        entity.setCreatedAt(patient.createdAt());
        entity.setCreatedBy(patient.createdBy() == null ? null : patient.createdBy().value());
        entity.setUpdatedAt(patient.updatedAt());
        entity.setUpdatedBy(patient.updatedBy() == null ? null : patient.updatedBy().value());
        return entity;
    }
    public Patient toDomain(PatientJpaEntity entity) {
        if (entity == null) return null;
        return Patient.restore(new PatientId(entity.getId()), new DocumentTypeId(entity.getDocumentTypeId()), entity.getDocumentNumber(), entity.getFirstName(), entity.getMiddleName(), entity.getLastName(), entity.getSecondLastName(), entity.getBirthDate(), new GenderId(entity.getBiologicalSexId()), new GenderId(entity.getGenderIdentity()), entity.getEmail(), entity.getPhone(), entity.getAddress(), entity.getActive(), new CityMunicipalityId(entity.getCityId()),
                entity.getCreatedAt(), entity.getCreatedBy() == null ? null : new ProfessionalId(entity.getCreatedBy()),
                entity.getUpdatedAt(), entity.getUpdatedBy() == null ? null : new ProfessionalId(entity.getUpdatedBy()));
    }
}
