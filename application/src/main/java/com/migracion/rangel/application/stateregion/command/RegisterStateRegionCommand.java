package com.migracion.rangel.application.stateregion.command;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;

public record RegisterStateRegionCommand(String nameRegion, String codeRegion, String description, Boolean isActive, CountryId countryId) {}
