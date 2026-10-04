package com.migracion.rangel.application.chatescalationassignment.usecase;
import com.migracion.rangel.domain.chatescalationassignment.port.repository.ChatEscalationAssignmentRepository;
import com.migracion.rangel.domain.chatescalationassignment.model.valueobject.ChatEscalationAssignmentId;
import com.migracion.rangel.application.chatescalationassignment.dto.ChatEscalationAssignmentResponse;
import com.migracion.rangel.application.chatescalationassignment.exception.ChatEscalationAssignmentNotFoundApplicationException;

public class GetChatEscalationAssignmentByIdUseCase {
    private final ChatEscalationAssignmentRepository repository;
    public GetChatEscalationAssignmentByIdUseCase(ChatEscalationAssignmentRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ChatEscalationAssignmentResponse execute(ChatEscalationAssignmentId id) { return ChatEscalationAssignmentResponse.from(repository.findById(id).orElseThrow(() -> new ChatEscalationAssignmentNotFoundApplicationException(id))); }
}

