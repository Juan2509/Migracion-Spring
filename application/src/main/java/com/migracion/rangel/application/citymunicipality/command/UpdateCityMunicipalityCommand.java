package com.migracion.rangel.application.citymunicipality.command;
import com.migracion.rangel.domain.stateregion.model.valueobject.StateRegionId;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
public record UpdateCityMunicipalityCommand(CityMunicipalityId id, String nameCity, String codeCiti, String description, Boolean isActive, StateRegionId regionId) {}
