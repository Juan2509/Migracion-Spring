package com.migracion.rangel.application.consenttype.command;
import com.migracion.rangel.domain.consenttype.model.valueobject.ConsentTypeId;
public record UpdateConsentTypeCommand(ConsentTypeId id, String code, String name, Boolean active, String description) {}

