package com.migracion.rangel.application.contact.command;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;

public record RegisterContactCommand(String fullName, String email,
        String notes, CityMunicipalityId cityId, ProfessionalId createdBy) {}
