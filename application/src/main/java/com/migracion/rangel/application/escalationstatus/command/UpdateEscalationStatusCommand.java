package com.migracion.rangel.application.escalationstatus.command;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
public record UpdateEscalationStatusCommand(EscalationStatusId id, String nameStatus) {}
