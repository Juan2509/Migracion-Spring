package com.migracion.rangel.application.chatescalationstatushistory.usecase;
import com.migracion.rangel.domain.chatescalationstatushistory.port.repository.ChatEscalationStatusHistoryRepository;
import com.migracion.rangel.domain.chatescalationstatushistory.model.valueobject.ChatEscalationStatusHistoryId;
import com.migracion.rangel.application.chatescalationstatushistory.exception.ChatEscalationStatusHistoryNotFoundApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.chatescalationstatushistory.event.ChatEscalationStatusHistoryDeletedEvent;
public class DeleteChatEscalationStatusHistoryUseCase {
    private final ChatEscalationStatusHistoryRepository repository;
    public DeleteChatEscalationStatusHistoryUseCase(ChatEscalationStatusHistoryRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ChatEscalationStatusHistoryDeletedEvent execute(ChatEscalationStatusHistoryId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new ChatEscalationStatusHistoryNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new ChatEscalationStatusHistoryDeletedEvent(id, LocalDateTime.now());
    }
}

