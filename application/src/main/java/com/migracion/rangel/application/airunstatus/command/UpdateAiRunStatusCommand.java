package com.migracion.rangel.application.airunstatus.command;
import com.migracion.rangel.domain.airunstatus.model.valueobject.AiRunStatusId;
public record UpdateAiRunStatusCommand(AiRunStatusId id, String nameStatus) {}
