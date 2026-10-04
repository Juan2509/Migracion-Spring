package com.migracion.rangel.application.priority.command;
import com.migracion.rangel.domain.priority.model.valueobject.PriorityId;
public record UpdatePriorityCommand(PriorityId id, String namePriority) {}
