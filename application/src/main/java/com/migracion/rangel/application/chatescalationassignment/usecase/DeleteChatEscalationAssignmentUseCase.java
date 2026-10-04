package com.migracion.rangel.application.chatescalationassignment.usecase;
import com.migracion.rangel.domain.chatescalationassignment.port.repository.ChatEscalationAssignmentRepository;
import com.migracion.rangel.domain.chatescalationassignment.model.valueobject.ChatEscalationAssignmentId;
import com.migracion.rangel.application.chatescalationassignment.exception.ChatEscalationAssignmentNotFoundApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.chatescalationassignment.event.ChatEscalationAssignmentDeletedEvent;
public class DeleteChatEscalationAssignmentUseCase {
    private final ChatEscalationAssignmentRepository repository;
    public DeleteChatEscalationAssignmentUseCase(ChatEscalationAssignmentRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ChatEscalationAssignmentDeletedEvent execute(ChatEscalationAssignmentId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new ChatEscalationAssignmentNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new ChatEscalationAssignmentDeletedEvent(id, LocalDateTime.now());
    }
}

