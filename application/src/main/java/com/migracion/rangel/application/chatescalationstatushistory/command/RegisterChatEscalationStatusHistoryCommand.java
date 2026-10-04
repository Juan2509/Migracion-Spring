package com.migracion.rangel.application.chatescalationstatushistory.command;
import com.migracion.rangel.domain.chatescalation.model.valueobject.ChatEscalationId;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import java.time.LocalDateTime;
public record RegisterChatEscalationStatusHistoryCommand(ChatEscalationId escalationId, EscalationStatusId escalationStatusId, LocalDateTime changedAt) {}
