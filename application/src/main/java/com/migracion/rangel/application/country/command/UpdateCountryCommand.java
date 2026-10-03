package com.migracion.rangel.application.country.command;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;
public record UpdateCountryCommand(CountryId id, String nameCountry, String codeCountry, String description, Boolean isActive, String telephonePrefix) {}
