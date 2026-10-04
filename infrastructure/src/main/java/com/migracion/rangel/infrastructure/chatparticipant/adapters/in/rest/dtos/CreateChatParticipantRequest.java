package com.migracion.rangel.infrastructure.chatparticipant.adapters.in.rest.dtos;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
public record CreateChatParticipantRequest(
        @NotNull UUID conversationId,
        @NotNull UUID participantTypeId,
        UUID patientId,
        UUID professionalId
) {}
