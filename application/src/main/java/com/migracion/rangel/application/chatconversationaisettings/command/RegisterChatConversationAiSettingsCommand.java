package com.migracion.rangel.application.chatconversationaisettings.command;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.aimodel.model.valueobject.AiModelId;

public record RegisterChatConversationAiSettingsCommand(ChatConversationId conversationId, Boolean aiEnabled, AiModelId defaultModelId) {}

