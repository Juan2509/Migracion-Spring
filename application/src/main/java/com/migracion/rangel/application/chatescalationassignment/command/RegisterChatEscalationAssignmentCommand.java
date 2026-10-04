package com.migracion.rangel.application.chatescalationassignment.command;
import com.migracion.rangel.domain.chatescalation.model.valueobject.ChatEscalationId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import java.time.LocalDateTime;
public record RegisterChatEscalationAssignmentCommand(ChatEscalationId escalationId, ProfessionalId professionalId, LocalDateTime assignedAt) {}
