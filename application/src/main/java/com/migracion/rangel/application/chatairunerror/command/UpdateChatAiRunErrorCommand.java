package com.migracion.rangel.application.chatairunerror.command;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatairun.model.valueobject.ChatAiRunId;
import com.migracion.rangel.domain.chatairunerror.model.valueobject.ChatAiRunErrorId;
public record UpdateChatAiRunErrorCommand(ChatAiRunErrorId id, ChatAiRunId aiRunId, String errorMessage, String errorCode, String providerErrorId) {}

