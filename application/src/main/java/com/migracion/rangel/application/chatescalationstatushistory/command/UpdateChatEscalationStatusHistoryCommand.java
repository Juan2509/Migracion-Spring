package com.migracion.rangel.application.chatescalationstatushistory.command;
import com.migracion.rangel.domain.chatescalation.model.valueobject.ChatEscalationId;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.chatescalationstatushistory.model.valueobject.ChatEscalationStatusHistoryId;
public record UpdateChatEscalationStatusHistoryCommand(ChatEscalationStatusHistoryId id, ChatEscalationId escalationId, EscalationStatusId escalationStatusId, LocalDateTime changedAt) {}
