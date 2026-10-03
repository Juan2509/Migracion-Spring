package com.migracion.rangel.application.professional.command;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;

public record RegisterProfessionalCommand(DocumentTypeId documentTypeId, String documentNumber, String firstName, String lastName, ProfessionalTypeId professionalType, String licenseNumber, Boolean active, CityMunicipalityId cityId) {}
