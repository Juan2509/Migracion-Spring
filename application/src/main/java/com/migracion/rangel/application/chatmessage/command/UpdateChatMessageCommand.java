package com.migracion.rangel.application.chatmessage.command;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.messagetype.model.valueobject.MessageTypeId;
import com.migracion.rangel.domain.chatparticipant.model.valueobject.ChatParticipantId;
import com.migracion.rangel.domain.chatmessage.model.valueobject.ChatMessageId;
public record UpdateChatMessageCommand(ChatMessageId id, ChatConversationId conversationId, MessageTypeId messageTypeId, ChatParticipantId participantId, String content, String metadata) {}

