package com.migracion.rangel.application.consenttype.command;

public record RegisterConsentTypeCommand(String code, String name, Boolean active, String description) {}

