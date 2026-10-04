package com.migracion.rangel.application.chatescalationassignment.usecase;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
import com.migracion.rangel.domain.chatescalation.port.repository.ChatEscalationRepository;
import com.migracion.rangel.application.chatescalation.exception.ChatEscalationNotFoundApplicationException;
import com.migracion.rangel.domain.chatescalationassignment.port.repository.ChatEscalationAssignmentRepository;
import com.migracion.rangel.application.chatescalationassignment.dto.ChatEscalationAssignmentResponse;
import com.migracion.rangel.application.chatescalationassignment.command.RegisterChatEscalationAssignmentCommand;
import com.migracion.rangel.domain.chatescalationassignment.model.aggregate.ChatEscalationAssignment;
public class RegisterChatEscalationAssignmentUseCase {
    private final ChatEscalationAssignmentRepository repository;
    private final ChatEscalationRepository escalations;
    private final ProfessionalRepository professionals;
    public RegisterChatEscalationAssignmentUseCase(ChatEscalationAssignmentRepository repository, ChatEscalationRepository escalations, ProfessionalRepository professionals) {
        this.repository = java.util.Objects.requireNonNull(repository);
        this.escalations = java.util.Objects.requireNonNull(escalations);
        this.professionals = java.util.Objects.requireNonNull(professionals);
    }
    public ChatEscalationAssignmentResponse execute(RegisterChatEscalationAssignmentCommand command) {
        var aggregate = ChatEscalationAssignment.register(command.escalationId(), command.professionalId(), command.assignedAt());
        escalations.findById(command.escalationId()).orElseThrow(() -> new ChatEscalationNotFoundApplicationException(command.escalationId()));
        professionals.findById(command.professionalId()).orElseThrow(() -> new ProfessionalNotFoundApplicationException(command.professionalId()));
        return ChatEscalationAssignmentResponse.from(repository.save(aggregate));
    }
}

