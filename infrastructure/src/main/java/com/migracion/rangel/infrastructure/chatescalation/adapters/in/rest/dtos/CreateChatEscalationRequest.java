package com.migracion.rangel.infrastructure.chatescalation.adapters.in.rest.dtos;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import java.util.UUID;
import jakarta.validation.constraints.*;
public record CreateChatEscalationRequest(
        @NotNull UUID conversationId,
        @NotNull String reason,
        @NotNull UUID statusId,
        @NotNull Boolean fromAi
) {}
