package com.migracion.rangel.application.chatairunerror.command;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatairun.model.valueobject.ChatAiRunId;

public record RegisterChatAiRunErrorCommand(ChatAiRunId aiRunId, String errorMessage, String errorCode, String providerErrorId) {}

