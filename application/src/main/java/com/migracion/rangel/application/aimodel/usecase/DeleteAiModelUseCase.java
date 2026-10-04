package com.migracion.rangel.application.aimodel.usecase;
import com.migracion.rangel.domain.aimodel.port.repository.AiModelRepository;
import com.migracion.rangel.domain.aimodel.model.valueobject.AiModelId;
import com.migracion.rangel.application.aimodel.dto.AiModelResponse;
import com.migracion.rangel.application.aimodel.exception.AiModelNotFoundApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.aimodel.event.AiModelDeletedEvent;
public class DeleteAiModelUseCase {
    private final AiModelRepository repository;
    public DeleteAiModelUseCase(AiModelRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public AiModelDeletedEvent execute(AiModelId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new AiModelNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new AiModelDeletedEvent(id, LocalDateTime.now());
    }
}

