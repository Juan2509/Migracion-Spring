package com.migracion.rangel.application.contact.command;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
public record UpdateContactCommand(ContactId id, String fullName, String email,
        String notes, CityMunicipalityId cityId, ProfessionalId updatedBy) {}
