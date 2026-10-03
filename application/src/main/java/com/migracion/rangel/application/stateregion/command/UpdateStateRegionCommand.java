package com.migracion.rangel.application.stateregion.command;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;
import com.migracion.rangel.domain.stateregion.model.valueobject.StateRegionId;
public record UpdateStateRegionCommand(StateRegionId id, String nameRegion, String codeRegion, String description, Boolean isActive, CountryId countryId) {}
