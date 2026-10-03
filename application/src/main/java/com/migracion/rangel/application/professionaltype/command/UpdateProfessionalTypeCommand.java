package com.migracion.rangel.application.professionaltype.command;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
public record UpdateProfessionalTypeCommand(ProfessionalTypeId id, String name) {}
