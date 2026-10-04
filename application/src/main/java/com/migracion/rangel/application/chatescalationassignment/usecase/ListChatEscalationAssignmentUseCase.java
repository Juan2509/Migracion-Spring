package com.migracion.rangel.application.chatescalationassignment.usecase;
import com.migracion.rangel.domain.chatescalationassignment.port.repository.ChatEscalationAssignmentRepository;
import com.migracion.rangel.application.chatescalationassignment.dto.ChatEscalationAssignmentResponse;
import java.util.List;
public class ListChatEscalationAssignmentUseCase {
    private final ChatEscalationAssignmentRepository repository;
    public ListChatEscalationAssignmentUseCase(ChatEscalationAssignmentRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<ChatEscalationAssignmentResponse> execute() { return repository.findAll().stream().map(ChatEscalationAssignmentResponse::from).toList(); }
}

