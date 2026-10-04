package com.migracion.rangel.application.chatairunerror.usecase;
import com.migracion.rangel.domain.chatairunerror.port.repository.ChatAiRunErrorRepository;
import com.migracion.rangel.domain.chatairunerror.model.valueobject.ChatAiRunErrorId;
import com.migracion.rangel.application.chatairunerror.dto.ChatAiRunErrorResponse;
import com.migracion.rangel.application.chatairunerror.exception.ChatAiRunErrorNotFoundApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.chatairunerror.event.ChatAiRunErrorDeletedEvent;
public class DeleteChatAiRunErrorUseCase {
    private final ChatAiRunErrorRepository repository;
    public DeleteChatAiRunErrorUseCase(ChatAiRunErrorRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ChatAiRunErrorDeletedEvent execute(ChatAiRunErrorId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new ChatAiRunErrorNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new ChatAiRunErrorDeletedEvent(id, LocalDateTime.now());
    }
}

