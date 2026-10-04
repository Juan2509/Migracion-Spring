package com.migracion.rangel.infrastructure.chatescalationstatushistory.adapters.in.rest.dtos;
import java.util.UUID;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;
public record CreateChatEscalationStatusHistoryRequest(@NotNull UUID escalationId, @NotNull UUID escalationStatusId, @NotNull LocalDateTime changedAt) {}
