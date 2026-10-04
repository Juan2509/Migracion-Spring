package com.migracion.rangel.application.conversationstatus.command;
import com.migracion.rangel.domain.conversationstatus.model.valueobject.ConversationStatusId;
public record UpdateConversationStatusCommand(ConversationStatusId id, String nameStatus) {}
