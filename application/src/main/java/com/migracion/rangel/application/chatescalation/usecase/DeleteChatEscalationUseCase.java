package com.migracion.rangel.application.chatescalation.usecase;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import com.migracion.rangel.domain.chatescalation.port.repository.ChatEscalationRepository;
import com.migracion.rangel.domain.chatescalation.model.valueobject.ChatEscalationId;
import com.migracion.rangel.application.chatescalation.dto.ChatEscalationResponse;
import com.migracion.rangel.application.chatescalation.exception.ChatEscalationNotFoundApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.chatescalation.event.ChatEscalationDeletedEvent;
public class DeleteChatEscalationUseCase {
    private final ChatEscalationRepository repository;
    public DeleteChatEscalationUseCase(ChatEscalationRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ChatEscalationDeletedEvent execute(ChatEscalationId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new ChatEscalationNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new ChatEscalationDeletedEvent(id, LocalDateTime.now());
    }
}

