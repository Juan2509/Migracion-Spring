package com.migracion.rangel.application.airunstatus.usecase;
import com.migracion.rangel.domain.airunstatus.port.repository.AiRunStatusRepository;
import com.migracion.rangel.domain.airunstatus.model.valueobject.AiRunStatusId;
import com.migracion.rangel.application.airunstatus.dto.AiRunStatusResponse;
import com.migracion.rangel.application.airunstatus.exception.AiRunStatusNotFoundApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.airunstatus.event.AiRunStatusDeletedEvent;
public class DeleteAiRunStatusUseCase {
    private final AiRunStatusRepository repository;
    public DeleteAiRunStatusUseCase(AiRunStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public AiRunStatusDeletedEvent execute(AiRunStatusId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new AiRunStatusNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new AiRunStatusDeletedEvent(id, LocalDateTime.now());
    }
}
