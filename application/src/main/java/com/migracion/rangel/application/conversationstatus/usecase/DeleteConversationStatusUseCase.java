package com.migracion.rangel.application.conversationstatus.usecase;
import com.migracion.rangel.domain.conversationstatus.port.repository.ConversationStatusRepository;
import com.migracion.rangel.domain.conversationstatus.model.valueobject.ConversationStatusId;
import com.migracion.rangel.application.conversationstatus.dto.ConversationStatusResponse;
import com.migracion.rangel.application.conversationstatus.exception.ConversationStatusNotFoundApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.conversationstatus.event.ConversationStatusDeletedEvent;
public class DeleteConversationStatusUseCase {
    private final ConversationStatusRepository repository;
    public DeleteConversationStatusUseCase(ConversationStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ConversationStatusDeletedEvent execute(ConversationStatusId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new ConversationStatusNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new ConversationStatusDeletedEvent(id, LocalDateTime.now());
    }
}
