package com.migracion.rangel.application.country.command;

public record RegisterCountryCommand(String nameCountry, String codeCountry, String description, Boolean isActive, String telephonePrefix) {}
