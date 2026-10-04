package com.migracion.rangel.application.chatescalation.command;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;

public record RegisterChatEscalationCommand(ChatConversationId conversationId, String reason, EscalationStatusId statusId, Boolean fromAi) {}

