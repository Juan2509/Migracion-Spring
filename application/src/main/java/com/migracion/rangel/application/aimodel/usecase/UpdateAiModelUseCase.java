package com.migracion.rangel.application.aimodel.usecase;
import com.migracion.rangel.domain.aimodel.port.repository.AiModelRepository;
import com.migracion.rangel.domain.aimodel.model.valueobject.AiModelId;
import com.migracion.rangel.application.aimodel.dto.AiModelResponse;
import com.migracion.rangel.application.aimodel.exception.AiModelNotFoundApplicationException;
import com.migracion.rangel.application.aimodel.command.UpdateAiModelCommand;
public class UpdateAiModelUseCase {
    private final AiModelRepository repository;
    public UpdateAiModelUseCase(AiModelRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public AiModelResponse execute(UpdateAiModelCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new AiModelNotFoundApplicationException(id));
        aggregate.update(command.providerModelId(), command.nameModel(), command.modelKey(), command.inputTokenPrice(), command.outputTokenPrice(), command.maxTokens(), command.contextWindow(), command.isActive());
        return AiModelResponse.from(repository.save(aggregate));
    }
}

