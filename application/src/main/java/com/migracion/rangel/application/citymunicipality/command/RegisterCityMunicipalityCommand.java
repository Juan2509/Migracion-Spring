package com.migracion.rangel.application.citymunicipality.command;
import com.migracion.rangel.domain.stateregion.model.valueobject.StateRegionId;

public record RegisterCityMunicipalityCommand(String nameCity, String codeCiti, String description, Boolean isActive, StateRegionId regionId) {}
