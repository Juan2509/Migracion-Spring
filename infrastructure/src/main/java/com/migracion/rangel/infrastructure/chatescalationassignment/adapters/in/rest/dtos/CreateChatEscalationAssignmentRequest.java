package com.migracion.rangel.infrastructure.chatescalationassignment.adapters.in.rest.dtos;
import java.util.UUID;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;
public record CreateChatEscalationAssignmentRequest(@NotNull UUID escalationId, @NotNull UUID professionalId, @NotNull LocalDateTime assignedAt) {}
