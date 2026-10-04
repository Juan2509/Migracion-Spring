package com.migracion.rangel.application.chatescalationassignment.command;
import com.migracion.rangel.domain.chatescalation.model.valueobject.ChatEscalationId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.chatescalationassignment.model.valueobject.ChatEscalationAssignmentId;
public record UpdateChatEscalationAssignmentCommand(ChatEscalationAssignmentId id, ChatEscalationId escalationId, ProfessionalId professionalId, LocalDateTime assignedAt) {}
