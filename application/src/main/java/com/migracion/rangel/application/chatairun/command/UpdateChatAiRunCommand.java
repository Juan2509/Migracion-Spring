package com.migracion.rangel.application.chatairun.command;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.chatmessage.model.valueobject.ChatMessageId;
import com.migracion.rangel.domain.aimodel.model.valueobject.AiModelId;
import com.migracion.rangel.domain.airunstatus.model.valueobject.AiRunStatusId;
import com.migracion.rangel.domain.chatairun.model.valueobject.ChatAiRunId;
public record UpdateChatAiRunCommand(ChatAiRunId id, ChatConversationId conversationId, ChatMessageId messageId, AiModelId modelId, AiRunStatusId aiRunStatusId) {}

