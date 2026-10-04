package com.migracion.rangel.application.chatconversation.command;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.conversationstatus.model.valueobject.ConversationStatusId;
import com.migracion.rangel.domain.priority.model.valueobject.PriorityId;

public record RegisterChatConversationCommand(ConversationStatusId conversationStatusId, PriorityId priorityId, LocalDateTime lastMessageAt, Boolean closed, LocalDateTime closedAt, UUID closedBy) {}

