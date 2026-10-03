package com.migracion.rangel.application.professional.command;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
public record UpdateProfessionalCommand(ProfessionalId id, DocumentTypeId documentTypeId, String documentNumber, String firstName, String lastName, ProfessionalTypeId professionalType, String licenseNumber, Boolean active, CityMunicipalityId cityId) {}
