package com.migracion.rangel.application.aimodel.usecase;
import com.migracion.rangel.domain.aimodel.port.repository.AiModelRepository;
import com.migracion.rangel.domain.aimodel.model.valueobject.AiModelId;
import com.migracion.rangel.application.aimodel.dto.AiModelResponse;
import com.migracion.rangel.application.aimodel.exception.AiModelNotFoundApplicationException;

public class GetAiModelByIdUseCase {
    private final AiModelRepository repository;
    public GetAiModelByIdUseCase(AiModelRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public AiModelResponse execute(AiModelId id) { return AiModelResponse.from(repository.findById(id).orElseThrow(() -> new AiModelNotFoundApplicationException(id))); }
}

