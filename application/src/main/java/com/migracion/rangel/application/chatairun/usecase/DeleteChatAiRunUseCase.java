package com.migracion.rangel.application.chatairun.usecase;
import com.migracion.rangel.domain.chatairun.port.repository.ChatAiRunRepository;
import com.migracion.rangel.domain.chatairun.model.valueobject.ChatAiRunId;
import com.migracion.rangel.application.chatairun.dto.ChatAiRunResponse;
import com.migracion.rangel.application.chatairun.exception.ChatAiRunNotFoundApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.chatairun.event.ChatAiRunDeletedEvent;
public class DeleteChatAiRunUseCase {
    private final ChatAiRunRepository repository;
    public DeleteChatAiRunUseCase(ChatAiRunRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ChatAiRunDeletedEvent execute(ChatAiRunId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new ChatAiRunNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new ChatAiRunDeletedEvent(id, LocalDateTime.now());
    }
}

