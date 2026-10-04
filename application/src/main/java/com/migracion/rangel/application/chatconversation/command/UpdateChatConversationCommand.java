package com.migracion.rangel.application.chatconversation.command;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.conversationstatus.model.valueobject.ConversationStatusId;
import com.migracion.rangel.domain.priority.model.valueobject.PriorityId;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
public record UpdateChatConversationCommand(ChatConversationId id, ConversationStatusId conversationStatusId, PriorityId priorityId, LocalDateTime lastMessageAt, Boolean closed, LocalDateTime closedAt, UUID closedBy) {}

